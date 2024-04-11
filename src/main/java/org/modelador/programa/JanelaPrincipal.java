package org.modelador.programa;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.modelador.base.janela.BaseJanela;
import org.modelador.seletor.SeletorRadial;

public class JanelaPrincipal extends BaseJanela {

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);
        setVisible(true);
        criarGrade();

        addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evento) {
                        if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
                            new SeletorRadial(MouseInfo.getPointerInfo().getLocation());
                        }
                    }
                });
    }

    private void criarGrade() {
        Grade grade = new Grade(getWidth(), getHeight(), 20, Color.GRAY, Color.BLACK);
        getContentPane().setLayout(null);
        getContentPane().add(grade);

        addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        super.componentResized(e);
                        grade.setSize(getSize());
                        grade.repaint();
                    }
                });
    }
}
