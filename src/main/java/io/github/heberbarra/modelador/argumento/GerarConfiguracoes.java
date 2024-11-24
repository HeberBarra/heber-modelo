package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import java.util.List;

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
    public void run() {
        ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
        configurador.criarArquivos();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
