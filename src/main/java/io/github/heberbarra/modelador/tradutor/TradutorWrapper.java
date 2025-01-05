package io.github.heberbarra.modelador.tradutor;

public class TradutorWrapper {

    public static final Tradutor tradutor;

    static {
        TradutorFactory tradutorFactory = new TradutorFactory();
        tradutor = tradutorFactory.criarObjeto();
    }
}
