/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.servlet;

import com.google.gson.Gson;
import com.sefazam.model.ConsultaNFeResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Messias
 */
public class ConsultaNFeEmitidasServlet extends HttpServlet {

    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gson = new Gson();
        System.out.println("✅ ConsultaNFeEmitidasServlet inicializado!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");

        System.out.println("📥 Requisição recebida - Consulta NF-e Emitidas");
        System.out.println("   Período: " + dataInicio + " a " + dataFim);

        try {
            // TODO: Implementar consulta de NF-e emitidas usando NFe.consultaDFe()
            // Esta funcionalidade requer configuração adicional e está em desenvolvimento

            ConsultaNFeResponse response1 = new ConsultaNFeResponse(
                    false,
                    "501",
                    "Funcionalidade em desenvolvimento. Use a consulta por chave de acesso.");

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(response1));
            out.flush();

        } catch (Exception e) {
            System.err.println("❌ Erro no servlet: " + e.getMessage());
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            ConsultaNFeResponse errorResponse = new ConsultaNFeResponse(
                    false,
                    "500",
                    "Erro interno: " + e.getMessage());

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(errorResponse));
            out.flush();
        }
    }
}
