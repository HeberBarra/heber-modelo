/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.test.coletor;

import io.github.heberbarra.modelador.impressor.ColetorCodigosSaida;
import io.github.heberbarra.modelador.impressor.DadosCodigoSaida;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TestColetorCodigosSaida {

    @Test
    public void testColetorColetaCodigos() {
        ColetorCodigosSaida coletorCodigosSaida = new ColetorCodigosSaida();
        coletorCodigosSaida.coletarCodigosEnum();
        Set<DadosCodigoSaida> dadosCodigoSaida = coletorCodigosSaida.getDadosCodigosSaida();
        assert !dadosCodigoSaida.isEmpty();
    }
}
