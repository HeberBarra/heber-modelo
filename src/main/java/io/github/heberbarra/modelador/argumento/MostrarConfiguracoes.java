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

import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import io.github.heberbarra.modelador.domain.argumento.Argumento;
import java.util.List;

/**
 * Exibe as configurações do programa
 *
 * @since v0.0.4-SNAPSHOT
 */
public class MostrarConfiguracoes extends Argumento {

    public MostrarConfiguracoes() {
        this.descricao = "Mostra as configurações do programa, sem fazer nenhuma modificação. Não encerra o programa";
        this.flagsPermitidas = List.of("--mostrar-config", "--show-config");
    }

    /**
     * Lê e exibe ao usuário as configurações do programa
     */
    @Override
    public void run() {
        ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
        configurador.lerConfiguracao();
        configurador.mostrarConfiguracao();
    }
}
