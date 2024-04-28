package org.modelador.programa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.configurador.paleta.Paleta;

public class Grade extends JPanel implements RecarregamentoComponente {

    private int tamanhoQuadrado;
    private Color corGrade;
    private Color corBorda;
    private int espessuraBorda;

    public Grade(Dimension dimensaoGrade, int tamanhoQuadrado, Color corGrade, Color corBorda, int espessuraBorda) {
        this(dimensaoGrade.width, dimensaoGrade.height, tamanhoQuadrado, corGrade, corBorda, espessuraBorda);
    }

    public Grade(
            int larguraGrade,
            int alturaGrade,
            int tamanhoQuadrado,
            Color corGrade,
            Color corBorda,
            int espessuraBorda) {
        super();
        this.tamanhoQuadrado = tamanhoQuadrado;
        this.corGrade = corGrade;
        this.corBorda = corBorda;
        this.espessuraBorda = espessuraBorda;

        configurarGrade(larguraGrade, alturaGrade);
    }

    protected void criarBordaGrade() {
        setBorder(BorderFactory.createLineBorder(corBorda, espessuraBorda));
    }

    private void configurarGrade(int largura, int altura) {
        setSize(largura, altura);
        setOpaque(false);
        criarBordaGrade();
    }

    public int recalcularTamanho(int tamanho) {
        return (Math.divideExact(tamanho, tamanhoQuadrado) - 2) * tamanhoQuadrado;
    }

    @Override
    public void setSize(int largura, int altura) {
        super.setSize(recalcularTamanho(largura), recalcularTamanho(altura));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(corGrade);

        for (int i = tamanhoQuadrado + espessuraBorda - 1; i < getWidth() - espessuraBorda - 1; i += tamanhoQuadrado) {
            graphics.drawLine(i, espessuraBorda, i, getHeight() - espessuraBorda - 1);
        }

        for (int i = tamanhoQuadrado + espessuraBorda - 1; i < getHeight() - espessuraBorda - 1; i += tamanhoQuadrado) {
            graphics.drawLine(espessuraBorda, i, getWidth() - espessuraBorda - 1, i);
        }
    }

    @Override
    public void recarregar() {
        criarBordaGrade();
        setCorGrade(Paleta.pegarCor("cor_grade"));
        repaint();
    }

    public int getTamanhoQuadrado() {
        return tamanhoQuadrado;
    }

    public void setTamanhoQuadrado(int tamanhoQuadrado) {
        this.tamanhoQuadrado = tamanhoQuadrado;
    }

    public Color getCorGrade() {
        return corGrade;
    }

    public void setCorGrade(Color corGrade) {
        this.corGrade = corGrade;
    }

    public int getEspessuraBorda() {
        return espessuraBorda;
    }

    public void setEspessuraBorda(int espessuraBorda) {
        this.espessuraBorda = espessuraBorda;
    }

    public Color getCorBorda() {
        return corBorda;
    }

    public void setCorBorda(Color corBorda) {
        this.corBorda = corBorda;
    }
}
