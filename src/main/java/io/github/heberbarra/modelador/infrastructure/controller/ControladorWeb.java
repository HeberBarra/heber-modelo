/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.infrastructure.controller;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.application.diagrama.ListadorTiposDiagrama;
import io.github.heberbarra.modelador.application.logging.JavaLogger;
import io.github.heberbarra.modelador.application.tradutor.TradutorWrapper;
import io.github.heberbarra.modelador.application.usecase.gerar.GeradorToken;
import io.github.heberbarra.modelador.domain.codigo.CodigoSaida;
import io.github.heberbarra.modelador.domain.configuracao.IConfigurador;
import io.github.heberbarra.modelador.domain.model.NovoDiagramaDTO;
import io.github.heberbarra.modelador.domain.model.UsuarioDTO;
import io.github.heberbarra.modelador.infrastructure.configuracao.ConfiguradorPrograma;
import io.github.heberbarra.modelador.infrastructure.configuracao.LeitorConfiguracao;
import io.github.heberbarra.modelador.infrastructure.configuracao.WatcherPastaConfiguracao;
import io.github.heberbarra.modelador.infrastructure.entity.Usuario;
import io.github.heberbarra.modelador.infrastructure.security.IUsuarioServices;
import jakarta.annotation.PostConstruct;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tomlj.TomlTable;

@EnableAsync
@Controller
@SpringBootApplication
@Service
public class ControladorWeb {

    private static final Logger logger = JavaLogger.obterLogger(ControladorWeb.class.getName());
    private static final String TOKEN_SECRETO;
    private static final IConfigurador configurador;
    private final TaskExecutor taskExecutor;
    private final IUsuarioServices usuarioServices;

    public ControladorWeb(
            @Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor, IUsuarioServices usuarioServices) {
        this.taskExecutor = taskExecutor;
        this.usuarioServices = usuarioServices;
    }

    static {
        configurador = ConfiguradorPrograma.getInstance();
        GeradorToken geradorToken = new GeradorToken();
        geradorToken.gerarToken();
        TOKEN_SECRETO = geradorToken.getToken();
    }

    private static void injetarTokenDesligar(ModelMap modelMap) {
        if (configurador.pegarValorConfiguracao("programa", "desativar_botao_desligar", boolean.class)) return;

        modelMap.addAttribute("desligar", TOKEN_SECRETO);
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

    public static void injetarBindings(ModelMap modelMap) {
        ConfiguradorPrograma configuradorPrograma = (ConfiguradorPrograma) configurador;
        LeitorConfiguracao leitorConfiguracao = configuradorPrograma.getLeitorConfiguracao();
        TomlTable tabelaBindings =
                leitorConfiguracao.getInformacoesConfiguracoes().getTable("bindings");

        if (tabelaBindings == null) return;

        for (String nomeBinding : tabelaBindings.dottedKeySet()) {
            modelMap.addAttribute(nomeBinding, tabelaBindings.get(nomeBinding));
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
    public void exibirMensagemProgramaPronto() {
        String host = configurador.pegarValorConfiguracao("programa", "dominio", String.class);
        int porta = Math.toIntExact(configurador.pegarValorConfiguracao("programa", "porta", long.class));
        logger.info(TradutorWrapper.tradutor.traduzirMensagem("app.ready").formatted(host, porta));
    }

    @SuppressWarnings("HttpUrlsUsage")
    @EventListener(ApplicationReadyEvent.class)
    public void abrirWebBrowser() throws IOException {
        if (!configurador.pegarValorConfiguracao("programa", "abrir_navegador_automaticamente", boolean.class)) return;

        logger.info(TradutorWrapper.tradutor.traduzirMensagem("app.opening.browser"));
        URI uriPrograma;
        try {
            String dominioPrograma = configurador.pegarValorConfiguracao("programa", "dominio", String.class);
            long portaPrograma = configurador.pegarValorConfiguracao("programa", "porta", long.class);
            uriPrograma = new URI("http://%s:%d".formatted(dominioPrograma, portaPrograma));
        } catch (URISyntaxException e) {
            logger.warning(TradutorWrapper.tradutor
                    .traduzirMensagem("error.browser.cannot.create.url")
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
        } else if (nomeSistemaOperacional.contains("windows")) {
            runtime.exec(new String[] {"powershell.exe", "-Command", uriPrograma.toString()});
        } else {
            logger.warning(TradutorWrapper.tradutor.traduzirMensagem("error.browser.cannot.open"));
        }
    }

    @RequestMapping({"/", "/index", "/index.html", "home", "home.html"})
    public String index(ModelMap modelMap) {
        injetarTokenDesligar(modelMap);
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, "");

        return "index";
    }

    @GetMapping({"/cadastro", "/cadastro.html"})
    public String cadastro(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Cadastro");
        modelMap.addAttribute("usuario", new UsuarioDTO());

        return "cadastro";
    }

    @PostMapping({"/cadastro", "/cadastro.html"})
    public String cadastro(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {

        if (usuarioDTO.getTipo() == null) {
            usuarioDTO.setTipo("E");
        }

        usuarioDTO.setNome(usuarioDTO.getNome().trim());
        usuarioDTO.setEmail(usuarioDTO.getEmail().trim());
        usuarioDTO.setSenha(usuarioDTO.getSenha().trim());
        usuarioDTO.setConfirmarSenha(usuarioDTO.getConfirmarSenha().trim());

        if (usuarioServices.findUserByMatricula(usuarioDTO.getMatricula()) != null
                || usuarioServices.findUserByNome(usuarioDTO.getNome()) != null
                || usuarioServices.findUserByEmail(usuarioDTO.getEmail()) != null) {
            return "redirect:/cadastro.html?exists";
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmarSenha())) {
            return "redirect:cadastro.html?mismatch";
        }

        Pattern regexEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (!regexEmail.matcher(usuarioDTO.getEmail()).matches()) {
            return "redirect:/cadastro.html?invalidEmail";
        }

        usuarioServices.saveUsuario(usuarioDTO);
        return "redirect:/login.html?cadastroSuccess";
    }

    @RequestMapping({"/login", "/login.html"})
    public String login(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Login");

        if (userDetails == null) return "login";

        return "redirect:/";
    }

    @RequestMapping({"/redefinir", "/redefinir.html"})
    public String redefinirSenha(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Redefinir Senha");

        return "redefinir";
    }

    @RequestMapping({"solicitar", "solicitar.html"})
    public String solicitarNovaSenha(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Solicitar Nova Senha");

        return "solicitar";
    }

    @RequestMapping(
            value = {"/editor", "/editor.html"},
            method = {RequestMethod.GET, RequestMethod.POST})
    public String editor(ModelMap modelMap, @ModelAttribute("novoDiagramaDTO") NovoDiagramaDTO novoDiagramaDTO) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Editor");
        injetarBindings(modelMap);

        if (configurador.pegarValorConfiguracao("grade", "exibir", boolean.class)) {
            long tamanhoQuadradoGrade = configurador.pegarValorConfiguracao("grade", "tamanho_quadrado_px", long.class);
            long espessuraGrade = configurador.pegarValorConfiguracao("grade", "espessura", long.class);

            modelMap.addAttribute("tamanhoQuadradoGrade", tamanhoQuadradoGrade + "px");
            modelMap.addAttribute("espessuraGrade", espessuraGrade + "px");
        }

        modelMap.addAttribute(
                "incrementoMovimentacaoElemento",
                configurador.pegarValorConfiguracao("editor", "incrementoMovimentacaoElemento", long.class));
        modelMap.addAttribute("novoDiagramaDTO", novoDiagramaDTO);
        modelMap.addAttribute("diagramasUML", ListadorTiposDiagrama.pegarDiagramasUML());
        modelMap.addAttribute("diagramasBD", ListadorTiposDiagrama.pegarDiagramasBancoDados());
        modelMap.addAttribute("diagramasOutros", ListadorTiposDiagrama.pegarDiagramasOutros());

        return "editor";
    }

    @RequestMapping({"/novo", "/novo.html"})
    public String novoDiagrama(ModelMap modelMap) {
        injetarPaleta(modelMap);
        injetarNomePrograma(modelMap, " - Criar Novo Diagrama");
        modelMap.addAttribute("novoDiagramaDTO", new NovoDiagramaDTO());
        modelMap.addAttribute("diagramasUML", ListadorTiposDiagrama.pegarDiagramasUML());
        modelMap.addAttribute("diagramasBD", ListadorTiposDiagrama.pegarDiagramasBancoDados());
        modelMap.addAttribute("diagramasOutros", ListadorTiposDiagrama.pegarDiagramasOutros());

        return "novo";
    }

    @GetMapping({"desligar", "desligar.html"})
    public String desligar() {
        return "redirect:index";
    }

    @PostMapping({"/desligar", "/desligar.html"})
    public void desligar(ModelMap modelMap, @RequestParam("token") String token) {
        injetarTokenDesligar(modelMap);
        injetarPaleta(modelMap);

        if (configurador.pegarValorConfiguracao("programa", "desativar_botao_desligar", boolean.class)) return;

        if (TOKEN_SECRETO.equals(token)) {
            logger.info(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            injetarNomePrograma(modelMap, " - Desligar");
            System.exit(CodigoSaida.OK.getCodigo());
        }

        injetarNomePrograma(modelMap, "");
        logger.severe(TradutorWrapper.tradutor.traduzirMensagem("error.shutdown.invalid.token"));
    }

    @RequestMapping({"/perfil", "/perfil.html"})
    public String perfil(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap) {
        injetarNomePrograma(modelMap, " - Perfil");
        injetarPaleta(modelMap);

        if (userDetails == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioServices.findUserByNome(userDetails.getUsername());
        modelMap.addAttribute("usuario", usuario);

        return "perfil";
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
