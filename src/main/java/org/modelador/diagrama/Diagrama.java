package org.modelador.diagrama;

import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;
import org.modelador.base.componente.Componente;

public class Diagrama extends JPanel {

    protected String nome;

    public Diagrama(@NotNull String nome) {
        super();
        this.nome = nome;
        setLayout(null);
        setOpaque(false);
    }

    public void adicionarComponente(@NotNull Componente componente, int x, int y, int largura, int altura) {
        add(componente);
        componente.setBounds(x, y, largura, altura);
        componente.repaint();
    }

    public void adicionarComponente(@NotNull Componente componente) {
        add(componente);
        componente.setBounds(componente.getX(), componente.getY(), componente.getLargura(), componente.getAltura());
        componente.repaint();
    }
}
