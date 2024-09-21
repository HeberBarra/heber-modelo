package org.modelador;

import org.modelador.atualizador.Atualizador;
import org.modelador.banco.EjetorArquivosBanco;
import org.modelador.configurador.Configurador;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class Principal {

    public static final String NOME_PROGRAMA = "sheepnator";
    public static final Configurador configurador = new Configurador();

    public static void main(String[] args) {
        if (args.length != 0 && args[0].equals("--version")) {
            System.out.println(Principal.class.getPackage().getImplementationVersion());
            System.exit(0);
        }

        if (args.length != 0 && args[0].equals("--gen-config")) {
            configurador.criarArquivos();
            System.exit(0);
        }

        if (args.length != 0 && args[0].equals("--show-config")) {
            configurador.lerConfiguracoes();
            configurador.mostrarConfiguracoes();
            System.exit(0);
        }

        if (args.length != 0 && args[0].equals("--update")) {
            Atualizador atualizador = new Atualizador();
            atualizador.baixarAtualizacao();
            System.exit(0);
        }

        configurador.criarArquivos();
        configurador.lerConfiguracoes();
        configurador.verificarConfiguracoes();
        configurador.combinarConfiguracoes();

        if (args.length != 0 && args[0].equals("--eject-database-scripts")) {
            EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
            ejetorArquivosBanco.ejetarScriptsConfiguracao();
            System.exit(0);
        }

        if (args.length != 0 && args[0].equals("--eject-docker-compose")) {
            EjetorArquivosBanco ejetorArquivosBanco = new EjetorArquivosBanco();
            ejetorArquivosBanco.ejetarDockerCompose();
            System.exit(0);
        }

        Atualizador atualizador = new Atualizador();
        atualizador.atualizar();
        SpringApplication.run(Principal.class, args);
    }

    @RequestMapping({"/", "/index", "/index.html", "/home", "/home.html"})
    String index(ModelMap modelMap) {
        return "index";
    }
}
