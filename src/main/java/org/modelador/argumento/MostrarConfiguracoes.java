package org.modelador.argumento;

import java.util.List;
import org.modelador.configurador.Configurador;

public class MostrarConfiguracoes extends Argumento {

    public MostrarConfiguracoes() {
        this.descricao = "Mostra as configurações do programa, sem fazer nenhuma modificação. Não encerra o programa";
        this.flagsPermitidas = List.of("--mostrar-config", "--show-config");
    }

    @Override
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.lerConfiguracoes();
        configurador.mostrarConfiguracoes();
    }
}
