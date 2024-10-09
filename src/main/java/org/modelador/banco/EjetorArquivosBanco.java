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

public class EjetorArquivosBanco {

    private static final Logger logger = JavaLogger.obterLogger(EjetorArquivosBanco.class.getName());
    public static final String PASTA_ARQUIVOS_BANCO = "database/";
    public static final String PASTA_SCRIPTS = PASTA_ARQUIVOS_BANCO + "init/";
    public static final String CONFIGURAR_BANCO = PASTA_SCRIPTS + "01 - ConfigurarBancoDados.sql";
    public static final String CONFIGURAR_USERS = PASTA_SCRIPTS + "02 - CriarUsuarios.sh";
    public static final String ARQUIVO_DOCKER_COMPOSE = PASTA_ARQUIVOS_BANCO + "docker-compose.yml";
    private final Configurador configurador;
    private String destinoArquivos;

    public EjetorArquivosBanco() {
        this.configurador = Configurador.getInstance();
        this.destinoArquivos = configurador.pegarValorConfiguracao("ejetor", "destino", String.class);
    }

    public void ejetarScriptsConfiguracao() {
        String pastaDestino = destinoArquivos + "init/";
        ejetarArquivo(pastaDestino, CONFIGURAR_BANCO);
        ejetarArquivo(pastaDestino, CONFIGURAR_USERS);
    }

    public void ejetarDockerCompose() {
        ejetarArquivo(destinoArquivos, ARQUIVO_DOCKER_COMPOSE);

        if (configurador.pegarValorConfiguracao("ejetor", "copiar_arquivo_env", boolean.class)) {
            copiarArquivoDotEnv();
        }
    }

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

    public String getDestinoArquivos() {
        return destinoArquivos;
    }

    public void setDestinoArquivos(String destinoArquivos) {
        this.destinoArquivos = destinoArquivos;
    }
}
