package org.modelador.argumento;

import java.util.List;
import org.modelador.configurador.Configurador;

/**
 * Exibe as configurações do programa
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
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.lerConfiguracoes();
        configurador.mostrarConfiguracoes();
    }
}
