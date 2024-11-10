package io.github.heberbarra.modelador.test.logger;

import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.test.CapturadorPrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestLogger {
    protected static final String NOME_LOGGER = TestLogger.class.getName();
    protected static final PrintStream stdOut = System.out;
    protected static final PrintStream stdErr = System.err;
    protected static ByteArrayOutputStream dados = new ByteArrayOutputStream();
    protected static String arquivoLog = "/log.txt";
    protected static File pastaLog = new File("src/test/resources/log/");

    @BeforeAll
    protected static void redirecionarStd() {
        System.setOut(new CapturadorPrintStream(dados));
        System.setErr(new CapturadorPrintStream(dados));
    }

    @BeforeAll
    protected static void configurarJavaLogger() {
        JavaLogger.setPastaLogs(pastaLog);
        JavaLogger.setNomeArquivo(arquivoLog);
    }

    @BeforeAll
    protected static void criarPasta() {
        pastaLog.mkdir();
    }

    @Test
    public void testarDesativar() {
        Logger logger = JavaLogger.obterLogger(NOME_LOGGER);
        JavaLogger.desativarLogger(true, logger);

        Assertions.assertEquals(Level.OFF, logger.getLevel());
    }

    @AfterAll
    protected static void restaurarStd() {
        System.setOut(stdOut);
        System.setErr(stdErr);
    }
}
