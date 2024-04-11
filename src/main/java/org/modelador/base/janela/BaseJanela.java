package org.modelador.base.janela;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class BaseJanela extends JFrame {

    protected JPanel conteudo = new JPanel();

    public BaseJanela(String titulo, int largura, int altura) {
        super(titulo);
        setSize(largura, altura);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(conteudo);
    }
}
