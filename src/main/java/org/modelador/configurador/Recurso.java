package org.modelador.configurador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.modelador.logger.JavaLogger;

public class Recurso {

    private static final Logger logger = JavaLogger.obterLogger(Recurso.class.getName());

    public static @NotNull InputStream pegarRecurso(@NotNull String caminhoRecurso) {
        ClassLoader classLoader = Recurso.class.getClassLoader();
        InputStream recurso = classLoader.getResourceAsStream(caminhoRecurso);

        if (recurso == null) {
            logger.severe("Falha ao tentar ler o arquivo: %s. Recurso não encontrado.".formatted(caminhoRecurso));
            logger.severe("Encerrando o programa");
            System.exit(1);
        }

        return recurso;
    }

    public static @NotNull File pegarArquivoRecurso(String caminhoRecurso) {
        StringBuilder nomeArquivo = new StringBuilder();
        String[] partesArquivo = caminhoRecurso.split("\\.");
        String extensaoArquivo = "";
        File arquivoRecurso = null;

        for (int i = 0; i < partesArquivo.length; i++) {
            if (i == partesArquivo.length - 1) {
                extensaoArquivo = partesArquivo[i];
            }

            nomeArquivo.append(partesArquivo[i]);
        }

        try (InputStream recursoStream = pegarRecurso(caminhoRecurso)) {
            arquivoRecurso = File.createTempFile(nomeArquivo.toString(), extensaoArquivo);
            arquivoRecurso.deleteOnExit();
            Files.copy(recursoStream, arquivoRecurso.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return arquivoRecurso;
        } catch (IOException e) {
            logger.severe("Falha ao tentar criar arquivo temporário. %n%s".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(1);
        }

        return arquivoRecurso;
    }

    public static @NotNull Path pegarCaminhoRecurso(@NotNull String caminhoRecurso) {
        return pegarArquivoRecurso(caminhoRecurso).toPath();
    }
}
