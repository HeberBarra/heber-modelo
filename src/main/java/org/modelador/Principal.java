package org.modelador;

import org.modelador.configurador.Configurador;
import org.modelador.programa.JanelaPrincipal;

public class Principal {

    public static void main(String[] args) {
        Configurador.recarregarConfiguracoes();
        JanelaPrincipal janelaPrincipal = new JanelaPrincipal(1080, 720);
    }
}
