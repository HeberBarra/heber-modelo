package io.github.heberbarra.modelador;

import io.github.heberbarra.modelador.argumento.executador.ExecutadorArgumentos;
import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import io.github.heberbarra.modelador.configurador.WatcherPastaConfiguracao;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.token.GeradorToken;
import jakarta.annotation.PostConstruct;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableAsync
@Controller
@SpringBootApplication
public class Principal {

    public static final String NOME_PROGRAMA = "Heber-Modelo";
    private static final Logger logger = JavaLogger.obterLogger(Principal.class.getName());
    private static final ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();
    private static String tokenSecreto;

    @Autowired
    private TaskExecutor taskExecutor;

    public static void main(String[] args) {
        criarArquivoDotEnv();
        ExecutadorArgumentos executadorArgumentos = new ExecutadorArgumentos(args);
        executadorArgumentos.executarFlags();

        configurador.criarArquivos();
        configurador.lerConfiguracao();
        configurador.verificarConfiguracoes();
        configurador.combinarConfiguracoes();

        GeradorToken geradorToken = new GeradorToken();
        geradorToken.gerarToken();
        tokenSecreto = geradorToken.getToken();

        AtualizadorPrograma atualizador = new AtualizadorPrograma();
        atualizador.atualizar();
        SpringApplication.run(Principal.class, args);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void criarArquivoDotEnv() {
        File dotEnv = new File(".env");

        if (!dotEnv.exists()) {
            try {
                dotEnv.createNewFile();
            } catch (IOException e) {
                logger.severe("Falha ao tentar criar o arquivo .env. Erro: %s".formatted(e.getMessage()));
            }
        }
    }

    @PostConstruct
    public static void configurarLogger() {
        Logger loggerGlobal = Logger.getLogger("");

        for (Handler handler : loggerGlobal.getHandlers()) {
            loggerGlobal.removeHandler(handler);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void iniciarWatcherConfig() {
        WatcherPastaConfiguracao watcherPastaConfiguracao = new WatcherPastaConfiguracao();
        taskExecutor.execute(watcherPastaConfiguracao);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void abrirWebBrowser() throws IOException {
        if (!configurador.pegarValorConfiguracao("programa", "abrir_navegador_automaticamente", boolean.class)) return;

        logger.info("Abrindo navegador padrão");
        URI uriPrograma;
        try {
            String dominioPrograma = configurador.pegarValorConfiguracao("programa", "dominio", String.class);
            long portaPrograma = configurador.pegarValorConfiguracao("programa", "porta", long.class);
            uriPrograma = new URI("http://%s:%d".formatted(dominioPrograma, portaPrograma));
        } catch (URISyntaxException e) {
            logger.warning(
                    "Ocorreu um erro ao tentar determinar a URL do programa. Será necessário abrir a página do programa manualmente. Erro: %s"
                            .formatted(e.getMessage()));
            return;
        }

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(uriPrograma);
            return;
        }

        String nomeSistemaOperacional = System.getProperty("os.name");
        Runtime runtime = Runtime.getRuntime();

        if (nomeSistemaOperacional.contains("mac")) {
            runtime.exec(new String[] {"open", uriPrograma.toString()});
        } else if (nomeSistemaOperacional.contains("nix") || nomeSistemaOperacional.contains("nux")) {
            runtime.exec(new String[] {"xdg-open", uriPrograma.toString()});
        } else {
            logger.warning("Não foi possível abrir o programa automaticamente no navegador principal.");
        }
    }

    public static void injetarTokenDesligar(ModelMap modelMap) {
        modelMap.addAttribute("desligar", "desligar.html?token=" + tokenSecreto);
    }

    public static void injetarNomePrograma(ModelMap modelMap, String nomePagina) {
        modelMap.addAttribute("programa", NOME_PROGRAMA.replace("-", " ") + nomePagina);
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
        injetarTokenDesligar(modelMap);
        injetarNomePrograma(modelMap, "");
        injetarPaleta(modelMap);

        return "index";
    }

    @RequestMapping({"/login", "/login.html"})
    String login(ModelMap modelMap) {
        injetarTokenDesligar(modelMap);
        injetarNomePrograma(modelMap, " - Login");
        injetarPaleta(modelMap);

        return "login";
    }

    @RequestMapping({"/cadastro", "/cadastro.html"})
    String cadastro(ModelMap modelMap) {
        injetarTokenDesligar(modelMap);
        injetarNomePrograma(modelMap, " - Cadastro");
        injetarPaleta(modelMap);

        return "cadastro";
    }

    @RequestMapping({"/redefinirsenha", "/redefinirsenha.html"})
    String redefinirSenha(ModelMap modelMap) {
        injetarTokenDesligar(modelMap);
        injetarNomePrograma(modelMap, " - Redefinir Senha");
        injetarPaleta(modelMap);

        return "redefinirsenha";
    }

    @RequestMapping({"editor", "editor.html"})
    String editor(ModelMap modelMap) {
        injetarTokenDesligar(modelMap);
        injetarNomePrograma(modelMap, " - Editor de Diagramas");
        injetarPaleta(modelMap);

        return "editor";
    }

    @RequestMapping({"privacidade", "privacidade.html", "politicaprivacidade", "politicaprivacidade.html"})
    String politicaPrivacidade(ModelMap modelMap) {
        injetarNomePrograma(modelMap, " - Política de Privacidade");
        injetarPaleta(modelMap);

        return "politicaPrivacidade";
    }

    @RequestMapping({"termos", "termos.html"})
    String termosGerais(ModelMap modelMap) {
        injetarNomePrograma(modelMap, " - Termos Gerais de Uso");
        injetarPaleta(modelMap);

        return "termosGerais";
    }

    @RequestMapping({"desligar", "desligar.html"})
    String desligar(ModelMap modelMap, @RequestParam("token") String token) {
        injetarTokenDesligar(modelMap);
        injetarPaleta(modelMap);

        if (tokenSecreto.equals(token)) {
            logger.info("Encerrando o programa");
            injetarNomePrograma(modelMap, " - Desligar");
            System.exit(CodigoSaida.OK.getCodigo());
            return "desligar";
        }

        injetarNomePrograma(modelMap, "");
        logger.severe("Alguém tentou encerrar o programa sem utilizar o token secreto");
        return "index";
    }

    @RequestMapping({"novodiagrama.html", "novodiagrama"})
    String novoDiagrama(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Criar Novo Diagrama");

        return "novoDiagrama";
    }
}
