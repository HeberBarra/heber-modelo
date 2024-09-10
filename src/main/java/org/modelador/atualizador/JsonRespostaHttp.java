package org.modelador.atualizador;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonRespostaHttp {

    private String name;
    private Map<String, Object> outrosAtributos;

    public JsonRespostaHttp() {
        outrosAtributos = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getOutrosAtributos() {
        return outrosAtributos;
    }

    public void setOutrosAtributos(Map<String, Object> outrosAtributos) {
        this.outrosAtributos = outrosAtributos;
    }

    @JsonAnySetter
    public void setAtributo(String chave, Object valor) {
        outrosAtributos.put(chave, valor);
    }
}
