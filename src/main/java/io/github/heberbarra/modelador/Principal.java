package io.github.heberbarra.modelador;

import io.github.heberbarra.modelador.argumento.executador.ExecutadorArgumentos;
import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.banco.UsuarioBanco;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.recurso.AcessadorRecursos;
import io.github.heberbarra.modelador.tradutor.SeletorLinguagem;
import io.github.heberbarra.modelador.tradutor.TradutorWrapper;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@ComponentScan
public class Principal implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>, WebMvcConfigurer {

    public static final String NOME_PROGRAMA = "Heber-Modelo";
    private static final Logger logger = JavaLogger.obterLogger(Principal.class.getName());
    private static final ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();

    public static void main(String[] args) {
        SeletorLinguagem.selecionarLinguagem(args);
        System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        criarArquivoDotEnv();
        ExecutadorArgumentos executadorArgumentos = new ExecutadorArgumentos(args);
        executadorArgumentos.executarFlags();

        configurador.criarArquivos();
        configurador.lerConfiguracao();
        configurador.verificarConfiguracoes();
        configurador.combinarConfiguracoes();

        AtualizadorPrograma atualizador = new AtualizadorPrograma();
        atualizador.atualizar();
        SpringApplication.run(ControladorWeb.class, args);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void criarArquivoDotEnv() {
        File dotEnv = new File(".env");

        if (!dotEnv.exists()) {
            try {
                dotEnv.createNewFile();
            } catch (IOException e) {
                logger.severe(TradutorWrapper.tradutor
                        .traduzirMensagem("error.create.env")
                        .formatted(e.getMessage()));
            }
        }
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(Math.toIntExact(configurador.pegarValorConfiguracao("programa", "porta", long.class)));
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.of("pt", "br"));

        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");

        return localeChangeInterceptor;
    }

    @Bean
    public DataSource dataSource() {
        AcessadorRecursos acessadorRecursos = new AcessadorRecursos();
        String host = acessadorRecursos.pegarValorVariavelAmbiente("MYSQL_HOST");
        String port = acessadorRecursos.pegarValorVariavelAmbiente("MYSQL_PORT");

        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://%s:%s/db_HeberModelo".formatted(host, port))
                .username(UsuarioBanco.ESTUDANTE.getNomeUsuario())
                .password(acessadorRecursos.pegarValorVariavelAmbiente(UsuarioBanco.ESTUDANTE.getNomeVariavelSenha()))
                .build();
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
