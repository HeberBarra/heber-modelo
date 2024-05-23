package org.modelador.programa;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.MouseInfo;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.base.janela.BaseJanela;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;
import org.modelador.diagrama.Diagrama;
import org.modelador.diagrama.Entidade;
import org.modelador.exploraradorarquivos.ExploradorArquivos;
import org.modelador.seletor.SeletorRadial;

public class JanelaPrincipal extends BaseJanela {

    protected Set<File> arquivosAbertos = new HashSet<>();
    protected ExploradorArquivos exploradorArquivos;
    protected Grade grade = criarGrade();
    protected Diagrama diagrama;
    protected BarraSuperior barraSuperior = new BarraSuperior();
    protected BarraLateral barraLateral = new BarraLateral();
    protected JPanel conteudo = new JPanel(null);
    protected int margem;

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);
        conteudo.setBackground(Paleta.pegarCor("cor_fundo"));
        setVisible(true);

        diagrama = new Diagrama("Diagrama 1");
        conteudo.add(diagrama);
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
                margem = grade.getTamanhoQuadrado();
                definirTamanhoPosicaoGrade(grade, tamanhoPanel);
                definirTamanhoBarraSuperior(barraSuperior, tamanhoPanel, grade.getSize());
                definirTamanhoPosicaoBarraLateral(barraLateral, tamanhoPanel, grade.getSize(), barraSuperior.getSize());
                definirTamanhoPosicaoDiagrama(diagrama, grade);
            }
        });

        diagrama.adicionarComponente(new Entidade(), 0, 0, 300, 200);
    }

    private void definirTamanhoPosicaoDiagrama(@NotNull Diagrama diagrama, @NotNull Grade grade) {
        int largura = grade.getWidth() - grade.getEspessuraBorda() * 2;
        int altura = grade.getHeight() - grade.getEspessuraBorda() * 2;
        int x = grade.getX() + grade.getEspessuraBorda();
        int y = grade.getY() + grade.getEspessuraBorda();

        diagrama.setBounds(x, y, largura, altura);
    }

    private @NotNull Grade criarGrade() {
        return new Grade(
                calcularTamanhoGrade(getSize()),
                Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class),
                Paleta.pegarCor("cor_grade"),
                Paleta.pegarCor("cor_borda_grade"),
                Configurador.pegarValorConfiguracao("grade", "espessura_borda", int.class));
    }

    private @NotNull Dimension calcularTamanhoGrade(@NotNull Dimension tamanho) {
        int largura = tamanho.width * 80 / 100;
        int altura = tamanho.height * 85 / 100;

        return new Dimension(largura, altura);
    }

    private void definirTamanhoPosicaoGrade(@NotNull Grade grade, @NotNull Dimension tamanhoPanel) {
        grade.setSize(calcularTamanhoGrade(tamanhoPanel));

        Dimension tamanhoGrade = grade.getSize();
        int x = tamanhoPanel.width - tamanhoGrade.width - margem * 2;
        int y = (tamanhoPanel.height - tamanhoGrade.height) / 2 + margem * 2;
        grade.setLocation(x, y);
        grade.repaint();
    }

    private void definirTamanhoBarraSuperior(
            @NotNull BarraSuperior barraSuperior, @NotNull Dimension tamanhoPanel, @NotNull Dimension tamanhoGrade) {
        barraSuperior.setSize(tamanhoPanel.width, tamanhoPanel.height - tamanhoGrade.height - margem * 4);
    }

    private void definirTamanhoPosicaoBarraLateral(
            @NotNull BarraLateral barraLateral,
            @NotNull Dimension tamanhoPanel,
            @NotNull Dimension tamanhoGrade,
            @NotNull Dimension tamanhoBarraSuperior) {
        int largura = tamanhoPanel.width - tamanhoGrade.width - margem * 4;
        int altura = tamanhoPanel.height - tamanhoBarraSuperior.height;
        barraLateral.setSize(largura, altura);

        int y = tamanhoPanel.height - barraLateral.getHeight();
        barraLateral.setLocation(0, y);
    }

    private void abrirArquivo() {
        exploradorArquivos = new ExploradorArquivos(this, "Abrir arquivo", FileDialog.LOAD);
        Path caminhoArquivo = exploradorArquivos.getCaminhoArquivo();
        arquivosAbertos.add(caminhoArquivo.toFile());
    }

    private void reconfigurarGrade(@NotNull Grade grade) {
        grade.setTamanhoQuadrado(Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class));
        grade.setEspessuraBorda(Configurador.pegarValorConfiguracao("grade", "espessura_borda", int.class));
        grade.setCorBorda(Paleta.pegarCor("cor_borda_grade"));
    }

    private void recarregarComponentes() {
        getContentPane().setBackground(Paleta.pegarCor("cor_fundo"));
        reconfigurarGrade(grade);
        definirTamanhoPosicaoDiagrama(diagrama, grade);

        for (Component componente : getContentPane().getComponents()) {
            if (componente instanceof RecarregamentoComponente) {
                ((RecarregamentoComponente) componente).recarregar();
            }
        }
    }
}
