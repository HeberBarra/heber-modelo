package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.banco.EjetorArquivosBanco;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import java.util.List;

/**
 * Ejeta o arquivo docker compose
 * @since v0.0.4-SNAPSHOT
 * */
public class EjetorArquivosDocker extends Argumento {

    public EjetorArquivosDocker() {
        this.descricao = "Ejeta o arquivo docker-compose.yml de dentro do jar. "
                + "Obs.: caso não existam, os arquivos de configuração serão gerados";
        this.flagsPermitidas = List.of("--eject-docker-compose", "--ejetar-docker-compose");
    }

    /**
     * Ejeta o arquivo docker compose, mas primeiro gera os arquivos de configuração caso não existam,
     * pois é necessário saber para onde copiar o arquivo. Encerra o programa após ejetar o arquivo.
     * */
    @Override
    public void run() {
        ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
        configurador.criarArquivos();
        configurador.lerConfiguracao();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarDockerCompose();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
