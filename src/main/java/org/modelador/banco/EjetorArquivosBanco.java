package org.modelador.banco;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.logging.Logger;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.Recurso;
import org.modelador.logger.JavaLogger;

/**
 * Faz uma cópia de certos arquivos que estão dentro do JAR e joga eles para fora, permitindo assim que o usuário
 * possa utilizá-los mais facilmente.
 * <p>
 * Considere "ejetar" nesse contexto, como fazer uma cópia de um arquivo.
 * */
public class EjetorArquivosBanco {

    private static final Logger logger = JavaLogger.obterLogger(EjetorArquivosBanco.class.getName());
    public static final String PASTA_ARQUIVOS_BANCO = "database/";
    public static final String PASTA_SCRIPTS = PASTA_ARQUIVOS_BANCO + "init/";
    public static final String CONFIGURAR_BANCO = PASTA_SCRIPTS + "01 - ConfigurarBancoDados.sql";
    public static final String CONFIGURAR_USERS = PASTA_SCRIPTS + "02 - CriarUsuarios.sh";
    public static final String ARQUIVO_DOCKER_COMPOSE = PASTA_ARQUIVOS_BANCO + "docker-compose.yml";
    private final Configurador configurador;
    private final String destinoArquivos;

    public EjetorArquivosBanco() {
        this.configurador = Configurador.getInstance();
        this.destinoArquivos = configurador.pegarValorConfiguracao("ejetor", "destino", String.class);
    }

    /**
     * Ejeta os arquivos utilizados para configurar a base de dados
     * */
    public void ejetarScriptsConfiguracao() {
        String pastaDestino = destinoArquivos + "init/";
        ejetarArquivo(pastaDestino, CONFIGURAR_BANCO);
        ejetarArquivo(pastaDestino, CONFIGURAR_USERS);
    }

    /**
     * Ejeta o arquivo utilizado para criar o docker compose do programa, opcionalmente, caso o usuário deseje
     * (decido pela configuração do programa), cria uma cópia do arquivo env para o mesmo local para onde será
     * ejetado o arquivo docker compose
     * */
    public void ejetarDockerCompose() {
        ejetarArquivo(destinoArquivos, ARQUIVO_DOCKER_COMPOSE);

        if (configurador.pegarValorConfiguracao("ejetor", "copiar_arquivo_env", boolean.class)) {
            copiarArquivoDotEnv();
        }
    }

    /**
     * Ejeta um arquivo específico de dentro do JAR, para o destino desejado, copiando o conteúdo do arquivo original
     * para o gerado
     * <p>
     * Cria a pasta de destino e suas antecessoras, caso as mesmas não existam
     * @param pastaDestino a pasta na qual o arquivo deve ser salvo
     * @param arquivo o arquivo a ser ejetado pelo programa
     */
    private void ejetarArquivo(String pastaDestino, String arquivo) {
        try (InputStream arquivoExtraido = Recurso.pegarRecurso(arquivo)) {
            String nomeArquivo = Arrays.stream(arquivo.split("/")).toList().getLast();
            File arquivoDestino = new File(pastaDestino + nomeArquivo);
            if (arquivoDestino.getParentFile().mkdirs()) logger.info("Pastas criadas com sucesso.\n");

            if (arquivoDestino.createNewFile())
                logger.info("Arquivo %s criado com sucesso.%n".formatted(arquivoDestino));

            Files.copy(arquivoExtraido, arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.warning("Falha ao tentar criar o arquivo %s.%n".formatted(arquivo));
        }
    }

    /**
     * Criar um link simbólico para o arquivo env especificado nas configurações do programa, permitindo assim passar
     * segredos para os arquivos ejetados mais facilmente.*/
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void copiarArquivoDotEnv() {
        String nomeEnv = configurador.pegarValorConfiguracao("ejetor", "nome_arquivo_env", String.class);
        Path arquivoEnv = new File(nomeEnv).getAbsoluteFile().toPath();
        File arquivoLink = new File(destinoArquivos, "/.env");
        Path caminhoLink = arquivoLink.toPath();

        try {
            if (arquivoLink.exists()) {
                arquivoLink.delete();
            }

            Files.createSymbolicLink(caminhoLink, arquivoEnv);
        } catch (IOException e) {
            logger.warning("Falha ao tentar criar link simbólico do arquivo %s.%n".formatted(arquivoLink));
            throw new RuntimeException(e);
        }
    }
}
