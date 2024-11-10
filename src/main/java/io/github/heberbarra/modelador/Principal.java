package io.github.heberbarra.modelador;

import io.github.heberbarra.modelador.argumento.executador.ExecutadorArgumentos;
import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class Principal {

    public static final String NOME_PROGRAMA = "Heber-Modelo";
    private static final ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();

    public static void main(String[] args) {
        ExecutadorArgumentos executadorArgumentos = new ExecutadorArgumentos(args);
        executadorArgumentos.executarFlags();

        configurador.criarArquivos();
        configurador.lerConfiguracao();
        configurador.verificarConfiguracoes();
        configurador.combinarConfiguracoes();

        AtualizadorPrograma atualizador = new AtualizadorPrograma();
        atualizador.atualizar();
        SpringApplication.run(Principal.class, args);
    }

    public static void injetarPaleta(ModelMap modelMap) {
        Map<String, String> variaveisPaleta = configurador.pegarInformacoesPaleta();
        StringBuilder stringBuilder = new StringBuilder(":root {\n");

        for (String nome : variaveisPaleta.keySet()) {
            stringBuilder.append("    --%s: %s;%n".formatted(nome.replace("_", "-"), variaveisPaleta.get(nome)));
        }

        stringBuilder.append("  }\n");
        modelMap.addAttribute("paleta", stringBuilder.toString());
    }

    @RequestMapping({"/", "/index", "/index.html", "/home", "/home.html"})
    String index(ModelMap modelMap) {
        injetarPaleta(modelMap);

        return "index";
    }

    @RequestMapping({"/login", "/login.html"})
    String login(ModelMap modelMap) {
        injetarPaleta(modelMap);

        return "login";
    }

    @RequestMapping({"/cadastro", "/cadastro.html"})
    String cadastro(ModelMap modelMap) {
        injetarPaleta(modelMap);

        return "cadastro";
    }

    @RequestMapping({"/redefinirsenha", "/redefinirsenha.html"})
    String redefinirSenha(ModelMap modelMap) {
        injetarPaleta(modelMap);

        return "redefinirsenha";
    }

    @RequestMapping({"editor", "editor.html"})
    String editor(ModelMap modelMap) {
        injetarPaleta(modelMap);

        return "editor";
    }

    @RequestMapping({"privacidade", "privacidade.html", "politicaprivacidade", "politicaprivacidade.html"})
    String politicaPrivacidade(ModelMap modelMap) {
        injetarPaleta(modelMap);
        return "politicaprivacidade";
    }
}
