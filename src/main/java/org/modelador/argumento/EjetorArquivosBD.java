package org.modelador.argumento;

import java.util.List;
import org.modelador.banco.EjetorArquivosBanco;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;

public class EjetorArquivosBD extends Argumento {

    public EjetorArquivosBD() {
        this.descricao = "Ejeta os arquivos de criação e configuração do banco de dados de dentro do jar."
                + "Obs.: caso não existam, os arquivos de configuração serão gerados";
        this.flagsPermitidas = List.of("--eject-database-scripts", "--ejetar-arquivos-banco");
    }

    @Override
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.lerConfiguracoes();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarScriptsConfiguracao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
