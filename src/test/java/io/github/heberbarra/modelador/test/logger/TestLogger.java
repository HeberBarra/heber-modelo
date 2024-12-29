package io.github.heberbarra.modelador.test.logger;

import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.test.CapturadorPrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeAll
    protected static void criarPasta() {
        pastaLog.mkdir();
    }

    @AfterAll
    protected static void restaurarStd() {
        System.setOut(stdOut);
        System.setErr(stdErr);
    }
}
