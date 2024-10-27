package org.modelador.argumento;

import java.util.List;
import org.modelador.banco.EjetorArquivosBanco;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;

/**
 * Ejeta os arquivos de configuração da base de dados
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
    public void executar() {
        Configurador configurador = Configurador.getInstance();
        configurador.lerConfiguracoes();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarScriptsConfiguracao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
