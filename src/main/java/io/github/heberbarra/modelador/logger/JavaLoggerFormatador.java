package io.github.heberbarra.modelador.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.fusesource.jansi.Ansi;

public class JavaLoggerFormatador extends Formatter {

    private static final Map<String, Ansi.Color> CORES_LEVELS = new HashMap<>();
    private boolean desativarCores;

    public JavaLoggerFormatador() {
        this(false);
    }

    static {
        CORES_LEVELS.put(Level.SEVERE.getName(), Ansi.Color.RED);
        CORES_LEVELS.put(Level.WARNING.getName(), Ansi.Color.YELLOW);
        CORES_LEVELS.put(Level.INFO.getName(), Ansi.Color.CYAN);
    }

    public JavaLoggerFormatador(boolean desativarCores) {
        this.desativarCores = desativarCores;
    }

    public String formatarNomeLevel(String nomeLevel) {
        if (!CORES_LEVELS.containsKey(nomeLevel)) {
            return nomeLevel;
        }

        return Ansi.ansi().fg(CORES_LEVELS.get(nomeLevel)).a(nomeLevel).reset().toString();
    }

    @Override
    public String format(LogRecord record) {
        String nomeLevel = record.getLevel().toString();

        if (!desativarCores) {
            nomeLevel = formatarNomeLevel(nomeLevel);
        }

        return "[%s] (%s) - %s::%s %n%s%n"
                .formatted(
                        nomeLevel,
                        new Date(record.getMillis()),
                        record.getSourceClassName(),
                        record.getSourceMethodName(),
                        record.getMessage());
    }

    public boolean isDesativarCores() {
        return desativarCores;
    }

    public void setDesativarCores(boolean desativarCores) {
        this.desativarCores = desativarCores;
    }
}
