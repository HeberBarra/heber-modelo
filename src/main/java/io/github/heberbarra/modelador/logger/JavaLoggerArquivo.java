package io.github.heberbarra.modelador.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.FileHandler;

public class JavaLoggerArquivo extends FileHandler {

    protected static String corrigirNomeArquivo(String nomeArquivo) {
        if (!nomeArquivo.startsWith("/")) {
            nomeArquivo = "/" + nomeArquivo;
        }

        if (!nomeArquivo.contains(".")) {
            return nomeArquivo + ".txt";
        }

        return nomeArquivo;
    }

    public JavaLoggerArquivo(File pastaLog, String nomeArquivo) throws IOException {
        this(pastaLog.toString(), nomeArquivo);
    }

    public JavaLoggerArquivo(Path pastaLog, String nomeArquivo) throws IOException {
        this(pastaLog.toString(), nomeArquivo);
    }

    public JavaLoggerArquivo(String pastaLog, String nomeArquivo) throws IOException {
        super(pastaLog + corrigirNomeArquivo(nomeArquivo));
        setFormatter(new JavaLoggerFormatador(true));
        setFilter(new JavaLoggerFiltro());
    }
}
