package org.modelador.programa;

import java.awt.Component;
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

    private void criarGrade() {
        grade = new Grade(
                larguraGrade,
                alturaGrade,
                Configurador.pegarValorConfiguracao("grade", "tamanho_quadrado", int.class),
                Paleta.pegarCor("cor_grade"),
                Paleta.pegarCor("cor_borda_grade"),
                Configurador.pegarValorConfiguracao("grade", "espessura_borda", int.class));
        getContentPane().setLayout(null);
        getContentPane().add(grade);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int tamanho_quadrado = grade.getTamanhoQuadrado();
                larguraGrade = getWidth() - tamanho_quadrado;
                alturaGrade = getHeight() - tamanho_quadrado;
                grade.setLocation(tamanho_quadrado, tamanho_quadrado);
                grade.setSize(larguraGrade, alturaGrade);
                grade.repaint();
            }
        });
    }
}
