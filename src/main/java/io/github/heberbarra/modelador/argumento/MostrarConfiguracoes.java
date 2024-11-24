package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import java.util.List;

/**
 * Exibe as configurações do programa
 * @since v0.0.4-SNAPSHOT
 * */
public class MostrarConfiguracoes extends Argumento {

    public MostrarConfiguracoes() {
        this.descricao = "Mostra as configurações do programa, sem fazer nenhuma modificação. Não encerra o programa";
        this.flagsPermitidas = List.of("--mostrar-config", "--show-config");
    }

    /**
     * Lê e exibe ao usuário as configurações do programa
     * */
    @Override
    public void run() {
        ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
        configurador.lerConfiguracao();
        configurador.mostrarConfiguracao();
    }
}
