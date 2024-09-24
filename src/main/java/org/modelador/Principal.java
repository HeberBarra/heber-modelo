package org.modelador;

import org.modelador.argumento.AnalisadorArgumentos;
import org.modelador.atualizador.Atualizador;
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
        AnalisadorArgumentos analisadorArgumentos = new AnalisadorArgumentos(args);
        analisadorArgumentos.analisarArgumentos();

        configurador.criarArquivos();
        configurador.lerConfiguracoes();
        configurador.verificarConfiguracoes();
        configurador.combinarConfiguracoes();

        Atualizador atualizador = new Atualizador();
        atualizador.atualizar();
        SpringApplication.run(Principal.class, args);
    }

    @RequestMapping({"/", "/index", "/index.html", "/home", "/home.html"})
    String index(ModelMap modelMap) {
        return "index";
    }

    @RequestMapping({"/login", "/login.html"})
    String login(ModelMap modelMap) {
        return "login";
    }

    @RequestMapping({"/cadastro", "/cadastro.html"})
    String cadastro(ModelMap modelMap) {
        return "cadastro";
    }

    @RequestMapping({"/redefinirsenha", "/redefinirsenha.html"})
    String redefinirSenha(ModelMap modelMap) {
        return "redefinirsenha";
    }

}
