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
public enum UnidadeFederativa {

    AC("12", "Acre", "AC"),
    AL("27", "Alagoas", "AL"),
    AP("16", "Amapá", "AP"),
    AM("13", "Amazonas", "AM"),
    BA("29", "Bahia", "BA"),
    CE("23", "Ceará", "CE"),
    DF("53", "Distrito Federal", "DF"),
    ES("32", "Espírito Santo", "ES"),
    GO("52", "Goiás", "GO"),
    MA("21", "Maranhão", "MA"),
    MT("51", "Mato Grosso", "MT"),
    MS("50", "Mato Grosso do Sul", "MS"),
    MG("31", "Minas Gerais", "MG"),
    PA("15", "Pará", "PA"),
    PB("25", "Paraíba", "PB"),
    PR("41", "Paraná", "PR"),
    PE("26", "Pernambuco", "PE"),
    PI("22", "Piauí", "PI"),
    RJ("33", "Rio de Janeiro", "RJ"),
    RN("24", "Rio Grande do Norte", "RN"),
    RS("43", "Rio Grande do Sul", "RS"),
    RO("11", "Rondônia", "RO"),
    RR("14", "Roraima", "RR"),
    SC("42", "Santa Catarina", "SC"),
    SP("35", "São Paulo", "SP"),
    SE("28", "Sergipe", "SE"),
    TO("17", "Tocantins", "TO");

    private final String codigo;
    private final String nome;
    private final String sigla;

    /**
     * Construtor do enum
     * 
     * @param codigo Código IBGE da UF
     * @param nome   Nome completo do estado
     * @param sigla  Sigla do estado
     */
    UnidadeFederativa(String codigo, String nome, String sigla) {
        this.codigo = codigo;
        this.nome = nome;
        this.sigla = sigla;
    }

    /**
     * Retorna o código IBGE da UF
     * 
     * @return Código IBGE
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o nome completo do estado
     * 
     * @return Nome do estado
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a sigla do estado
     * 
     * @return Sigla (2 letras)
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Busca UF pela sigla
     * 
     * @param sigla Sigla do estado (ex: "AM")
     * @return UnidadeFederativa correspondente
     * @throws IllegalArgumentException se sigla não for encontrada
     */
    public static UnidadeFederativa porSigla(String sigla) {
        for (UnidadeFederativa uf : values()) {
            if (uf.getSigla().equalsIgnoreCase(sigla)) {
                return uf;
            }
        }
        throw new IllegalArgumentException("UF não encontrada: " + sigla);
    }

    /**
     * Busca UF pelo código IBGE
     * 
     * @param codigo Código IBGE
     * @return UnidadeFederativa correspondente
     * @throws IllegalArgumentException se código não for encontrado
     */
    public static UnidadeFederativa porCodigo(String codigo) {
        for (UnidadeFederativa uf : values()) {
            if (uf.getCodigo().equals(codigo)) {
                return uf;
            }
        }
        throw new IllegalArgumentException("UF não encontrada com código: " + codigo);
    }

    @Override
    public String toString() {
        return sigla + " - " + nome;
    }
}
