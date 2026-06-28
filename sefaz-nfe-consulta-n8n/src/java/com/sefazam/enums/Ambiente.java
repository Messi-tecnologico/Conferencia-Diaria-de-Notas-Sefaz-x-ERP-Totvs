/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.enums;

/**
 *
 * @author Messias
 */
public enum Ambiente {

    /**
     * Ambiente de Produção (código 1)
     */
    PRODUCAO("1", "Produção"),

    /**
     * Ambiente de Homologação (código 2)
     */
    HOMOLOGACAO("2", "Homologação");

    private final String codigo;
    private final String descricao;

    /**
     * Construtor do enum
     * 
     * @param codigo    Código do ambiente (1=Produção, 2=Homologação)
     * @param descricao Descrição do ambiente
     */
    Ambiente(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    /**
     * Retorna o código do ambiente
     * 
     * @return Código (1 ou 2)
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna a descrição do ambiente
     * 
     * @return Descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a URL do webservice de consulta NF-e para SEFAZ-AM
     * 
     * @return URL do webservice
     */
    public String getUrlConsultaNFe() {
        if (this == PRODUCAO) {
            return "https://nfe.sefaz.am.gov.br/services2/services/NfeConsulta4";
        } else {
            return "https://homnfe.sefaz.am.gov.br/services2/services/NfeConsulta4";
        }
    }

    @Override
    public String toString() {
        return descricao + " (" + codigo + ")";
    }
}

