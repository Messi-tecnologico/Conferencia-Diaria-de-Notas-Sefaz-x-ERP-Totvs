/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.config;

/**
 *
 * @author Messias
 */
import com.sefazam.enums.Ambiente;
import com.sefazam.enums.UnidadeFederativa;

import java.security.KeyStore;

/**
 * Classe de configuração para comunicação com SEFAZ
 * Substitui: br.com.swconsultoria.nfe.dom.ConfiguracoesNfe
 */
public class ConfiguracoesNfe {

    private UnidadeFederativa uf;
    private Ambiente ambiente;
    private KeyStore keyStore;
    private String senhaCertificado;
    private boolean ajustaAutorizacao;

    /**
     * Construtor privado - use o método estático iniciaConfiguracoes()
     */
    private ConfiguracoesNfe() {
    }

    /**
     * Inicializa as configurações da SEFAZ
     * 
     * @param uf               Unidade Federativa
     * @param ambiente         Ambiente (PRODUCAO ou HOMOLOGACAO)
     * @param keyStore         KeyStore com o certificado digital
     * @param senhaCertificado Senha do certificado
     * @return Instância configurada de ConfiguracoesNfe
     */
    public static ConfiguracoesNfe iniciaConfiguracoes(
            UnidadeFederativa uf,
            Ambiente ambiente,
            KeyStore keyStore,
            String senhaCertificado) {

        ConfiguracoesNfe config = new ConfiguracoesNfe();
        config.uf = uf;
        config.ambiente = ambiente;
        config.keyStore = keyStore;
        config.senhaCertificado = senhaCertificado;
        config.ajustaAutorizacao = false;

        return config;
    }

    // ========== GETTERS E SETTERS ==========

    public UnidadeFederativa getUf() {
        return uf;
    }

    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public String getSenhaCertificado() {
        return senhaCertificado;
    }

    public void setSenhaCertificado(String senhaCertificado) {
        this.senhaCertificado = senhaCertificado;
    }

    public boolean isAjustaAutorizacao() {
        return ajustaAutorizacao;
    }

    public void setAjustaAutorizacao(boolean ajustaAutorizacao) {
        this.ajustaAutorizacao = ajustaAutorizacao;
    }

    @Override
    public String toString() {
        return "ConfiguracoesNfe{" +
                "uf=" + uf +
                ", ambiente=" + ambiente +
                ", certificado=" + (keyStore != null ? "carregado" : "não carregado") +
                ", ajustaAutorizacao=" + ajustaAutorizacao +
                '}';
    }
}