package org.modelador.argumento;

import java.util.List;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;

public class GerarConfiguracoes extends Argumento {

    public GerarConfiguracoes() {
        super();
        this.descricao = "Cria os arquivos de configuração e encerra o programa";
        this.flagsPermitidas = List.of("--gen-config", "--gerar-config");
    }

    @Override
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.criarArquivos();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
