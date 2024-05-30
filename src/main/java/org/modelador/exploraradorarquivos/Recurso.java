package org.modelador.exploraradorarquivos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelador.logger.JavaLogger;

public class Recurso {

    private static final Logger logger = JavaLogger.obterLogger(Recurso.class.getName());

    public @Nullable InputStream pegarRecurso(@NotNull String caminhoArquivo) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream recurso = classLoader.getResourceAsStream(caminhoArquivo);

        if (recurso == null) {
            logger.warning("Falha ao tentar ler o recurso: %s. Recurso possivelmente não existe.");
            return null;
        }

        return recurso;
    }

    public @Nullable File pegarArquivoRecurso(@NotNull String caminhoArquivo) {
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
        try (InputStream recursoStream = pegarRecurso(caminhoArquivo)) {
            arquivoRecurso = File.createTempFile(nomeArquivo.toString(), extensaoArquivo);
            arquivoRecurso.deleteOnExit();
            Files.copy(
                    Objects.requireNonNull(recursoStream),
                    arquivoRecurso.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return arquivoRecurso;
        } catch (IOException e) {
            logger.warning("Falha ao tentar criar arquivo temporário. %n%s".formatted(e.getMessage()));
        }

        return null;
    }

    public @NotNull Path pegarCaminhoRecurso(@NotNull String caminhoArquivo) {
        File arquivoRecurso = pegarArquivoRecurso(caminhoArquivo);

        return Objects.requireNonNull(arquivoRecurso).toPath();
    }
}
