package org.modelador.configurador.verificador;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public abstract class JsonVerificador<T extends AtributoJson> {

    @JsonProperty("$schema")
    protected String schema;

    protected List<T> atributos;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n  \"$schema\": \"%s\"".formatted(schema));
        stringBuilder.append("  \"atributos: \": [\n");
        atributos.forEach(atributo -> {
            stringBuilder.append(atributos);

            if (atributos.getLast() != atributo) {
                stringBuilder.append(", ");
            }

            stringBuilder.append("\n");
        });
        stringBuilder.append("  ]\n}");

        return stringBuilder.toString();
    }

    public String getSchema() {
        return schema;
    }

    public List<T> getAtributos() {
        return atributos;
    }
}
