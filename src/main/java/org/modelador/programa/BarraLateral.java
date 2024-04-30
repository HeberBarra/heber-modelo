package org.modelador.programa;

import javax.swing.JPanel;
import org.modelador.base.componente.RecarregamentoComponente;
import org.modelador.configurador.paleta.Paleta;

public class BarraLateral extends JPanel implements RecarregamentoComponente {

    public BarraLateral() {
        super();
    }

    @Override
    public void recarregar() {
        setBackground(Paleta.pegarCor("cor_barra_lateral"));
    }
}
