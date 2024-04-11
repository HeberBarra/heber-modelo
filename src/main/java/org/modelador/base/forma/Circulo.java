package org.modelador.base.forma;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Circulo extends JPanel {

    private int raioBorda;

    public Circulo(int raioBorda, Color corFundo) {
        super();
        setBackground(corFundo);
        setOpaque(false);
        this.raioBorda = raioBorda;
    }

    public Circulo(int raioBorda) {
        super();
        this.raioBorda = raioBorda;
    }

    public int getRaioBorda() {
        return raioBorda;
    }

    public void setRaioBorda(int raioBorda) {
        this.raioBorda = raioBorda;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Dimension arcos = new Dimension(raioBorda, raioBorda);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getBackground());
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), arcos.width, arcos.height);
    }
}
