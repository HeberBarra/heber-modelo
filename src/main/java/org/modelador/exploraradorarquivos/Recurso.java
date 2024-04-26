package org.modelador.exploraradorarquivos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

public class Recurso {

    protected Logger logger = JavaLogger.obterLogger(Recurso.class.getName());

    public File pegarRecurso(String caminhoArquivo) {
        ClassLoader classLoader = getClass().getClassLoader();
        StringBuilder nomeArquivo = new StringBuilder();
        String extensaoArquivo = ".toml";
        String[] partesArquivo = caminhoArquivo.split("\\.");

        for (int i = 0; i < partesArquivo.length; i++) {
            if (i == partesArquivo.length - 1) {
                extensaoArquivo = partesArquivo[i];
                break;
            }

            nomeArquivo.append(partesArquivo[i]);
        }

        File arquivoRecurso;
        try (InputStream recursoStream = classLoader.getResourceAsStream(caminhoArquivo)) {
            arquivoRecurso = File.createTempFile(nomeArquivo.toString(), extensaoArquivo);
            arquivoRecurso.deleteOnExit();
            Files.copy(
                    Objects.requireNonNull(recursoStream),
                    arquivoRecurso.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return arquivoRecurso;
        } catch (IOException e) {
            logger.warning("Erro ao ler recurso: %s - %s".formatted(caminhoArquivo, e.getMessage()));
        }

        return null;
    }
}
