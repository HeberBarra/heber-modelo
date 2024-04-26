package org.modelador.seletor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.modelador.base.forma.Circulo;
import org.modelador.base.forma.FracaoCirculo;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;

public class SeletorRadial extends JFrame {

    public final Color COR_HOVER = Paleta.pegarCor("cor_hover");
    public final Color COR_FUNDO = Paleta.pegarCor("cor_seletor_radial");
    public final Color TRANSPARENTE = new Color(1, 1, 1, 0);
    private final int RAIO_BORDA = 10000;
    private final int DIAMETRO = Configurador.pegarValorConfiguracao("seletor", "diametro", int.class);
    protected JPanel conteudo = new JPanel(new BorderLayout());
    protected Circulo circuloInterno = new Circulo(RAIO_BORDA, Color.GRAY);

    public SeletorRadial(Point posicao) {
        super();

        configurarSeletor();
        criarCirculoInterno();
        criarFracoes();
        setLocation((int) (posicao.getX() - DIAMETRO / 2), (int) (posicao.getY() - DIAMETRO / 2));
        setVisible(true);
        setAutoRequestFocus(true);
        fecharAoPerderFoco();
    }

    private void fecharAoPerderFoco() {
        addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {}

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });
    }

    private void criarFracoes() {
        final int NUMERO_FUNCOES = 4;
        conteudo.setSize(DIAMETRO, DIAMETRO);
        FracaoCirculo[] fracoesCirculo = new FracaoCirculo[NUMERO_FUNCOES];

        int anguloFracoes = 360 / NUMERO_FUNCOES;

        Dimension[] dimensoes = new Dimension[NUMERO_FUNCOES];
        dimensoes[0] = new Dimension(DIAMETRO / 2, DIAMETRO / 2);
        dimensoes[1] = new Dimension(DIAMETRO, DIAMETRO / 2);
        dimensoes[2] = new Dimension(DIAMETRO / 2, DIAMETRO);
        dimensoes[3] = new Dimension(DIAMETRO, DIAMETRO);

        int[] angulos = {90, 0, 180, 270};

        for (int i = 0; i < NUMERO_FUNCOES; i++) {
            fracoesCirculo[i] =
                    new FracaoCirculo(DIAMETRO, DIAMETRO, 0, 0, DIAMETRO, angulos[i], anguloFracoes, COR_FUNDO);
            conteudo.add(fracoesCirculo[i]);
            fracoesCirculo[i].setSize(dimensoes[i]);
        }

        for (FracaoCirculo fracaoCirculo : fracoesCirculo) {
            fracaoCirculo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    fracaoCirculo.setBackground(COR_HOVER);
                    circuloInterno.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    fracaoCirculo.setBackground(COR_FUNDO);
                    circuloInterno.repaint();
                }
            });
        }
    }

    private void criarCirculoInterno() {
        int diametroCirculoInterno = DIAMETRO / 2;
        int circuloInternoX = (DIAMETRO - diametroCirculoInterno) / 2;
        int circuloInternoY = (DIAMETRO - diametroCirculoInterno) / 2;
        circuloInterno.setBounds(circuloInternoX, circuloInternoY, diametroCirculoInterno, diametroCirculoInterno);

        conteudo.add(circuloInterno);
    }

    private void configurarSeletor() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(conteudo);
        setSize(DIAMETRO, DIAMETRO);
        setResizable(false);
        setUndecorated(true);
        setBackground(TRANSPARENTE);
    }
}
