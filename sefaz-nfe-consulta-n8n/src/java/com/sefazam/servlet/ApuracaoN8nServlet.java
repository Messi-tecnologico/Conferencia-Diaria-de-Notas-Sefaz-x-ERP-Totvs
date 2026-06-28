package com.sefazam.servlet;

import com.sefazam.config.ConfiguracoesNfe;
import com.sefazam.config.SefazConfig;
import com.sefazam.email.N8nEmailAutomation;
import com.sefazam.model.ResultadoFilial;
import com.sefazam.nfe.NFeDistribuicao;
import com.sefazam.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servlet de apuração diária automática — todas as filiais, data D-1.
 * Chamado pelo N8N via GET /apuracaon8n (sem parâmetros).
 * Parâmetro opcional: ?data=YYYYMMDD para reprocessar uma data específica.
 */
public class ApuracaoN8nServlet extends HttpServlet {

    // ── Mapa ordenado CNPJ → Label da filial ────────────────────────────────
    private static final LinkedHashMap<String, String> FILIAIS = new LinkedHashMap<>();
  
}
