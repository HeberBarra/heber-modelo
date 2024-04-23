package org.modelador.programa;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.base.janela.BaseJanela;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;
import org.modelador.seletor.SeletorRadial;

public class JanelaPrincipal extends BaseJanela {

    protected Grade grade;
    protected int larguraGrade;
    protected int alturaGrade;

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);
        getContentPane().setBackground(Paleta.pegarCor("cor_fundo"));
        setVisible(true);

        larguraGrade = largura;
        alturaGrade = altura;

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
                            recarregarComponentes();
                        }
                    }
                });
    }

    private void recarregarComponentes() {
        getContentPane().setBackground(Paleta.pegarCor("cor_fundo"));
        grade.setTamanhoQuadrado(
                Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class));
        grade.setSize(larguraGrade, alturaGrade);

        for (Component componente : getContentPane().getComponents()) {
            if (componente instanceof RecarregamentoComponente) {
                ((RecarregamentoComponente) componente).recarregar();
            }
        }
    }

    private void criarGrade() {
        grade =
                new Grade(
                        larguraGrade,
                        alturaGrade,
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
