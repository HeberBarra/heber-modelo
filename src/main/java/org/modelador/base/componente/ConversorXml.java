package org.modelador.base.componente;

import java.lang.reflect.Field;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

public interface ConversorXml {

    default String pegarValorAtributo(Field atributo) {
        Logger logger = JavaLogger.obterLogger(getClass().getName());

        try {
            atributo.setAccessible(true);
            return atributo.get(this).toString();
        } catch (IllegalAccessException e) {
            logger.severe("Falha ao ler valor do atributo: %s. %n%s".formatted(atributo.getName(), e.getMessage()));
        }

        return "";
    }

    default String converterParaStringXml() {
        StringBuilder stringBuilder = new StringBuilder();
        String nomeClasse = this.getClass().getSimpleName();

        stringBuilder.append("<%s>%n".formatted(nomeClasse));

        for (Field field : getClass().getDeclaredFields()) {
            stringBuilder.append("\t<%s>%s".formatted(field.getName(), pegarValorAtributo(field)));
            stringBuilder.append("</%s>%n".formatted(field.getName()));
        }

        stringBuilder.append("</%s>".formatted(nomeClasse));

        return stringBuilder.toString();
    }
}
