/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.service;

import com.sefazam.config.SefazConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Messias
 */

/**
 * Serviço de Cache para armazenar os totais da SEFAZ localmente.
 * Isso evita consultas excessivas e lentidão no módulo de reconciliação.
 */
public class SefazCacheService {

    /**
     * Carrega os totais cacheados para uma data específica.
     * 
     * @param dataFormatoYmd Formato YYYYMMDD
     * @return Mapa de Filial-CNPJ -> Valor
     */
    public Map<String, Double> carregarTotais(String dataFormatoYmd) {
        Map<String, Double> totais = new HashMap<>();
        File arquivoCache = getArquivoCache(dataFormatoYmd);

        if (arquivoCache.exists()) {
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream(arquivoCache)) {
                prop.load(fis);
                for (String key : prop.stringPropertyNames()) {
                    totais.put(key, Double.parseDouble(prop.getProperty(key)));
                }
            } catch (Exception e) {
                System.err.println("⚠️ Erro ao ler cache SEFAZ: " + e.getMessage());
            }
        }
        return totais;
    }

    /**
     * Salva os totais no cache local.
     */
    public void salvarTotais(String dataFormatoYmd, Map<String, Double> totais) {
        File pasta = new File(SefazConfig.PATH_CACHE_SEFAZ);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivoCache = getArquivoCache(dataFormatoYmd);
        Properties prop = new Properties();
        for (Map.Entry<String, Double> entry : totais.entrySet()) {
            prop.setProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }

        try (FileOutputStream fos = new FileOutputStream(arquivoCache)) {
            prop.store(fos, "Cache Totais SEFAZ - " + dataFormatoYmd);
        } catch (Exception e) {
            System.err.println("⚠️ Erro ao gravar cache SEFAZ: " + e.getMessage());
        }
    }

    private File getArquivoCache(String dataFormatoYmd) {
        return new File(SefazConfig.PATH_CACHE_SEFAZ, "totais_" + dataFormatoYmd + ".properties");
    }
}

