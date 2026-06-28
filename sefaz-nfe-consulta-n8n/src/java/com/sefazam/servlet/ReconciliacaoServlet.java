/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.servlet;


/**
 *
 * @author Messias
 */

/**
 * Controller do Módulo de Reconciliação (Confronto de Valores)
 */

import com.sefazam.service.DatabaseService;
import com.sefazam.service.SefazBulkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller do Módulo de Reconciliação (Confronto de Valores)
 */
public class ReconciliacaoServlet extends HttpServlet {

    private DatabaseService databaseService;
    private SefazBulkService sefazBulkService;

    @Override
    public void init() throws ServletException {
        this.databaseService = new DatabaseService();
        this.sefazBulkService = new SefazBulkService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view   = request.getParameter("v");
        String action = request.getParameter("action");
        String page   = "/reconciliacao.jsp";

        // Ação de reset: zera todos os NSUs e remove cache de totais
        if ("resetNSU".equals(action)) {
            sefazBulkService.resetarTodosNSU();
            request.setAttribute("mensagem", "NSU resetado para todos os CNPJs. "
                    + "Próxima consulta fará re-scan completo da SEFAZ.");
            String dataInicio = request.getParameter("dataInicio");
            String dataFim    = request.getParameter("dataFim");
            if (dataInicio != null && !dataInicio.isEmpty()) {
                request.setAttribute("dataInicio", dataInicio);
                request.setAttribute("dataFim", dataFim != null ? dataFim : dataInicio);
            }
            request.getRequestDispatcher(page).forward(request, response);
            return;
        }

        // Se v=2, processa automaticamente para ontem (hoje - 1)
        if ("2".equals(view)) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.DATE, -1);
            String ontem = new java.text.SimpleDateFormat("yyyyMMdd").format(cal.getTime());
            processarReconciliacao(request, ontem, ontem);
            page = "/reconciliacao2.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String source = request.getParameter("source");

        // Regra para hoje se vier vazio
        if (dataInicio == null || dataInicio.isEmpty()) {
            dataInicio = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        if (dataFim == null || dataFim.isEmpty()) {
            dataFim = dataInicio;
        }

        processarReconciliacao(request, dataInicio, dataFim);

        // Retorna para a página de origem (1 ou 2)
        String target = "reconciliacao2".equals(source) ? "/reconciliacao2.jsp" : "/reconciliacao.jsp";
        request.getRequestDispatcher(target).forward(request, response);
    }

    /**
     * Centraliza a lógica de busca ERP + SEFAZ
     */
    private void processarReconciliacao(HttpServletRequest request, String dataInicio, String dataFim) {
        try {
            // 1. ERP
            List<DatabaseService.VendaDiaria> vendasERP = databaseService.getVendasPorPeriodo(dataInicio, dataFim);

            // 2. SEFAZ (com log direto no SefazBulkService)
            Map<String, Double> totaisSefaz = sefazBulkService.consultarTotaisSefaz(vendasERP);

            // 3. Detalhamento (opcional)
            String filialDetalhe = request.getParameter("filial_detalhe");
            String dataDetalhe = request.getParameter("data_detalhe");
            if (filialDetalhe != null && !filialDetalhe.trim().isEmpty()) {
                String dataRaw = dataDetalhe.replace("-", "");
                List<DatabaseService.VendaDetalhe> detalhes = databaseService.getDetalhesVendas(filialDetalhe, dataRaw);
                request.setAttribute("vendasDetalhe", detalhes);
                request.setAttribute("filialSelecionada", filialDetalhe);
                request.setAttribute("dataSelecionada", dataDetalhe);
            }

            // 4. Atributos para View
            request.setAttribute("vendasERP", vendasERP);
            request.setAttribute("totaisSefaz", totaisSefaz);
            request.setAttribute("dataInicio", dataInicio);
            request.setAttribute("dataFim", dataFim);
            request.setAttribute("mensagem", "Confronto realizado com sucesso!");

        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao processar reconciliação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

