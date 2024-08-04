package org.modelador.base.componente;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.border.AbstractBorder;

// https://stackoverflow.com/questions/52759203/rounded-lineborder-not-all-corners-are-rounded
public class BordaRedonda extends AbstractBorder {

    private final Paint preenchimento;
    private final Stroke traco;
    private final int espessura;
    private final int tamanhoCanto;
    private Object antiAliasing;

    public BordaRedonda(Paint preenchimento, int espessura, int tamanhoCanto) {
        this.preenchimento = preenchimento;
        this.espessura = espessura;
        this.tamanhoCanto = tamanhoCanto;
        traco = new BasicStroke(espessura);
        antiAliasing = RenderingHints.VALUE_ANTIALIAS_OFF;
    }

    public BordaRedonda(Paint preenchimento, int espessura, int tamanhoCanto, boolean useAntiAliasing) {
        this(preenchimento, espessura, tamanhoCanto);

        if (useAntiAliasing) {
            antiAliasing = RenderingHints.VALUE_ANTIALIAS_ON;
        }
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        int tamanho = Math.max(espessura, tamanhoCanto);

        return new Insets(tamanho, tamanho, tamanho, tamanho);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D graphics2D = (Graphics2D) g;
        Paint preenchimentoAnterior = graphics2D.getPaint();
        Stroke tracoAnterior = graphics2D.getStroke();
        Object valorAntiAliasingAnterior = graphics2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

        if (preenchimento == null) {
            graphics2D.setPaint(c.getBackground());
        } else {
            graphics2D.setPaint(preenchimento);
        }

        graphics2D.setStroke(traco);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing);

        int offset = espessura >> 1;
        graphics2D.drawRoundRect(
                x + offset, y + offset, width - espessura, height - espessura, tamanhoCanto, tamanhoCanto);

        graphics2D.setPaint(preenchimentoAnterior);
        graphics2D.setStroke(tracoAnterior);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, valorAntiAliasingAnterior);
    }
}
