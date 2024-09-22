package org.modelador.argumento;

import java.util.logging.Logger;
import org.modelador.Principal;
import org.modelador.atualizador.Atualizador;
import org.modelador.banco.EjetorArquivosBanco;
import org.modelador.configurador.Configurador;
import org.modelador.logger.JavaLogger;

public class ExecutadorLogicaArgumentos {

    private static final Logger logger = JavaLogger.obterLogger(ExecutadorLogicaArgumentos.class.getName());
    private final Configurador configurador;

    public ExecutadorLogicaArgumentos() {
        configurador = Principal.configurador;
    }

    public void mostrarVersao() {
        String versao = ExecutadorLogicaArgumentos.class.getPackage().getImplementationVersion();

        if (versao == null) {
            logger.warning("Não foi pegar a versão atual do programa.\n");
            System.exit(1);
        }

        System.out.println(versao);
        System.exit(0);
    }

    public void gerarConfiguracoes() {
        configurador.criarArquivos();
        System.exit(0);
    }

    public void mostrarConfiguracoes() {
        configurador.lerConfiguracoes();
        configurador.mostrarConfiguracoes();
    }

    public void atualizarPrograma() {
        Atualizador atualizador = new Atualizador();
        atualizador.baixarAtualizacao();
        System.exit(0);
    }

    public void ejetarArquivosBancoDados() {
        configurador.criarArquivos();
        configurador.lerConfiguracoes();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarScriptsConfiguracao();
        System.exit(0);
    }

    public void ejetarDockerCompose() {
        configurador.criarArquivos();
        configurador.lerConfiguracoes();
        EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
        ejetorArquivosBanco.ejetarDockerCompose();
        System.exit(0);
    }
}
