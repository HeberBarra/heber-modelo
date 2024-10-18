package org.modelador.argumento;

import java.util.List;
import org.modelador.banco.EjetorArquivosBanco;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;

public class EjetorArquivosDocker extends Argumento {

    public EjetorArquivosDocker() {
        this.descricao = "Ejeta o arquivo docker-compose.yml de dentro do jar. "
                + "Obs.: caso não existam, os arquivos de configuração serão gerados";
        this.flagsPermitidas = List.of("--eject-docker-compose", "--ejetar-docker-compose");
    }

    @Override
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.criarArquivos();
        configurador.lerConfiguracoes();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarDockerCompose();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
