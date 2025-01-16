/**
 * Copyright (C) 2025 Heber Ferreira Barra, João Gabriel de Cristo, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.impressor.ColetorCodigosSaida;
import io.github.heberbarra.modelador.impressor.ImpressorCodigosSaida;
import java.util.List;

public class ImprimirCodigosSaida extends Argumento {

    public ImprimirCodigosSaida() {
        this.descricao = "Imprimi no stdout o nome e o número de cada código de saída";
        this.flagsPermitidas = List.of("--imprimir-codigos-saida", "--print-exit-codes");
    }

    @Override
    public void run() {
        ColetorCodigosSaida coletorCodigosSaida = new ColetorCodigosSaida();
        ImpressorCodigosSaida impressorCodigosSaida = new ImpressorCodigosSaida(coletorCodigosSaida);
        impressorCodigosSaida.imprimirCodigos();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
