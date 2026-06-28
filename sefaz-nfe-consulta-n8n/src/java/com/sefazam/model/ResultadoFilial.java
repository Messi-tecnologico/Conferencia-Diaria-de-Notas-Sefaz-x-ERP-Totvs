package com.sefazam.model;

import com.sefazam.servlet.TesteSefazCnpjServlet.ComparacaoNota;
import java.util.ArrayList;
import java.util.List;

/**
 * Resultado consolidado de uma filial para a apuração diária N8N.
 */
public class ResultadoFilial {

    public String cnpj;
    public String filialLabel;   // ex: "FILIAL14"
    public String filialErp;     // código ERP ex: "14"
    public double totalErp;
    public double totalSefaz;
    public double somaVendasErp;
    public double somaDevErp;
    public double somaVendasSefaz;
    public double somaDevSefaz;
    public long   qtdOk;
    public long   qtdDiv;
    public long   qtdSoErp;
    public long   qtdSoSefaz;
    public boolean cooldown;
    public String  cooldownMsg;
    public List<ComparacaoNota> comparacoes = new ArrayList<>();

    // ── Calculados ──────────────────────────────────────────────────────
    public double getDiferenca()            { return totalErp - totalSefaz; }
    public double getLiquidoErp()           { return somaVendasErp - somaDevErp; }
    public double getLiquidoSefaz()         { return somaVendasSefaz - somaDevSefaz; }
    public double getDiferencaConferencia() { return getLiquidoErp() - getLiquidoSefaz(); }
    public boolean isOk()                   { return Math.abs(getDiferenca()) <= 0.05; }

    // ── Getters para JSTL EL ────────────────────────────────────────────
    public String  getCnpj()            { return cnpj; }
    public String  getFilialLabel()     { return filialLabel; }
    public String  getFilialErp()       { return filialErp != null && !filialErp.isEmpty() ? filialErp : "—"; }
    public double  getTotalErp()        { return totalErp; }
    public double  getTotalSefaz()      { return totalSefaz; }
    public double  getSomaVendasErp()   { return somaVendasErp; }
    public double  getSomaDevErp()      { return somaDevErp; }
    public double  getSomaVendasSefaz() { return somaVendasSefaz; }
    public double  getSomaDevSefaz()    { return somaDevSefaz; }
    public long    getQtdOk()           { return qtdOk; }
    public long    getQtdDiv()          { return qtdDiv; }
    public long    getQtdSoErp()        { return qtdSoErp; }
    public long    getQtdSoSefaz()      { return qtdSoSefaz; }
    public boolean isCooldown()         { return cooldown; }
    public String  getCooldownMsg()     { return cooldownMsg != null ? cooldownMsg : ""; }
    public List<ComparacaoNota> getComparacoes() { return comparacoes; }
}
