/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.util;
import java.io.InputStream;
import java.security.KeyStore;
/**
 *
 * @author Messias
 */
public class CertificadoUtil {

    /**
     * Carrega um certificado digital do tipo A1 (.pfx ou .p12)
     * 
     * @param inputStream Stream do arquivo do certificado
     * @param senha       Senha do certificado
     * @return KeyStore com o certificado carregado
     * @throws Exception em caso de erro ao carregar o certificado
     */
    public static KeyStore getKeyStore(InputStream inputStream, String senha) throws Exception {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream do certificado não pode ser nulo");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha do certificado não pode ser vazia");
        }

        try {
            // Cria instância do KeyStore para certificados PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            // Carrega o certificado com a senha fornecida
            keyStore.load(inputStream, senha.toCharArray());

            System.out.println("✅ Certificado digital carregado com sucesso!");
            System.out.println("   Tipo: PKCS12 (A1)");
            System.out.println("   Aliases: " + keyStore.size());

            return keyStore;

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar certificado digital!");
            System.err.println("   Verifique:");
            System.err.println("   1. Se o arquivo é um certificado válido (.pfx ou .p12)");
            System.err.println("   2. Se a senha está correta");
            System.err.println("   3. Se o certificado não está corrompido");
            throw new Exception("Falha ao carregar certificado: " + e.getMessage(), e);
        }
    }

    /**
     * Carrega um certificado digital do tipo A1 (.pfx ou .p12)
     * Sobrecarga do método para compatibilidade
     * 
     * @param caminhoArquivo Caminho completo do arquivo do certificado
     * @param senha          Senha do certificado
     * @return KeyStore com o certificado carregado
     * @throws Exception em caso de erro ao carregar o certificado
     */
    public static KeyStore getKeyStore(String caminhoArquivo, String senha) throws Exception {
        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream(caminhoArquivo);
            return getKeyStore(fis, senha);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    // Ignora erro ao fechar
                }
            }
        }
    }

    /**
     * Valida se um certificado está dentro do prazo de validade
     * 
     * @param keyStore KeyStore com o certificado
     * @return true se válido, false se expirado
     */
    public static boolean validarCertificado(KeyStore keyStore) {
        try {
            String alias = keyStore.aliases().nextElement();
            java.security.cert.X509Certificate cert = (java.security.cert.X509Certificate) keyStore
                    .getCertificate(alias);

            // Verifica validade
            cert.checkValidity();

            System.out.println("✅ Certificado válido!");
            System.out.println("   Válido até: " + cert.getNotAfter());

            return true;

        } catch (java.security.cert.CertificateExpiredException e) {
            System.err.println("❌ Certificado EXPIRADO!");
            return false;
        } catch (java.security.cert.CertificateNotYetValidException e) {
            System.err.println("❌ Certificado ainda NÃO VÁLIDO!");
            return false;
        } catch (Exception e) {
            System.err.println("❌ Erro ao validar certificado: " + e.getMessage());
            return false;
        }
    }
}