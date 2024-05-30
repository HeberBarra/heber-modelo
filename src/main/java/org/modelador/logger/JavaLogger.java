package org.modelador.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaLogger {

    private static String nomeArquivo = "log.txt";
    private static File pastaLogs = new File("log/");
    private static final Logger logger = Logger.getLogger(JavaLogger.class.getName());
    protected static ConsoleHandler consoleHandler = criarConsoleHandler();
    protected static FileHandler fileHandler = criarFileHandler(nomeArquivo);

    static {
        // https://stackoverflow.com/questions/2533227/how-can-i-disable-the-default-console-handler-while-using-the-java-logging-api
        Logger loggerGlobal = Logger.getLogger("");
        Handler[] handlers = loggerGlobal.getHandlers();
        for (Handler handler : handlers) {
            loggerGlobal.removeHandler(handler);
        }
    }

    public static String getNomeArquivo() {
        return nomeArquivo;
    }

    public static void setNomeArquivo(String nomeArquivo) {
        JavaLogger.nomeArquivo = nomeArquivo;
    }

    public static File getPastaLogs() {
        return pastaLogs;
    }

    public static void setPastaLogs(File pastaLogs) {
        JavaLogger.pastaLogs = pastaLogs;
    }

    public static @NotNull Logger obterLogger(@NotNull String nome) {
        Logger logger = Logger.getLogger(nome);

        logger.addHandler(consoleHandler);
        logger.addHandler(fileHandler);

        return logger;
    }

    protected static @NotNull ConsoleHandler criarConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new FormatadorJavaLogger());
        consoleHandler.setFilter(new JavaLoggerFiltro());

        return consoleHandler;
    }

    protected static @Nullable FileHandler criarFileHandler(@NotNull String nomeArquivo) {
        try {
            logger.finest(String.valueOf(pastaLogs.mkdir()));
            return new JavaLoggerArquivo(pastaLogs, nomeArquivo);

        } catch (IOException e) {
            logger.warning("Falha ao criar logger: %s".formatted(e.getMessage()));
            return null;
        }
    }

    public static void desativarLogger(boolean deveDesativar, @NotNull Logger logger) {
        if (deveDesativar) {
            logger.setLevel(Level.OFF);
        }
    }
}
