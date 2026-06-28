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

import com.sefazam.model.ConsultaNFeResponse;
import com.sefazam.service.ConsultaNFeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet para consulta de NF-e por chave de acesso
 * Endpoint: /consulta-nfe
 * Trabalha com JSP para exibição dos resultados
 * 
 * @author Messias
 */
@WebServlet(name = "ConsultaNFeServlet", urlPatterns = { "/consulta-nfe" })
public class ConsultaNFeServlet extends HttpServlet {

    private ConsultaNFeService consultaService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.consultaService = new ConsultaNFeService();
        System.out.println("✅ ConsultaNFeServlet inicializado!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona GET para a página inicial
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configura encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String chaveAcesso = request.getParameter("chaveAcesso");

        System.out.println("📥 Requisição recebida - Consulta NF-e");
        System.out.println("   Chave: " + chaveAcesso);

        try {
            // Valida se a chave foi informada
            if (chaveAcesso == null || chaveAcesso.trim().isEmpty()) {
                request.setAttribute("erro", "Chave de acesso não informada");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }

            // Realiza a consulta
            ConsultaNFeResponse consultaResponse = consultaService.consultarNotaFiscalNova(chaveAcesso);

            // Coloca o resultado no request para o JSP exibir
            request.setAttribute("resultado", consultaResponse);

            // Forward para a página JSP
            request.getRequestDispatcher("/index.jsp").forward(request, response);

            System.out.println("📤 Resposta enviada: " + consultaResponse.getCodigoStatus());

        } catch (Exception e) {
            System.err.println("❌ Erro no servlet: " + e.getMessage());
            e.printStackTrace();

            // Em caso de erro, envia para página de erro
            request.setAttribute("erro", "Erro ao processar consulta: " + e.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}
