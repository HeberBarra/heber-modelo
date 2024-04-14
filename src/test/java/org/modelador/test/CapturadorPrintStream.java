package org.modelador.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CapturadorPrintStream extends PrintStream {

    public CapturadorPrintStream(ByteArrayOutputStream dados) {
        super(dados, true);
    }
}
