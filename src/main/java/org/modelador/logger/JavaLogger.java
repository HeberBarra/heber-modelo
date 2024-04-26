package org.modelador.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLogger {

    public static String getNomeArquivo() {
        return nomeArquivo;
    }

    public static void setNomeArquivo(String nomeArquivo) {
        JavaLogger.nomeArquivo = nomeArquivo;
    }

    private static String nomeArquivo = "log.txt";
    private static File pastaLogs = new File("log/");

    public static File getPastaLogs() {
        return pastaLogs;
    }

    public static void setPastaLogs(File pastaLogs) {
        JavaLogger.pastaLogs = pastaLogs;
    }

    public static Logger obterLogger(String nome) {
        Logger logger = Logger.getLogger(nome);

        logger.addHandler(criarConsoleHandler());

        try {
            logger.finest(String.valueOf(pastaLogs.mkdir()));
            logger.addHandler(criarFileHandler(nomeArquivo));

        } catch (IOException e) {
            logger.warning("Falha ao criar logger: %s".formatted(e.getMessage()));
        }

        return logger;
    }

    protected static ConsoleHandler criarConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new FormatadorJavaLogger());
        consoleHandler.setFilter(new JavaLoggerFiltro());

        return consoleHandler;
    }

    protected static FileHandler criarFileHandler(String nomeArquivo) throws IOException {
        return new JavaLoggerArquivo(pastaLogs, nomeArquivo);
    }

    public static void desativarLogger(boolean deveDesativar, Logger logger) {
        if (deveDesativar) {
            logger.setLevel(Level.OFF);
        }
    }
}
