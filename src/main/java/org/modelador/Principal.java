package org.modelador;

import org.modelador.configurador.Configurador;
import org.modelador.programa.JanelaPrincipal;

public class Principal {

    public static void main(String[] args) {
        Configurador.criarArquivoConfiguracoes();

        JanelaPrincipal janelaPrincipal = new JanelaPrincipal(1080, 720);
    }
}
