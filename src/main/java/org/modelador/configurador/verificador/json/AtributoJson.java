package org.modelador.configurador.verificador.json;

import java.util.Map;

/**
 * Define um atributo da configuração do programa, que pode ser mapeado de um arquivo JSON
 * @since v0.0.2-SNAPSHOT
 * */
public abstract class AtributoJson {

    protected static final int TAMANHO_INDENTACAO = 2;
    protected static final int NIVEL_INDENTACAO = 3;

    /**
     * Converte as informações do atributo para um {@link Map}
     * @return um {@link Map} que contém as informações do atributo
     * */
    public abstract Map<String, String> converterParaMap();
}
