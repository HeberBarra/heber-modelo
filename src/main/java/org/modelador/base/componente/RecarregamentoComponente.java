package org.modelador.base.componente;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public interface RecarregamentoComponente {

    default void recarregar() {
        throw new UnsupportedOperationException("Componente não implementou esse método");
    }

    default void recarregar(Color cor) {
        throw new UnsupportedOperationException("Componente não implementou esse método");
    }

    default void recarregar(List<Color> cores) {
        throw new UnsupportedOperationException("Componente não implementou esse método");
    }

    default void recarregar(Map<String, Color> cores) {
        throw new UnsupportedOperationException("Componente não implementou esse método");
    }
}
