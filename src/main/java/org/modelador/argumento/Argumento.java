package org.modelador.argumento;

import java.util.List;
import java.util.Map;

public class Argumento {

    private List<String> flagsPermitidas;
    private String descricao;

    public Argumento(List<String> flagsPermitidas, String descricao) {
        this.flagsPermitidas = flagsPermitidas;
        this.descricao = descricao;
    }

    public void autoRegistrar(Map<String, Argumento> mapArgumentos) {
        for (String flag : flagsPermitidas) {
            mapArgumentos.put(flag, this);
        }
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
