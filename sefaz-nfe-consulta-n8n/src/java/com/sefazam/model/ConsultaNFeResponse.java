/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.model;

/**
 *
 * @author Messias
 */

public class ConsultaNFeResponse {

    private boolean sucesso;
    private String codigoStatus;
    private String mensagem;
    private String chaveAcesso;
    private String xmlRetorno;
    private String numeroProtocolo;
    private String dataAutorizacao;
    private String situacao;

    // Dados da NF-e (quando autorizada)
    private String numeroNota;
    private String serieNota;
    private String cnpjEmitente;
    private String nomeEmitente;
    private String valorTotal;
    private String dataEmissao;
    // Dados do endereço do emitente
    private String enderecoEmitente;

    // Dados do consumidor
    private String nomeConsumidor;
    private String cpfCnpjConsumidor;
    private String enderecoConsumidor;

    // Totais detalhados
    private String valorDesconto;
    private String valorPagar;
    private String totalItens; // Nova: Qtd total de itens

    // Pagamento
    private String formaPagamento; // Nova: "Cartão de Crédito", etc
    private String valorPago; // Nova: Valor total pago
    private String valorTroco; // Nova: Troco

    // Lista de produtos
    private java.util.List<Produto> produtos;

    public ConsultaNFeResponse() {
        this.produtos = new java.util.ArrayList<>();
    }

    public ConsultaNFeResponse(boolean sucesso, String codigoStatus, String mensagem) {
        this();
        this.sucesso = sucesso;
        this.codigoStatus = codigoStatus;
        this.mensagem = mensagem;
    }

    // ========== CLASSE INTERNA: Produto ==========

    public static class Produto {
        private String descricao;
        private String quantidade;
        private String valorUnitario;
        private String valorTotal;
        private String codigo;

        public Produto(String descricao, String quantidade, String valorUnitario, String valorTotal) {
            this.descricao = descricao;
            this.quantidade = quantidade;
            this.valorUnitario = valorUnitario;
            this.valorTotal = valorTotal;
        }

        // Getters e Setters
        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(String quantidade) {
            this.quantidade = quantidade;
        }

        public String getValorUnitario() {
            return valorUnitario;
        }

        public void setValorUnitario(String valorUnitario) {
            this.valorUnitario = valorUnitario;
        }

        public String getValorTotal() {
            return valorTotal;
        }

        public void setValorTotal(String valorTotal) {
            this.valorTotal = valorTotal;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
    }

    // Getters e Setters

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getCodigoStatus() {
        return codigoStatus;
    }

    public void setCodigoStatus(String codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getXmlRetorno() {
        return xmlRetorno;
    }

    public void setXmlRetorno(String xmlRetorno) {
        this.xmlRetorno = xmlRetorno;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(String dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }

    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }

    public String getNomeEmitente() {
        return nomeEmitente;
    }

    public void setNomeEmitente(String nomeEmitente) {
        this.nomeEmitente = nomeEmitente;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getEnderecoEmitente() {
        return enderecoEmitente;
    }

    public void setEnderecoEmitente(String enderecoEmitente) {
        this.enderecoEmitente = enderecoEmitente;
    }

    public String getNomeConsumidor() {
        return nomeConsumidor;
    }

    public void setNomeConsumidor(String nomeConsumidor) {
        this.nomeConsumidor = nomeConsumidor;
    }

    public String getCpfCnpjConsumidor() {
        return cpfCnpjConsumidor;
    }

    public void setCpfCnpjConsumidor(String cpfCnpjConsumidor) {
        this.cpfCnpjConsumidor = cpfCnpjConsumidor;
    }

    public String getEnderecoConsumidor() {
        return enderecoConsumidor;
    }

    public void setEnderecoConsumidor(String enderecoConsumidor) {
        this.enderecoConsumidor = enderecoConsumidor;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(String valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(String valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(String totalItens) {
        this.totalItens = totalItens;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public String getValorTroco() {
        return valorTroco;
    }

    public void setValorTroco(String valorTroco) {
        this.valorTroco = valorTroco;
    }

    public java.util.List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(java.util.List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "ConsultaNFeResponse{" +
                "sucesso=" + sucesso +
                ", codigoStatus='" + codigoStatus + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", chaveAcesso='" + chaveAcesso + '\'' +
                ", situacao='" + situacao + '\'' +
                '}';
    }
}
