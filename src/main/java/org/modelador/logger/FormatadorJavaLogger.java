package org.modelador.logger;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FormatadorJavaLogger extends Formatter {

    @Override
    public String format(LogRecord record) {
        return String.format(
                "%s - [%s] %s::%s - %s\n",
                new Date(record.getMillis()),
                record.getLevel(),
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getThrown());
    }
}
