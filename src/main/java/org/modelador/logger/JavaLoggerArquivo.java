package org.modelador.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import org.jetbrains.annotations.NotNull;

public class JavaLoggerArquivo extends FileHandler {

    protected static @NotNull String corrigirNomeArquivo(@NotNull String nomeArquivo) {
        if (!nomeArquivo.startsWith("/")) {
            nomeArquivo = "/" + nomeArquivo;
        }

        if (!nomeArquivo.contains(".")) {
            return nomeArquivo + ".txt";
        }

        return nomeArquivo;
    }

    public JavaLoggerArquivo(@NotNull File pastaLog, @NotNull String nomeArquivo) throws IOException {
        this(pastaLog.toString(), nomeArquivo);
    }

    public JavaLoggerArquivo(@NotNull Path pastaLog, @NotNull String nomeArquivo) throws IOException {
        this(pastaLog.toString(), nomeArquivo);
    }

    public JavaLoggerArquivo(@NotNull String pastaLog, @NotNull String nomeArquivo) throws IOException {
        super(pastaLog + corrigirNomeArquivo(nomeArquivo));
        setFormatter(new FormatadorJavaLogger(true));
        setFilter(new JavaLoggerFiltro());
    }
}
