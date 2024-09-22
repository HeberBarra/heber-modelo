package org.modelador.codigosaida;

public enum CodigoSaida {
    OK(0),
    ERRO_CRIACAO_CONFIG(1),
    ERRO_CONFIGURACOES(2),
    ERRO_CRIACAO_ARQUIVO_TEMP(3),
    ERRO_LEITURA_ARQUIVO(4),
    RECURSO_NAO_ENCONTRADO(5),
    ERRO_CRIACAO_URLS(6),
    ERRO_CONEXAO(7),
    PROTOCOLO_INVALIDO(8),
    ERRO_LEITURA_RESPONSE(9),
    ERRO_CONVERSAO_RESPONSE(10),
    ALGORITMO_INVALIDO(11),
    ERRO_PEGAR_VERSAO(12);

    final int codigo;

    CodigoSaida(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
