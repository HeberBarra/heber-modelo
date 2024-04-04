package org.modelador.programa;

import org.modelador.base.janela.BaseJanela;
import org.modelador.seletor.SeletorRadial;

import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JanelaPrincipal extends BaseJanela {

    public final int DIAMETRO_SELETOR_RADIAL = 150;

    public JanelaPrincipal(int largura, int altura) {
        super("DER-MODELADOR", largura, altura);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                    new SeletorRadial(DIAMETRO_SELETOR_RADIAL, MouseInfo.getPointerInfo().getLocation());
                }
            }
        });
    }

}
