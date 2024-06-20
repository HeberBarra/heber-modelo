package org.modelador.diagrama;

import java.awt.Color;
import org.modelador.base.componente.BordaRedonda;
import org.modelador.base.componente.Componente;
import org.modelador.configurador.Configurador;
import org.modelador.configurador.paleta.Paleta;

public class Entidade extends Componente {

    protected Color corBorda;
    protected int raioBorda;

    public static int obterAlturaPadrao() {
        return Configurador.pegarValorConfiguracao("diagrama", "altura_entidade", int.class);
    }

    public static int obterLarguraPadrao() {
        return Configurador.pegarValorConfiguracao("diagrama", "largura_entidade", int.class);
    }

    public Entidade() {
        super();
        setSize(obterLarguraPadrao(), obterAlturaPadrao());
        restaurarCor();
        restaurarBorda();
    }

    public Entidade(int x, int y, int largura, int altura) {
        super();
        setSize(largura, altura);
        setLocation(x, y);
        restaurarCor();
        restaurarBorda();
    }

    public void restaurarCor() {
        setBackground(Paleta.pegarCor("cor_entidade"));
    }

    public void restaurarBorda() {
        Color corBorda = Paleta.pegarCor("cor_borda_entidade");
        int espessura_entidade = Configurador.pegarValorConfiguracao("diagrama", "espessura_borda_entidade", int.class);
        this.setBorder(new BordaRedonda(corBorda, espessura_entidade, 10, true));
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
