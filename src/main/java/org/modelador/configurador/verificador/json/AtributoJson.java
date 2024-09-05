package org.modelador.configurador.verificador.json;

import java.util.Map;

public abstract class AtributoJson {

    protected static final int TAMANHO_INDENTACAO = 2;
    protected static final int NIVEL_INDENTACAO = 3;

    public abstract Map<String, String> converterParaMap();
}
