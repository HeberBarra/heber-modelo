package org.modelador.programa;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.base.janela.BaseJanela;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;
import org.modelador.exploraradorarquivos.ExploradorArquivos;
import org.modelador.seletor.SeletorRadial;

public class JanelaPrincipal extends BaseJanela {
    // TODO: Fazer lógica par a ler o conteúdo de um arquivo aberto

    protected Set<File> arquivosAbertos = new HashSet<>();
    protected ExploradorArquivos exploradorArquivos;
    protected Grade grade = criarGrade();
    protected BarraSuperior barraSuperior = new BarraSuperior();
    protected BarraLateral barraLateral = new BarraLateral();
    protected JPanel conteudo = new JPanel(null);

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);
        conteudo.setBackground(Paleta.pegarCor("cor_fundo"));
        setVisible(true);

        conteudo.add(grade);
        conteudo.add(barraSuperior);
        conteudo.add(barraLateral);
        barraLateral.setBackground(Paleta.pegarCor("cor_barra_lateral"));
        barraSuperior.setBackground(Paleta.pegarCor("cor_barra_superior"));

        setContentPane(conteudo);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                if (evento.getKeyCode() == KeyEvent.VK_SPACE) {
                    new SeletorRadial(MouseInfo.getPointerInfo().getLocation());
                }

                if (evento.getKeyCode() == KeyEvent.VK_F1) {
                    Configurador.recarregarConfiguracoes();
                    recarregarComponentes();
                }

                if (evento.getKeyCode() == KeyEvent.VK_F2) {
                    abrirArquivo();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension tamanhoPanel = conteudo.getSize();
                int margem = grade.getTamanhoQuadrado();
                mudarGrade(tamanhoPanel, margem);
                mudarBarraSuperior(tamanhoPanel, margem);
                mudarBarraLateral(tamanhoPanel, margem);
            }
        });
    }

    protected Grade criarGrade() {
        return new Grade(
                calcularTamanhoGrade(getSize()),
                Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class),
                Paleta.pegarCor("cor_grade"),
                Paleta.pegarCor("cor_borda_grade"),
                Configurador.pegarValorConfiguracao("grade", "espessura_borda", int.class));
    }

    protected Dimension calcularTamanhoGrade(Dimension tamanho) {
        int largura = tamanho.width * 80 / 100;
        int altura = tamanho.height * 85 / 100;

        return new Dimension(largura, altura);
    }

    protected Point calcularPosicaoGrade(Dimension tamanhoPanel, Dimension tamanhoGrade, int margem) {
        int x = tamanhoPanel.width - tamanhoGrade.width + margem;
        int y = (tamanhoPanel.height - tamanhoGrade.height) / 2 + margem * 3;

        return new Point(x, y);
    }

    protected void mudarGrade(Dimension tamanhoPanel, int margem) {
        Dimension tamanhoGrade = calcularTamanhoGrade(tamanhoPanel);
        Point posicaoGrade = calcularPosicaoGrade(tamanhoPanel, tamanhoGrade, margem);

        grade.setSize(tamanhoGrade);
        grade.setLocation(posicaoGrade);
        grade.repaint();
    }

    protected Dimension calcularTamanhoBarraSuperior(Dimension tamanhoPanel, Dimension tamanhoGrade, int margem) {
        int altura = tamanhoPanel.height - tamanhoGrade.height - margem * 4;

        return new Dimension(tamanhoPanel.width, altura);
    }

    protected void mudarBarraSuperior(Dimension tamanhoPanel, int margem) {
        barraSuperior.setSize(calcularTamanhoBarraSuperior(tamanhoPanel, grade.getSize(), margem));
    }

    protected Dimension calcularTamanhoBarraLateral(
            Dimension tamanhoPanel, Dimension tamanhoGrade, Dimension tamanhoBarraSuperior, int margem) {
        int largura = tamanhoPanel.width - tamanhoGrade.width - margem * 4;
        int altura = tamanhoPanel.height - tamanhoBarraSuperior.height;

        return new Dimension(largura, altura);
    }

    protected Point calcularPosicaoBarraLateral(Dimension tamanhoPanel, Dimension tamanhoBarraLateral) {
        return new Point(0, tamanhoPanel.height - tamanhoBarraLateral.height);
    }

    protected void mudarBarraLateral(Dimension tamanhoPanel, int margem) {
        Dimension tamanhoGrade = grade.getSize();
        Dimension tamanhoBarraSuperior = barraSuperior.getSize();
        Dimension tamanhoBarraLateral =
                calcularTamanhoBarraLateral(tamanhoPanel, tamanhoGrade, tamanhoBarraSuperior, margem);
        Point posicaoBarraLateral = calcularPosicaoBarraLateral(tamanhoPanel, tamanhoBarraLateral);

        barraLateral.setSize(tamanhoBarraLateral);
        barraLateral.setLocation(posicaoBarraLateral);
    }

    private void abrirArquivo() {
        exploradorArquivos = new ExploradorArquivos(this, "Abrir arquivo", FileDialog.LOAD);
        Path caminhoArquivo = exploradorArquivos.getCaminhoArquivo();
        arquivosAbertos.add(caminhoArquivo.toFile());
    }

    private void recarregarComponentes() {
        getContentPane().setBackground(Paleta.pegarCor("cor_fundo"));
        grade.setTamanhoQuadrado(Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class));
        grade.setEspessuraBorda(Configurador.pegarValorConfiguracao("grade", "espessura_borda", int.class));
        grade.setCorBorda(Paleta.pegarCor("cor_borda_grade"));

        for (Component componente : getContentPane().getComponents()) {
            if (componente instanceof RecarregamentoComponente) {
                ((RecarregamentoComponente) componente).recarregar();
            }
        }
    }
}
