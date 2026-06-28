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
import java.util.ArrayList;
import java.util.List;

/**
 * Classes para representar dados detalhados da NF-e
 * Extensão do TRetConsSitNFe
 */
public class NFeDados {

    // ========== CLASSE: ProcNFe (Processo da NF-e) ==========

    public static class ProcNFe {
        private NFe nfe;

        public NFe getNfe() {
            return nfe;
        }

        public void setNfe(NFe nfe) {
            this.nfe = nfe;
        }
    }

    // ========== CLASSE: NFe ==========

    public static class NFe {
        private InfNFe infNFe;

        public InfNFe getInfNFe() {
            return infNFe;
        }

        public void setInfNFe(InfNFe infNFe) {
            this.infNFe = infNFe;
        }
    }

    // ========== CLASSE: InfNFe (Informações da NF-e) ==========

    public static class InfNFe {
        private Ide ide;
        private Emit emit;
        private Dest dest;
        private List<Det> det;
        private Total total;
        private Pag pag; // Nova: Informações de pagamento

        public InfNFe() {
            this.det = new ArrayList<>();
        }

        public Ide getIde() {
            return ide;
        }

        public void setIde(Ide ide) {
            this.ide = ide;
        }

        public Emit getEmit() {
            return emit;
        }

        public void setEmit(Emit emit) {
            this.emit = emit;
        }

        public Dest getDest() {
            return dest;
        }

        public void setDest(Dest dest) {
            this.dest = dest;
        }

        public List<Det> getDet() {
            return det;
        }

        public void setDet(List<Det> det) {
            this.det = det;
        }

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public Pag getPag() {
            return pag;
        }

        public void setPag(Pag pag) {
            this.pag = pag;
        }
    }

    // ========== CLASSE: Ide (Identificação) ==========

    public static class Ide {
        private String nNF; // Número da nota
        private String serie; // Série
        private String dhEmi; // Data/hora emissão

        public String getNNF() {
            return nNF;
        }

        public void setNNF(String nNF) {
            this.nNF = nNF;
        }

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }

        public String getDhEmi() {
            return dhEmi;
        }

        public void setDhEmi(String dhEmi) {
            this.dhEmi = dhEmi;
        }
    }

    // ========== CLASSE: Emit (Emitente) ==========

    public static class Emit {
        private String cnpj;
        private String xNome;
        private EnderEmit enderEmit;

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getXNome() {
            return xNome;
        }

        public void setXNome(String xNome) {
            this.xNome = xNome;
        }

        public EnderEmit getEnderEmit() {
            return enderEmit;
        }

        public void setEnderEmit(EnderEmit enderEmit) {
            this.enderEmit = enderEmit;
        }
    }

    // ========== CLASSE: EnderEmit (Endereço do Emitente) ==========

    public static class EnderEmit {
        private String xLgr; // Logradouro
        private String nro; // Número
        private String xBairro; // Bairro
        private String xMun; // Município
        private String uf; // UF
        private String cep; // CEP

        public String getXLgr() {
            return xLgr;
        }

        public void setXLgr(String xLgr) {
            this.xLgr = xLgr;
        }

        public String getNro() {
            return nro;
        }

        public void setNro(String nro) {
            this.nro = nro;
        }

        public String getXBairro() {
            return xBairro;
        }

        public void setXBairro(String xBairro) {
            this.xBairro = xBairro;
        }

        public String getXMun() {
            return xMun;
        }

        public void setXMun(String xMun) {
            this.xMun = xMun;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        /**
         * Retorna endereço formatado
         */
        public String getEnderecoCompleto() {
            StringBuilder sb = new StringBuilder();
            if (xLgr != null)
                sb.append(xLgr);
            if (nro != null)
                sb.append(", ").append(nro);
            if (xBairro != null)
                sb.append(" - ").append(xBairro);
            if (xMun != null && uf != null)
                sb.append(" - ").append(xMun).append("/").append(uf);
            if (cep != null)
                sb.append(" - CEP: ").append(cep);
            return sb.toString();
        }
    }

    // ========== CLASSE: Dest (Destinatário/Consumidor) ==========

    public static class Dest {
        private String cpf;
        private String cnpj;
        private String xNome;
        private EnderDest enderDest;

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getXNome() {
            return xNome;
        }

        public void setXNome(String xNome) {
            this.xNome = xNome;
        }

        public EnderDest getEnderDest() {
            return enderDest;
        }

        public void setEnderDest(EnderDest enderDest) {
            this.enderDest = enderDest;
        }

        /**
         * Retorna CPF ou CNPJ
         */
        public String getCpfCnpj() {
            return cpf != null ? cpf : cnpj;
        }
    }

    // ========== CLASSE: EnderDest (Endereço do Destinatário) ==========

    public static class EnderDest {
        private String xLgr; // Logradouro
        private String nro; // Número
        private String xBairro; // Bairro
        private String xMun; // Município
        private String uf; // UF
        private String cep; // CEP

        public String getXLgr() {
            return xLgr;
        }

        public void setXLgr(String xLgr) {
            this.xLgr = xLgr;
        }

        public String getNro() {
            return nro;
        }

        public void setNro(String nro) {
            this.nro = nro;
        }

        public String getXBairro() {
            return xBairro;
        }

        public void setXBairro(String xBairro) {
            this.xBairro = xBairro;
        }

        public String getXMun() {
            return xMun;
        }

        public void setXMun(String xMun) {
            this.xMun = xMun;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getEnderecoCompleto() {
            StringBuilder sb = new StringBuilder();
            if (xLgr != null)
                sb.append(xLgr);
            if (nro != null)
                sb.append(", ").append(nro);
            if (xBairro != null)
                sb.append(" - ").append(xBairro);
            if (xMun != null && uf != null)
                sb.append(" - ").append(xMun).append("/").append(uf);
            if (cep != null)
                sb.append(" - CEP: ").append(cep);
            return sb.toString();
        }
    }

    // ========== CLASSE: Det (Detalhe do Produto) ==========

    public static class Det {
        private String nItem;
        private Prod prod;

        public String getNItem() {
            return nItem;
        }

        public void setNItem(String nItem) {
            this.nItem = nItem;
        }

        public Prod getProd() {
            return prod;
        }

        public void setProd(Prod prod) {
            this.prod = prod;
        }
    }

    // ========== CLASSE: Prod (Produto) ==========

    public static class Prod {
        private String xProd; // Descrição
        private String qCom; // Quantidade
        private String vUnCom; // Valor unitário
        private String vProd; // Valor total do produto

        public String getXProd() {
            return xProd;
        }

        public void setXProd(String xProd) {
            this.xProd = xProd;
        }

        public String getQCom() {
            return qCom;
        }

        public void setQCom(String qCom) {
            this.qCom = qCom;
        }

        public String getVUnCom() {
            return vUnCom;
        }

        public void setVUnCom(String vUnCom) {
            this.vUnCom = vUnCom;
        }

        public String getVProd() {
            return vProd;
        }

        public void setVProd(String vProd) {
            this.vProd = vProd;
        }
    }

    // ========== CLASSE: Total (Totais da NF-e) ==========

    public static class Total {
        private ICMSTot icmsTot;

        public ICMSTot getIcmsTot() {
            return icmsTot;
        }

        public void setIcmsTot(ICMSTot icmsTot) {
            this.icmsTot = icmsTot;
        }
    }

    // ========== CLASSE: ICMSTot (Totais de ICMS) ==========

    public static class ICMSTot {
        private String vProd; // Valor total dos produtos e serviços
        private String vDesc; // Valor do desconto
        private String vNF; // Valor total da NF-e (Valor a pagar)

        public String getVProd() {
            return vProd;
        }

        public void setVProd(String vProd) {
            this.vProd = vProd;
        }

        public String getVNF() {
            return vNF;
        }

        public void setVNF(String vNF) {
            this.vNF = vNF;
        }

        public String getVDesc() {
            return vDesc;
        }

        public void setVDesc(String vDesc) {
            this.vDesc = vDesc;
        }
    }
    // ========== CLASSE: Pag (Pagamento) ==========

    public static class Pag {
        private List<DetPag> detPag;
        private String vTroco;

        public Pag() {
            this.detPag = new ArrayList<>();
        }

        public List<DetPag> getDetPag() {
            return detPag;
        }

        public void setDetPag(List<DetPag> detPag) {
            this.detPag = detPag;
        }

        public String getVTroco() {
            return vTroco;
        }

        public void setVTroco(String vTroco) {
            this.vTroco = vTroco;
        }
    }

    // ========== CLASSE: DetPag (Detalhe do Pagamento) ==========

    public static class DetPag {
        private String tPag; // Tipo de pagamento
        private String vPag; // Valor do pagamento

        public String getTPag() {
            return tPag;
        }

        public void setTPag(String tPag) {
            this.tPag = tPag;
        }

        public String getVPag() {
            return vPag;
        }

        public void setVPag(String vPag) {
            this.vPag = vPag;
        }
    }
}
