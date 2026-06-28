// ========== CONFIGURAÇÃO ==========
const API_BASE_URL = window.location.origin + '/sefaz-nfe-consulta';

// ========== ELEMENTOS DO DOM ==========
const consultaForm = document.getElementById('consultaForm');
const chaveAcessoInput = document.getElementById('chaveAcesso');
const btnConsultar = document.getElementById('btnConsultar');
const resultadoCard = document.getElementById('resultadoCard');
const resultadoContent = document.getElementById('resultadoContent');

// ========== FORMATAÇÃO DA CHAVE DE ACESSO ==========
chaveAcessoInput.addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // Remove não-dígitos

    // Limita a 44 dígitos
    if (value.length > 44) {
        value = value.substring(0, 44);
    }

    // Formata em blocos de 4 dígitos
    let formatted = '';
    for (let i = 0; i < value.length; i++) {
        if (i > 0 && i % 4 === 0) {
            formatted += ' ';
        }
        formatted += value[i];
    }

    e.target.value = formatted;
});

// ========== VALIDAÇÃO ==========
function validarChaveAcesso(chave) {
    const chaveNumeros = chave.replace(/\D/g, '');

    if (chaveNumeros.length !== 44) {
        return {
            valido: false,
            mensagem: 'A chave de acesso deve conter exatamente 44 dígitos.'
        };
    }

    return { valido: true };
}

// ========== SUBMIT DO FORMULÁRIO ==========
consultaForm.addEventListener('submit', async function (e) {
    e.preventDefault();

    const chaveAcesso = chaveAcessoInput.value.trim();

    // Validação
    const validacao = validarChaveAcesso(chaveAcesso);
    if (!validacao.valido) {
        mostrarErro(validacao.mensagem);
        return;
    }

    // Mostra loading
    setLoading(true);

    try {
        // Envia requisição
        const response = await fetch(`${API_BASE_URL}/consulta-nfe`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `chaveAcesso=${encodeURIComponent(chaveAcesso)}`
        });

        const data = await response.json();

        // Exibe resultado
        exibirResultado(data);

    } catch (error) {
        console.error('Erro na requisição:', error);
        mostrarErro('Erro ao conectar com o servidor. Verifique se a aplicação está rodando.');
    } finally {
        setLoading(false);
    }
});

// ========== EXIBIR RESULTADO ==========
function exibirResultado(data) {
    let html = '';

    // Badge de status
    let statusClass = 'status-info';
    let statusIcon = 'ℹ️';

    if (data.sucesso) {
        statusClass = 'status-success';
        statusIcon = '✅';
    } else {
        statusClass = 'status-error';
        statusIcon = '❌';
    }

    html += `<div class="status-badge ${statusClass}">
        ${statusIcon} ${data.situacao || 'Consulta Realizada'}
    </div>`;

    // Informações principais
    html += `<div class="result-item">
        <strong>📋 Código de Status (cStat)</strong>
        <p>${data.codigoStatus || 'N/A'}</p>
    </div>`;

    html += `<div class="result-item">
        <strong>💬 Mensagem da SEFAZ</strong>
        <p>${data.mensagem || 'N/A'}</p>
    </div>`;

    html += `<div class="result-item">
        <strong>🔑 Chave de Acesso</strong>
        <p>${data.chaveAcesso || 'N/A'}</p>
    </div>`;

    // Informações adicionais (se autorizada)
    if (data.sucesso && data.numeroProtocolo) {
        html += `<div class="result-item">
            <strong>📝 Número do Protocolo</strong>
            <p>${data.numeroProtocolo}</p>
        </div>`;
    }

    if (data.dataAutorizacao) {
        html += `<div class="result-item">
            <strong>📅 Data de Autorização</strong>
            <p>${formatarData(data.dataAutorizacao)}</p>
        </div>`;
    }

    if (data.numeroNota) {
        html += `<div class="result-item">
            <strong>🔢 Número da Nota</strong>
            <p>${data.numeroNota} - Série: ${data.serieNota || 'N/A'}</p>
        </div>`;
    }

    if (data.nomeEmitente) {
        html += `<div class="result-item">
            <strong>🏢 Emitente</strong>
            <p>${data.nomeEmitente}</p>
            <p>CNPJ: ${data.cnpjEmitente || 'N/A'}</p>
        </div>`;
    }

    if (data.valorTotal) {
        html += `<div class="result-item">
            <strong>💰 Valor Total</strong>
            <p>R$ ${data.valorTotal}</p>
        </div>`;
    }

    // XML de retorno (se disponível)
    if (data.xmlRetorno) {
        html += `<div class="result-item">
            <strong>📄 XML de Retorno</strong>
            <div class="xml-container">${escapeHtml(data.xmlRetorno)}</div>
        </div>`;
    }

    resultadoContent.innerHTML = html;
    resultadoCard.style.display = 'block';

    // Scroll suave até o resultado
    resultadoCard.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// ========== MOSTRAR ERRO ==========
function mostrarErro(mensagem) {
    const errorData = {
        sucesso: false,
        codigoStatus: 'ERRO',
        mensagem: mensagem,
        situacao: 'ERRO'
    };

    exibirResultado(errorData);
}

// ========== LOADING ==========
function setLoading(isLoading) {
    const btnText = btnConsultar.querySelector('.btn-text');
    const btnLoader = btnConsultar.querySelector('.btn-loader');

    if (isLoading) {
        btnText.style.display = 'none';
        btnLoader.style.display = 'inline';
        btnConsultar.disabled = true;
    } else {
        btnText.style.display = 'inline';
        btnLoader.style.display = 'none';
        btnConsultar.disabled = false;
    }
}

// ========== UTILITÁRIOS ==========
function formatarData(dataISO) {
    try {
        const data = new Date(dataISO);
        return data.toLocaleString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    } catch (e) {
        return dataISO;
    }
}

function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

// ========== INICIALIZAÇÃO ==========
console.log('✅ Sistema de Consulta SEFAZ-AM carregado!');
console.log('📡 API Base URL:', API_BASE_URL);
