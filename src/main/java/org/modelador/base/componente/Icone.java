package org.modelador.base.componente;

import java.awt.Image;
import java.io.File;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelador.exploraradorarquivos.Recurso;

public class Icone {

    private static final Recurso RECURSO = new Recurso();

    public static @NotNull ImageIcon criarImagem(
            @NotNull String imagem, @Nullable String descricaoImagem, int largura, int altura) {
        File arquivoImagem = RECURSO.pegarArquivoRecurso(imagem);
        ImageIcon imageIcon =
                new ImageIcon(Objects.requireNonNull(arquivoImagem).getPath(), descricaoImagem);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH));

        return imageIcon;
    }

    public static @NotNull JLabel criarLabelImagem(
            @NotNull String imagem, @Nullable String descricaoImagem, int largura, int altura) {
        ImageIcon imageIcon = criarImagem(imagem, descricaoImagem, largura, altura);
        return new JLabel("", imageIcon, SwingConstants.CENTER);
    }
}
