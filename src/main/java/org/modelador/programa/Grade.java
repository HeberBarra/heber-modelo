package org.modelador.programa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.configurador.paleta.Paleta;

public class Grade extends JPanel implements RecarregamentoComponente {

    private int tamanhoQuadrado;
    private Color corGrade;

    public Grade(Dimension dimensaoGrade, int tamanhoQuadrado, Color corGrade) {
        this(dimensaoGrade.width, dimensaoGrade.height, tamanhoQuadrado, corGrade);
    }

    public Grade(int larguraGrade, int alturaGrade, int tamanhoQuadrado, Color corGrade) {
        super();
        this.tamanhoQuadrado = tamanhoQuadrado;
        this.corGrade = corGrade;

        configurarGrade(larguraGrade, alturaGrade);
    }

    private void configurarGrade(int largura, int altura) {
        setSize(largura, altura);
        setOpaque(false);
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

        for (int i = tamanhoQuadrado; i < getWidth(); i += tamanhoQuadrado) {
            graphics.drawLine(i, 0, i, getHeight());
        }

        for (int i = tamanhoQuadrado; i < getHeight(); i += tamanhoQuadrado) {
            graphics.drawLine(0, i, getWidth(), i);
        }
    }

    @Override
    public void recarregar() {
        ;
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
}
