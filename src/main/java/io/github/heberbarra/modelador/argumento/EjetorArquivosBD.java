package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.banco.EjetorArquivosBanco;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import java.util.List;

/**
 * Ejeta os arquivos de configuração da base de dados
 * @since v0.0.4-SNAPSHOT
 * */
public class EjetorArquivosBD extends Argumento {

    public EjetorArquivosBD() {
        this.descricao = "Ejeta os arquivos de criação e configuração do banco de dados de dentro do jar."
                + "Obs.: caso não existam, os arquivos de configuração serão gerados";
        this.flagsPermitidas = List.of("--eject-database-scripts", "--ejetar-arquivos-banco");
    }

    /**
     * Ejeta os arquivos de configuração da base de dados, mas primeiro gera os arquivos de configuração caso não
     * existam, pois é necessário saber o destino dos arquivos ejetados. Encerra o programa após ejetar os arquivos
     * */
    @Override
    public void run() {
        ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
        configurador.lerConfiguracao();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarScriptsConfiguracao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
