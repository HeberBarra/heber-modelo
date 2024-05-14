package org.modelador.diagrama;

import java.awt.Color;
import org.modelador.base.componente.Componente;

public class Entidade extends Componente {

    protected Color corBorda;
    protected int raioBorda;

    public Entidade() {
        super();
        setBackground(Color.CYAN);
    }

    public Color getCorBorda() {
        return corBorda;
    }

    public void setCorBorda(Color corBorda) {
        this.corBorda = corBorda;
    }

    public int getRaioBorda() {
        return raioBorda;
    }

    public void setRaioBorda(int raioBorda) {
        this.raioBorda = raioBorda;
    }
}
