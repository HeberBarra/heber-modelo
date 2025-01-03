package io.github.heberbarra.modelador;

import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.configurador.Configurador;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import io.github.heberbarra.modelador.configurador.WatcherPastaConfiguracao;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.token.GeradorToken;
import jakarta.annotation.PostConstruct;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableAsync
@Controller
@SpringBootApplication
@Service
public class ControladorWeb {

    private static final Logger logger = JavaLogger.obterLogger(ControladorWeb.class.getName());
    private static final String TOKEN_SECRETO;
    private static final Configurador configurador;
    private final TaskExecutor taskExecutor;

    public ControladorWeb(@Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    static {
        configurador = ConfiguradorPrograma.getInstance();
        GeradorToken geradorToken = new GeradorToken();
        geradorToken.gerarToken();
        TOKEN_SECRETO = geradorToken.getToken();
    }

    private static void injetarToken(ModelMap modelMap) {
        modelMap.addAttribute("desligar", "desligar.html?token=" + TOKEN_SECRETO);
    }

    public static void injetarNomePrograma(ModelMap modelMap, String nomePagina) {
        modelMap.addAttribute("programa", Principal.NOME_PROGRAMA.replace("-", " ") + nomePagina);
    }

    public static void injetarPaleta(ModelMap modelMap) {
        Map<String, String> variaveisPaleta = configurador.pegarInformacoesPaleta();
        StringBuilder stringBuilder = new StringBuilder(":root {\n");

        for (String variavel : variaveisPaleta.keySet()) {
            stringBuilder.append(
                    "    --%s: %s;%n".formatted(variavel.replace("_", "-"), variaveisPaleta.get(variavel)));
        }

        stringBuilder.append("  }\n");
        modelMap.addAttribute("paleta", stringBuilder.toString());
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
    public void exibirMensagemProgramaPronto() {
        String host = configurador.pegarValorConfiguracao("programa", "dominio", String.class);
        int porta = Math.toIntExact(configurador.pegarValorConfiguracao("programa", "porta", long.class));
        logger.info(Principal.tradutor.traduzirMensagem("app.ready").formatted(host, porta));
    }

    @SuppressWarnings("HttpUrlsUsage")
    @EventListener(ApplicationReadyEvent.class)
    public void abrirWebBrowser() throws IOException {
        if (!configurador.pegarValorConfiguracao("programa", "abrir_navegador_automaticamente", boolean.class)) return;

        logger.info(Principal.tradutor.traduzirMensagem("app.opening.browser"));
        URI uriPrograma;
        try {
            String dominioPrograma = configurador.pegarValorConfiguracao("programa", "dominio", String.class);
            long portaPrograma = configurador.pegarValorConfiguracao("programa", "porta", long.class);
            uriPrograma = new URI("http://%s:%d".formatted(dominioPrograma, portaPrograma));
        } catch (URISyntaxException e) {
            logger.warning(Principal.tradutor
                    .traduzirMensagem("error.browser.cant.create.url")
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
            logger.warning(Principal.tradutor.traduzirMensagem("error.browser.cant.open"));
        }
    }

    @RequestMapping({"/", "/index", "/index.html", "home", "home.html"})
    public String index(ModelMap modelMap) {
        injetarToken(modelMap);
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, "");

        return "index";
    }

    @RequestMapping({"/cadastro", "/cadastro.html"})
    public String cadastro(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Cadastro");

        return "cadastro";
    }

    @RequestMapping({"/login", "/login.html"})
    public String login(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Login");

        return "login";
    }

    @RequestMapping({"/redefinir", "/redefinir.html"})
    public String redefinirSenha(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Redefinir Senha");

        return "redefinir";
    }

    @RequestMapping({"/editor", "/editor.html"})
    public String editor(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Editor");

        return "editor";
    }

    @RequestMapping({"/novo", "/novo.html"})
    public String novoDiagrama(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Criar Novo Diagrama");

        return "novo";
    }

    @RequestMapping({"/desligar", "/desligar.html"})
    public String desligar(ModelMap modelMap, @RequestParam("token") String token) {
        injetarToken(modelMap);
        injetarPaleta(modelMap);

        if (TOKEN_SECRETO.equals(token)) {
            logger.info(Principal.tradutor.traduzirMensagem("app.end"));
            injetarNomePrograma(modelMap, " - Desligar");
            System.exit(CodigoSaida.OK.getCodigo());
            return "desligar";
        }

        injetarNomePrograma(modelMap, "");
        logger.severe("Alguém tentou encerrar o programa sem utilizar o token secreto");
        return "index";
    }

    @RequestMapping({"/privacidade", "/privacidade.html"})
    public String politicaPrivacidade(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Política de Privacidade");

        return "privacidade";
    }

    @RequestMapping({"/termos", "/termos.html"})
    public String termosGerais(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Termos Gerais de Uso");

        return "termos";
    }
}
