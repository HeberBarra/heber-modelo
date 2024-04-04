package org.modelador.seletor;

import org.modelador.base.forma.Circulo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class SeletorRadial extends JFrame {

    public final int RAIO_BORDA = 10000;
    private final Color TRANSPARENTE = new Color(1, 1, 1, 0);
    protected final JPanel conteudo = new JPanel(null);

    public SeletorRadial(int diametro, Point posicao) {
        super();
        configurarSeletor(diametro);
        criarCirculos(diametro);
        setLocation((int) (posicao.getX() - diametro / 2), (int) (posicao.getY() - diametro / 2));
        setVisible(true);
        setAutoRequestFocus(true);
        fecharAoPerderFoco();
    }

    private void fecharAoPerderFoco() {
        addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });
    }

    private void criarCirculos(int diametro) {
        Circulo circuloSeletor = new Circulo(RAIO_BORDA, Color.LIGHT_GRAY);
        circuloSeletor.setOpaque(false);
        circuloSeletor.setBounds(0, 0, diametro, diametro);

        Circulo circuloInterno = new Circulo(RAIO_BORDA, Color.GRAY);
        circuloInterno.setOpaque(false);
        int diametroCirculoInterno = circuloSeletor.getWidth() / 2;
        int circuloInternoX = circuloSeletor.getX() + (circuloSeletor.getWidth() - diametroCirculoInterno) / 2;
        int circuloInternoY = circuloSeletor.getY() + (circuloSeletor.getHeight() - diametroCirculoInterno) / 2;
        circuloInterno.setBounds(circuloInternoX, circuloInternoY, diametroCirculoInterno, diametroCirculoInterno);

        conteudo.add(circuloInterno);
        conteudo.add(circuloSeletor);
    }

    private void configurarSeletor(int diametro) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(conteudo);
        setSize(diametro, diametro);
        setResizable(false);
        setUndecorated(true);
        setBackground(TRANSPARENTE);
    }

}
