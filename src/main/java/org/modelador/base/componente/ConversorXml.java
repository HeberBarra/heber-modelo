package org.modelador.base.componente;

import java.lang.reflect.Field;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

public interface ConversorXml {

    default String pegarValorAtributo(Field atributo) {
        Logger logger = JavaLogger.obterLogger(getClass().getName());

        try {
            atributo.setAccessible(true);
            var valorAtributo = atributo.get(this);

            if (valorAtributo == null) {
                return "";
            }

            return valorAtributo.toString();
        } catch (IllegalAccessException e) {
            logger.severe("Falha ao ler valor do atributo: %s. %n%s".formatted(atributo.getName(), e.getMessage()));
        }

        return "";
    }

    default String pegarAtributosClasses() {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> classe = getClass();
        while (classe != null) {
            for (Field field : classe.getDeclaredFields()) {
                // Ignora as classes que pertencem ao JDK, pois os atributos dessas não podem ser acessados
                if (classe.getClassLoader() == "".getClass().getClassLoader()) break;

                stringBuilder.append("\t<%s>%s".formatted(field.getName(), pegarValorAtributo(field)));
                stringBuilder.append("</%s>%n".formatted(field.getName()));
            }

            classe = classe.getSuperclass();
        }

        return stringBuilder.toString();
    }

    default String converterParaStringXml() {
        StringBuilder stringBuilder = new StringBuilder();
        String nomeClasse = this.getClass().getSimpleName();

        stringBuilder.append("<%s>%n".formatted(nomeClasse));
        stringBuilder.append(pegarAtributosClasses());
        stringBuilder.append("</%s>".formatted(nomeClasse));

        return stringBuilder.toString();
    }
}
