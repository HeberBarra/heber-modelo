package org.modelador.argumento;

import java.util.List;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;

/**
 * Gera os arquivos de configuração do programa
 * @since v0.0.4-SNAPSHOT
 * */
public class GerarConfiguracoes extends Argumento {

    public GerarConfiguracoes() {
        super();
        this.descricao = "Cria os arquivos de configuração e encerra o programa";
        this.flagsPermitidas = List.of("--gen-config", "--gerar-config");
    }

    /**
     * Gera todos os arquivos de configuração do programa, logo após encerra o programa
     * */
    @Override
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.criarArquivos();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
