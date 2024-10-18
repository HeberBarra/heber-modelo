package org.modelador.argumento;

import java.util.List;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

public abstract class Argumento {

    protected Logger logger;
    protected List<String> flagsPermitidas;
    protected String descricao;

    public Argumento() {
        this.logger = JavaLogger.obterLogger(this.getClass().getName());
    }

    public abstract void executar();

    public boolean contemFlag(String flag) {
        return flagsPermitidas.contains(flag);
    }

    public List<String> getFlagsPermitidas() {
        return flagsPermitidas;
    }

    public void setFlagsPermitidas(List<String> flagsPermitidas) {
        this.flagsPermitidas = flagsPermitidas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
