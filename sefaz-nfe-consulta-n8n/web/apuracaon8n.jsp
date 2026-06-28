<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Apuração Diária N8N — SEFAZ × ERP</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; background: #f0f2f5; margin: 0; padding: 20px; color: #1e293b; }
        .wrap { max-width: 1300px; margin: 0 auto; }

        /* Cabeçalho */
        .page-header { background: linear-gradient(135deg,#0f172a 0%,#1e3a5f 100%);
            color:#fff; padding:20px 28px; border-radius:12px; margin-bottom:20px; }
        .page-header h1 { font-size:1.4rem; margin:0 0 4px; }
        .page-header p  { font-size:.85rem; opacity:.8; margin:0; }

        /* Cards de resumo geral */
        .resumo-grid { display:grid; grid-template-columns:repeat(3,1fr); gap:14px; margin-bottom:20px; }
        .resumo-box  { background:#fff; border-radius:10px; padding:18px 20px; text-align:center;
                       box-shadow:0 1px 4px rgba(0,0,0,.08); }
        .resumo-box .r-label { font-size:.72rem; font-weight:700; text-transform:uppercase;
                               letter-spacing:.05em; color:#64748b; margin-bottom:6px; }
        .resumo-box .r-valor { font-size:1.6rem; font-weight:700; }
        .col-ok  { color:#16a34a; }
        .col-div { color:#dc2626; }
        .col-neu { color:#2563eb; }

        /* Tabela resumo filiais */
        .card { background:#fff; border-radius:10px; padding:18px 20px;
                box-shadow:0 1px 4px rgba(0,0,0,.08); margin-bottom:16px; }
        table.resumo-tab { width:100%; border-collapse:collapse; font-size:.83rem; }
        table.resumo-tab th { background:#f1f5f9; color:#334155; font-weight:700; padding:9px 12px;
                              text-align:left; border-bottom:2px solid #e2e8f0; }
        table.resumo-tab td { padding:8px 12px; border-bottom:1px solid #f1f5f9; }
        table.resumo-tab tr:last-child td { border-bottom:none; font-weight:700; background:#f8fafc; }
        table.resumo-tab td:not(:first-child):not(:nth-child(2)) { text-align:right; }
        .text-right { text-align:right; }

        /* Painel por filial */
        .filial-card { background:#fff; border-radius:10px;
                       box-shadow:0 1px 4px rgba(0,0,0,.08); margin-bottom:16px; overflow:hidden; }
        .filial-hdr  { display:flex; align-items:center; gap:10px;
                       padding:14px 18px; border-bottom:2px solid #e2e8f0; background:#f8fafc; }
        .filial-hdr h3 { font-size:1rem; margin:0; color:#0f172a; }
        .filial-body { padding:16px 18px; }

        /* Badges apuração */
        .apuracao-grid { display:grid; grid-template-columns:repeat(4,1fr); gap:10px; margin-bottom:16px; }
        .ap-box  { border-radius:8px; padding:12px 10px; text-align:center; }
        .ap-box .ap-num   { font-size:1.6rem; font-weight:700; line-height:1; }
        .ap-box .ap-label { font-size:.7rem; text-transform:uppercase; letter-spacing:.05em; margin-top:3px; }
        .ap-ok     { background:#dcfce7; color:#166534; }
        .ap-div    { background:#fee2e2; color:#991b1b; }
        .ap-soerp  { background:#dbeafe; color:#1e40af; }
        .ap-sosefaz{ background:#fef9c3; color:#713f12; }

        /* Detalhamento por tipo (2 col) */
        .det-grid { display:grid; grid-template-columns:1fr 1fr; gap:14px; margin-bottom:14px; }
        .det-box  { background:#f8fafc; border-radius:8px; padding:12px 14px; }
        .det-box h4 { font-size:.75rem; font-weight:700; text-transform:uppercase;
                      color:#475569; margin:0 0 8px; }
        .det-row  { display:flex; justify-content:space-between; font-size:.83rem; padding:3px 0;
                    border-bottom:1px solid #e2e8f0; color:#475569; }
        .det-row:last-child { border-bottom:none; font-weight:700; color:#1e293b; padding-top:6px; }

        /* Tabela nota a nota */
        table.notas { width:100%; border-collapse:collapse; font-size:.82rem; }
        table.notas thead th { background:#f1f5f9; color:#334155; font-weight:600; padding:8px 10px;
                               text-align:left; border-bottom:2px solid #e2e8f0; font-size:.75rem;
                               text-transform:uppercase; letter-spacing:.04em; }
        table.notas thead th:not(:first-child) { text-align:right; }
        table.notas tbody td { padding:6px 10px; border-bottom:1px solid #f1f5f9; }
        table.notas tbody td:not(:first-child) { text-align:right; }
        table.notas tfoot td { background:#f8fafc; font-weight:700; padding:8px 10px;
                               border-top:2px solid #e2e8f0; }
        table.notas tfoot td:not(:first-child) { text-align:right; }
        tr.row-div   td { background:#fff5f5; }
        tr.row-soerp  td { background:#eff6ff; }
        tr.row-sosefaz td { background:#fefce8; }

        .badge { display:inline-block; padding:2px 7px; border-radius:4px;
                 font-size:.72rem; font-weight:700; }
        .badge-venda  { background:#dcfce7; color:#166534; }
        .badge-dev    { background:#fef3c7; color:#d97706; }
        .st-ok        { color:#16a34a; font-weight:600; }
        .st-div       { color:#dc2626; font-weight:600; }
        .st-erp       { color:#2563eb; font-weight:600; }
        .st-sefaz     { color:#ca8a04; font-weight:600; }

        .status-pill { display:inline-block; padding:3px 10px; border-radius:20px;
                       font-size:.75rem; font-weight:700; }
        .pill-ok      { background:#dcfce7; color:#166534; }
        .pill-div     { background:#fee2e2; color:#991b1b; }
        .pill-cool    { background:#fff7ed; color:#9a3412; }
        .pill-vazio   { background:#f1f5f9; color:#64748b; }

        .text-muted { color:#94a3b8; }
        .total-filiais { font-size:.82rem; color:#64748b; }

        @media(max-width:900px) {
            .resumo-grid { grid-template-columns:1fr 1fr; }
            .apuracao-grid { grid-template-columns:1fr 1fr; }
            .det-grid { grid-template-columns:1fr; }
        }
    </style>
</head>
<body>



</body>
</html>
