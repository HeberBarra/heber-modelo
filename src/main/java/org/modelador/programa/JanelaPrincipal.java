package org.modelador.programa;

import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.modelador.base.janela.BaseJanela;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;
import org.modelador.seletor.SeletorRadial;

public class JanelaPrincipal extends BaseJanela {

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);
        getContentPane().setBackground(Paleta.pegarCor("cor_fundo"));
        setVisible(true);
        criarGrade();

        addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent evento) {
                        if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
                            new SeletorRadial(MouseInfo.getPointerInfo().getLocation());
                        }

                        if (evento.getKeyCode() == KeyEvent.VK_F1) {
                            Configurador.recarregarConfiguracoes();
                        }
                    }
                });
    }

    private void criarGrade() {
        Grade grade =
                new Grade(
                        getWidth(),
                        getHeight(),
                        Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class),
                        Paleta.pegarCor("cor_grade"));
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
