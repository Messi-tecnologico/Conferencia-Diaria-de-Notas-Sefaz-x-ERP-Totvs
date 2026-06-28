/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sefazam.service;

/**
 *
 * @author Messias
 */

//import br.com.swconsultoria.nfe.NFe;
//import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
//import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
//import br.com.swconsultoria.nfe.util.WebServiceUtil;

import com.sefazam.nfe.NFe;
import com.sefazam.config.ConfiguracoesNfe;
import com.sefazam.schema.TRetConsSitNFe;
import com.sefazam.util.WebServiceUtil;
import com.sefazam.config.SefazConfig;
import com.sefazam.schema.NFeDados;
import com.sefazam.model.ConsultaNFeResponse;

/**
 * Serviço para consulta de NF-e na SEFAZ-AM
 * Contém a lógica de negócio para comunicação com a SEFAZ
 */
public class ConsultaNFeService {

    /**
     * Consulta uma NF-e pela chave de acesso
     * 
     * @param chaveAcesso Chave de acesso de 44 dígitos
     * @return ConsultaNFeResponse com os dados da consulta
     */
    public ConsultaNFeResponse consultarNotaFiscalNova(String chaveAcesso) {
        System.out.println("🚀 VERSÃO COM DISTRIBUIÇÃO RODANDO! (MÉTODO NOVO)"); // LOG DE DEBUG DA VERSÃO
        ConsultaNFeResponse response = new ConsultaNFeResponse();
        response.setChaveAcesso(chaveAcesso);

        try {
            // Remove espaços e caracteres especiais da chave
            chaveAcesso = chaveAcesso.replaceAll("[^0-9]", "");

            // Valida a chave de acesso
            if (!validarChaveAcesso(chaveAcesso)) {
                response.setSucesso(false);
                response.setMensagem("Chave de acesso inválida. Deve conter 44 dígitos.");
                return response;
            }

            // Obtém as configurações da SEFAZ
            ConfiguracoesNfe config = SefazConfig.getConfiguracoes();

            // INÍCIO DA CONSULTA UNIFICADA
            System.out.println("🔍 Iniciando consulta de dados da NF-e...");
            TRetConsSitNFe retorno = null;

            // 1. DISTRIBUIÇÃO DFe: Para obter o XML completo (Itens, Pagamentos, Endereço)
            try {
                retorno = com.sefazam.nfe.NFeDistribuicao.baixarNfePorChave(config, chaveAcesso);
            } catch (Exception e) {
                System.out.println("⚠️ Distribuição não disponível momentaneamente: " + e.getMessage());
            }

            // 2. CONSULTA PROTOCOLO: Para garantir o Status e dados básicos (indispensável
            // se a Distr. falhar ou vier vazia)
            if (retorno == null || retorno.getProcNFe() == null) {
                System.out.println("📑 Obtendo situação cadastral no servidor da SEFAZ Amazonas...");
                TRetConsSitNFe retStatus = NFe.consultaXml(config, chaveAcesso);

                if (retorno == null) {
                    retorno = retStatus;
                } else if (retStatus != null && retStatus.getProtNFe() != null) {
                    // Mantém o que já temos (eventual XML de distribuição) mas garante o
                    // protocolo/status
                    retorno.setProtNFe(retStatus.getProtNFe());
                    retorno.setCStat(retStatus.getCStat());
                    retorno.setXMotivo(retStatus.getXMotivo());
                }
            }

            // Processa o retorno (seja do download ou da consulta simples)
            processarRetorno(retorno, response);

        } catch (Exception e) {
            System.err.println("❌ Erro ao consultar NF-e: " + e.getMessage());
            e.printStackTrace();

            response.setSucesso(false);
            response.setCodigoStatus("999");
            response.setMensagem("Erro ao consultar NF-e: " + e.getMessage());
        }

        return response;
    }

    /**
     * Processa o retorno da SEFAZ e popula o objeto de resposta
     * 
     * @param retorno  Retorno da SEFAZ
     * @param response Objeto de resposta a ser populado
     */
    private void processarRetorno(TRetConsSitNFe retorno, ConsultaNFeResponse response) throws Exception {
        String cStat = retorno.getCStat();
        String xMotivo = retorno.getXMotivo();

        response.setCodigoStatus(cStat);
        response.setMensagem(xMotivo);

        System.out.println("📋 Código de Retorno (cStat): " + cStat);
        System.out.println("📋 Motivo (xMotivo): " + xMotivo);

        // Códigos de sucesso
        if ("100".equals(cStat)) {
            // 100: Autorizado o Uso da NF-e
            response.setSucesso(true);
            response.setSituacao("AUTORIZADA");
            extrairDadosNFe(retorno, response);

            System.out.println("✅ NF-e AUTORIZADA!");

        } else if ("150".equals(cStat)) {
            // 150: Autorizado o Uso da NF-e, com divergências
            response.setSucesso(true);
            response.setSituacao("AUTORIZADA COM DIVERGÊNCIAS");
            extrairDadosNFe(retorno, response);

            System.out.println("⚠️ NF-e AUTORIZADA COM DIVERGÊNCIAS!");

        } else if ("101".equals(cStat)) {
            // 101: Cancelamento de NF-e homologado
            response.setSucesso(true);
            response.setSituacao("CANCELADA");

            System.out.println("⚠️ NF-e CANCELADA!");

        } else if ("110".equals(cStat)) {
            // 110: Uso Denegado
            response.setSucesso(false);
            response.setSituacao("DENEGADA");

            System.out.println("❌ NF-e DENEGADA!");

        } else if ("217".equals(cStat)) {
            // 217: NF-e não consta na base de dados da SEFAZ
            response.setSucesso(false);
            response.setSituacao("NÃO ENCONTRADA");

            System.out.println("⚠️ NF-e NÃO ENCONTRADA na base da SEFAZ!");

        } else {
            // Outros erros
            response.setSucesso(false);
            response.setSituacao("ERRO");

            System.err.println("❌ ERRO na consulta: " + xMotivo);
        }
    }

    /**
     * Extrai dados da NF-e do retorno da SEFAZ
     * 
     * @param retorno  Retorno da SEFAZ
     * @param response Objeto de resposta
     */
    private void extrairDadosNFe(TRetConsSitNFe retorno, ConsultaNFeResponse response) throws Exception {
        if (retorno.getProtNFe() != null) {
            // Número do protocolo
            if (retorno.getProtNFe().getInfProt() != null) {
                response.setNumeroProtocolo(retorno.getProtNFe().getInfProt().getNProt());

                if (retorno.getProtNFe().getInfProt().getDhRecbto() != null) {
                    response.setDataAutorizacao(retorno.getProtNFe().getInfProt().getDhRecbto());
                }
            }

            // XML completo do protocolo
            String xmlProtocolo = WebServiceUtil.criaXmlStatus(retorno.getProtNFe());
            response.setXmlRetorno(xmlProtocolo);
        }

        // Extrai dados detalhados da NF-e
        NFeDados.ProcNFe procNFe = retorno.getProcNFe();

        System.out.println("📦 Verificando dados detalhados (procNFe)...");
        if (procNFe == null) {
            System.out.println("❌ procNFe é NULL! A SEFAZ retornou apenas o status, sem o XML da nota.");
            System.out.println("   --> Por isso os produtos não aparecem na tela.");
        } else {
            System.out.println("✅ procNFe ENCONTRADO! Extraindo produtos...");
        }

        // TRATAMENTO DE DADOS: Se não houver XML completo (ex: notas > 90 dias ou
        // apenas resumo),
        // extraímos dados básicos da Chave de Acesso para compor a resposta.
        if (procNFe == null && response.getChaveAcesso() != null && response.getChaveAcesso().length() == 44) {
            String chave = response.getChaveAcesso();

            // 1. Número e Série (da Chave: série pos 23-25, número pos 26-34)
            response.setSerieNota(chave.substring(22, 25));
            response.setNumeroNota(chave.substring(25, 34).replaceFirst("^0+(?!$)", ""));

            // 2. Data aproximada (usamos a do protocolo)
            if (response.getDataAutorizacao() != null) {
                response.setDataEmissao(response.getDataAutorizacao());
            }

            // 3. Emitente
            String cnpjEmitente = chave.substring(6, 20);
            response.setCnpjEmitente(cnpjEmitente);

            // Verifica se é uma de nossas filiais para dar um nome amigável
            if (com.sefazam.config.SefazConfig.isCnpjAutorizado(cnpjEmitente)) {
                response.setNomeEmitente("MIR IMPORTACAO / RAMSONS (Filial)");
            } else {
                response.setNomeEmitente("Emitente não identificado (Nota > 90 dias)");
            }

            // 4. Consumidor
            response.setNomeConsumidor("Não identificado (Nota > 90 dias)");

            // Explica o motivo do procNFe ser nulo (prazo de 90 dias)
            response.setMensagem(
                    "Itens não disponíveis: A data de emissão ultrapassou o limite de 90 dias da SEFAZ para download do XML completo.");
        }

        if (procNFe != null && procNFe.getNfe() != null && procNFe.getNfe().getInfNFe() != null) {
            NFeDados.InfNFe infNFe = procNFe.getNfe().getInfNFe();

            // 1. Dados da Nota
            if (infNFe.getIde() != null) {
                response.setNumeroNota(infNFe.getIde().getNNF());
                response.setSerieNota(infNFe.getIde().getSerie());
                response.setDataEmissao(infNFe.getIde().getDhEmi());
            }

            // 2. Dados do Emitente
            if (infNFe.getEmit() != null) {
                response.setCnpjEmitente(infNFe.getEmit().getCnpj());
                response.setNomeEmitente(infNFe.getEmit().getXNome());

                if (infNFe.getEmit().getEnderEmit() != null) {
                    response.setEnderecoEmitente(infNFe.getEmit().getEnderEmit().getEnderecoCompleto());
                }
            }

            // 3. Dados do Consumidor
            if (infNFe.getDest() != null) {
                response.setNomeConsumidor(infNFe.getDest().getXNome());
                response.setCpfCnpjConsumidor(infNFe.getDest().getCpfCnpj());

                if (infNFe.getDest().getEnderDest() != null) {
                    response.setEnderecoConsumidor(infNFe.getDest().getEnderDest().getEnderecoCompleto());
                }
            }

            // 4. Totais
            if (infNFe.getTotal() != null && infNFe.getTotal().getIcmsTot() != null) {
                NFeDados.ICMSTot icmsTot = infNFe.getTotal().getIcmsTot();
                response.setValorTotal(icmsTot.getVProd()); // Valor Bruto (Produtos)
                response.setValorDesconto(icmsTot.getVDesc()); // Descontos
                response.setValorPagar(icmsTot.getVNF()); // Valor Líquido (A Pagar)
            }

            // 5. Produtos
            if (infNFe.getDet() != null) {
                // Define a quantidade total de itens (linhas da nota)
                response.setTotalItens(String.valueOf(infNFe.getDet().size()));

                for (NFeDados.Det det : infNFe.getDet()) {
                    if (det.getProd() != null) {
                        ConsultaNFeResponse.Produto produto = new ConsultaNFeResponse.Produto(
                                det.getProd().getXProd(),
                                det.getProd().getQCom(),
                                det.getProd().getVUnCom(),
                                det.getProd().getVProd());
                        // Tenta definir o código (nItem)
                        produto.setCodigo(det.getNItem());

                        response.getProdutos().add(produto);
                    }
                }
            }

            // 6. Pagamento (Novo)
            if (infNFe.getPag() != null) {
                response.setValorTroco(infNFe.getPag().getVTroco());

                if (infNFe.getPag().getDetPag() != null && !infNFe.getPag().getDetPag().isEmpty()) {
                    // Pega a primeira forma de pagamento (comum ser uma só)
                    NFeDados.DetPag detPag = infNFe.getPag().getDetPag().get(0);
                    response.setFormaPagamento(mapearFormaPagamento(detPag.getTPag()));
                    response.setValorPago(detPag.getVPag());
                }
            }
        }
    }

    /**
     * Mapeia o código da forma de pagamento da SEFAZ para texto
     */
    private String mapearFormaPagamento(String codigo) {
        if (codigo == null)
            return "Não informado";
        switch (codigo) {
            case "01":
                return "Dinheiro";
            case "02":
                return "Cheque";
            case "03":
                return "Cartão de Crédito";
            case "04":
                return "Cartão de Débito";
            case "05":
                return "Crédito Loja";
            case "10":
                return "Vale Alimentação";
            case "11":
                return "Vale Refeição";
            case "12":
                return "Vale Presente";
            case "13":
                return "Vale Combustível";
            case "15":
                return "Boleto Bancário";
            case "90":
                return "Sem pagamento";
            default:
                return "Outros (" + codigo + ")";
        }
    }

    /**
     * Valida se a chave de acesso está no formato correto
     * 
     * @param chaveAcesso Chave a ser validada
     * @return true se válida, false caso contrário
     */
    private boolean validarChaveAcesso(String chaveAcesso) {
        if (chaveAcesso == null || chaveAcesso.length() != 44) {
            return false;
        }

        // Verifica se contém apenas números
        return chaveAcesso.matches("\\d{44}");
    }

    /**
     * Formata a chave de acesso para exibição
     * Formato: 9999 9999 9999 9999 9999 9999 9999 9999 9999 9999 9999
     * 
     * @param chaveAcesso Chave sem formatação
     * @return Chave formatada
     */
    public static String formatarChaveAcesso(String chaveAcesso) {
        if (chaveAcesso == null || chaveAcesso.length() != 44) {
            return chaveAcesso;
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < chaveAcesso.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(" ");
            }
            formatted.append(chaveAcesso.charAt(i));
        }
        return formatted.toString();
    }
}