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
package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.domain.argumento.Argumento;
import io.github.heberbarra.modelador.domain.codigo.CodigoSaida;
import java.util.List;

/**
 * Baixa a última atualização do programa, se houver.
 *
 * @since v0.0.4-SNAPSHOT
 */
public class AtualizarPrograma extends Argumento {

    public AtualizarPrograma() {
        this.descricao = "Baixa a última versão do programa, se a mesma não for a versão instalada.";
        this.flagsPermitidas = List.of("--atualizar", "--update");
    }

    /**
     * Atualiza o programa para a versão mais recente e encerra o programa,
     * para garantir que o usuário utilize a atualizada
     */
    @Override
    public void run() {
        AtualizadorPrograma atualizador = new AtualizadorPrograma();
        atualizador.baixarAtualizacao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
