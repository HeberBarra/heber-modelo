package org.modelador.base.forma;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;

public class FracaoCirculo extends JPanel {

    private int anguloInicial;
    private int anguloArco;
    private int diametro;
    private int circuloX;
    private int circuloY;

    public FracaoCirculo(int largura, int altura, int circuloX, int circuloY , int diametro, int anguloInicial, int anguloArco, Color corFundo) {
        super();
        this.anguloInicial = anguloInicial;
        this.anguloArco = anguloArco;
        this.diametro = diametro;
        this.circuloX = circuloX;
        this.circuloY = circuloY;

        setSize(largura, altura);
        setOpaque(false);
        setBackground(corFundo);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setBackground(getBackground());
        graphics2D.setColor(getBackground());
        Arc2D arco = new Arc2D.Double(circuloX, circuloY, diametro, diametro, anguloInicial, anguloArco, Arc2D.PIE);
        graphics2D.fill(arco);
    }

    public int getCirculoX() {
        return circuloX;
    }

    public int getAnguloInicial() {
        return anguloInicial;
    }

    public void setAnguloInicial(int anguloInicial) {
        this.anguloInicial = anguloInicial;
    }

    public int getAnguloArco() {
        return anguloArco;
    }

    public void setAnguloArco(int anguloArco) {
        this.anguloArco = anguloArco;
    }

    public int getDiametro() {
        return diametro;
    }

    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    public void setCirculoX(int circuloX) {
        this.circuloX = circuloX;
    }

    public int getCirculoY() {
        return circuloY;
    }

    public void setCirculoY(int circuloY) {
        this.circuloY = circuloY;
    }

}
