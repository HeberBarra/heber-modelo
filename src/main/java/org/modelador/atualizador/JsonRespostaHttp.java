package org.modelador.atualizador;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utilizado para mapear uma resposta HTTP para uma classe.
 * @since v0.0.3-SNAPSHOT
 * */
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
