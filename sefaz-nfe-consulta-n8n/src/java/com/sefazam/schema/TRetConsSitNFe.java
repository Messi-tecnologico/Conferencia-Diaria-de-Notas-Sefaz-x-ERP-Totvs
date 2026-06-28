/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.schema;

/**
 *
 * @author Messias
 */
public class TRetConsSitNFe {

    private String versao;
    private String tpAmb;
    private String verAplic;
    private String cStat;
    private String xMotivo;
    private String cUF;
    private String chNFe;
    private ProtNFe protNFe;
    private NFeDados.ProcNFe procNFe; // Dados detalhados da NF-e

    // ========== GETTERS E SETTERS ==========

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getTpAmb() {
        return tpAmb;
    }

    public void setTpAmb(String tpAmb) {
        this.tpAmb = tpAmb;
    }

    public String getVerAplic() {
        return verAplic;
    }

    public void setVerAplic(String verAplic) {
        this.verAplic = verAplic;
    }

    public String getCStat() {
        return cStat;
    }

    public void setCStat(String cStat) {
        this.cStat = cStat;
    }

    public String getXMotivo() {
        return xMotivo;
    }

    public void setXMotivo(String xMotivo) {
        this.xMotivo = xMotivo;
    }

    public String getCUF() {
        return cUF;
    }

    public void setCUF(String cUF) {
        this.cUF = cUF;
    }

    public String getChNFe() {
        return chNFe;
    }

    public void setChNFe(String chNFe) {
        this.chNFe = chNFe;
    }

    public ProtNFe getProtNFe() {
        return protNFe;
    }

    public void setProtNFe(ProtNFe protNFe) {
        this.protNFe = protNFe;
    }

    public NFeDados.ProcNFe getProcNFe() {
        return procNFe;
    }

    public void setProcNFe(NFeDados.ProcNFe procNFe) {
        this.procNFe = procNFe;
    }

    // ========== CLASSE INTERNA: ProtNFe ==========

    public static class ProtNFe {
        private InfProt infProt;

        public InfProt getInfProt() {
            return infProt;
        }

        public void setInfProt(InfProt infProt) {
            this.infProt = infProt;
        }
    }

    // ========== CLASSE INTERNA: InfProt ==========

    public static class InfProt {
        private String tpAmb;
        private String verAplic;
        private String chNFe;
        private String dhRecbto;
        private String nProt;
        private String digVal;
        private String cStat;
        private String xMotivo;

        public String getTpAmb() {
            return tpAmb;
        }

        public void setTpAmb(String tpAmb) {
            this.tpAmb = tpAmb;
        }

        public String getVerAplic() {
            return verAplic;
        }

        public void setVerAplic(String verAplic) {
            this.verAplic = verAplic;
        }

        public String getChNFe() {
            return chNFe;
        }

        public void setChNFe(String chNFe) {
            this.chNFe = chNFe;
        }

        public String getDhRecbto() {
            return dhRecbto;
        }

        public void setDhRecbto(String dhRecbto) {
            this.dhRecbto = dhRecbto;
        }

        public String getNProt() {
            return nProt;
        }

        public void setNProt(String nProt) {
            this.nProt = nProt;
        }

        public String getDigVal() {
            return digVal;
        }

        public void setDigVal(String digVal) {
            this.digVal = digVal;
        }

        public String getCStat() {
            return cStat;
        }

        public void setCStat(String cStat) {
            this.cStat = cStat;
        }

        public String getXMotivo() {
            return xMotivo;
        }

        public void setXMotivo(String xMotivo) {
            this.xMotivo = xMotivo;
        }
    }
}