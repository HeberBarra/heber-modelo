package org.modelador;

import org.modelador.configurador.Configurador;
import org.modelador.programa.JanelaPrincipal;

public class Principal {

    public static JanelaPrincipal janelaPrincipal;

    public static void main(String[] args) {
        if (args.length != 0 && args[0].equals("--version")) {
            System.out.println(Principal.class.getPackage().getImplementationVersion());
            System.exit(0);
        }

        Configurador.recarregarConfiguracoes();
        janelaPrincipal = new JanelaPrincipal(1080, 720);
    }
}
