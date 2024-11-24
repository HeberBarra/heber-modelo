package io.github.heberbarra.modelador.logger;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class JavaLoggerFiltro implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return record.getLevel() != Level.FINE;
    }
}
