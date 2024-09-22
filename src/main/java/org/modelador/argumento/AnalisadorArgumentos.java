package org.modelador.argumento;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

public class AnalisadorArgumentos {

    private static final Logger logger = JavaLogger.obterLogger(AnalisadorArgumentos.class.getName());
    private final Map<String, Argumento> argumentosPermitidos;
    private final String[] argumentosPassados;
    private final ExecutadorLogicaArgumentos executadorLogicaArgumentos;

    public AnalisadorArgumentos(String[] args) {
        argumentosPassados = args;
        argumentosPermitidos = new LinkedHashMap<>();
        executadorLogicaArgumentos = new ExecutadorLogicaArgumentos();
        adicionarArgumentosPermitidos();
    }

    protected void adicionarArgumentosPermitidos() {
        Argumento mostrarVersao =
                new Argumento(List.of("--version", "--versao", "-v"), "Mostra a versão atual do programa");
        Argumento gerarConfig = new Argumento(
                List.of("--gen-config", "--gerar-config"),
                "Cria os arquivos de configuração, sem executar as demais partes do programa");
        Argumento mostrarConfig = new Argumento(
                List.of("--show-config", "--mostrar-config"),
                "Mostrar as configurações atuais do programa sem fazer nenhuma atualização ou mudança");
        Argumento atualizar = new Argumento(
                List.of("--update", "--atualizar"),
                "Baixa automaticamente a última versão programa se não for a versão instalada");
        Argumento ejetarArquivosDatabase = new Argumento(
                List.of("--eject-database-scripts", "--ejetar-arquivos-banco"),
                "Ejeta os arquivos de configuração do banco de dados para que o usuário os possa usar");
        Argumento ejetarArquivoCompose = new Argumento(
                List.of("--eject-docker-compose", "--ejetar-docker-compose"),
                "Ejeta o arquivo docker-compose.yml para que o usuário possa usá-lo");

        mostrarVersao.autoRegistrar(argumentosPermitidos);
        gerarConfig.autoRegistrar(argumentosPermitidos);
        mostrarConfig.autoRegistrar(argumentosPermitidos);
        atualizar.autoRegistrar(argumentosPermitidos);
        ejetarArquivosDatabase.autoRegistrar(argumentosPermitidos);
        ejetarArquivoCompose.autoRegistrar(argumentosPermitidos);
    }

    public void analisarArgumentos() {
        for (String argumento : argumentosPassados) {

            if (!argumentosPermitidos.containsKey(argumento)) {
                logger.warning("A flag %s é inválida, por isso ela será ignorada%n".formatted(argumento));
                return;
            }

            switch (argumento) {
                case "--version", "--versao", "-v" -> executadorLogicaArgumentos.mostrarVersao();

                case "--gen-config", "--gerar-config" -> executadorLogicaArgumentos.gerarConfiguracoes();

                case "--show-config", "--mostrar-config" -> executadorLogicaArgumentos.mostrarConfiguracoes();

                case "--update", "--atualizar" -> executadorLogicaArgumentos.atualizarPrograma();

                case "--eject-database-scripts", "--ejetar-arquivos-banco" -> executadorLogicaArgumentos
                        .ejetarArquivosBancoDados();

                case "--eject-docker-compose", "--ejetar-docker-compose" -> executadorLogicaArgumentos
                        .ejetarDockerCompose();

                default -> logger.severe("A flag passada é válida, mas não tem funcionalidade, se estiver na "
                        + "última versão, por favor reporte aos desenvolvedores no GitHub do projeto.");
            }
        }
    }
}
