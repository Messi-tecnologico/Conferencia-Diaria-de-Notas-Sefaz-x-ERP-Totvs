/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.util;
import com.sefazam.schema.TRetConsSitNFe;
/**
 *
 * @author Messias
 */
public class WebServiceUtil {

    /**
     * Cria XML do status do protocolo da NF-e
     * 
     * @param protNFe Protocolo da NF-e
     * @return XML formatado do protocolo
     */
    public static String criaXmlStatus(TRetConsSitNFe.ProtNFe protNFe) {
        if (protNFe == null || protNFe.getInfProt() == null) {
            return "";
        }

        TRetConsSitNFe.InfProt infProt = protNFe.getInfProt();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<protNFe versao=\"4.00\">\n");
        xml.append("  <infProt>\n");

        if (infProt.getTpAmb() != null) {
            xml.append("    <tpAmb>").append(infProt.getTpAmb()).append("</tpAmb>\n");
        }

        if (infProt.getVerAplic() != null) {
            xml.append("    <verAplic>").append(infProt.getVerAplic()).append("</verAplic>\n");
        }

        if (infProt.getChNFe() != null) {
            xml.append("    <chNFe>").append(infProt.getChNFe()).append("</chNFe>\n");
        }

        if (infProt.getDhRecbto() != null) {
            xml.append("    <dhRecbto>").append(infProt.getDhRecbto()).append("</dhRecbto>\n");
        }

        if (infProt.getNProt() != null) {
            xml.append("    <nProt>").append(infProt.getNProt()).append("</nProt>\n");
        }

        if (infProt.getDigVal() != null) {
            xml.append("    <digVal>").append(infProt.getDigVal()).append("</digVal>\n");
        }

        if (infProt.getCStat() != null) {
            xml.append("    <cStat>").append(infProt.getCStat()).append("</cStat>\n");
        }

        if (infProt.getXMotivo() != null) {
            xml.append("    <xMotivo>").append(escapeXml(infProt.getXMotivo())).append("</xMotivo>\n");
        }

        xml.append("  </infProt>\n");
        xml.append("</protNFe>");

        return xml.toString();
    }

    /**
     * Escapa caracteres especiais XML
     * 
     * @param text Texto a ser escapado
     * @return Texto com caracteres escapados
     */
    private static String escapeXml(String text) {
        if (text == null) {
            return "";
        }

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
