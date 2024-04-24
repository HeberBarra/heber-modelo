package org.modelador.exploraradorarquivos;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.nio.file.Path;

public class ExploradorArquivos extends FileDialog {

    protected Path caminhoArquivo;

    protected boolean filtrar(File diretorio, String nomeArquivo) {
        nomeArquivo = nomeArquivo.toLowerCase();
        return nomeArquivo.endsWith("brm3") || nomeArquivo.endsWith("xml");
    }

    public ExploradorArquivos(Frame framePai, String titulo, int modo) {
        super(framePai, titulo, modo);
        setDirectory(System.getProperty("user.home"));

        setFilenameFilter(this::filtrar);

        setVisible(true);
        caminhoArquivo = Path.of(this.getDirectory() + "/" + this.getFile());
    }

    public Path getCaminhoArquivo() {
        return caminhoArquivo;
    }
}
