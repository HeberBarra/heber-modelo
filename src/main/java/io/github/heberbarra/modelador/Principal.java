package io.github.heberbarra.modelador;

import io.github.heberbarra.modelador.argumento.executador.ExecutadorArgumentos;
import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.configurador.ConfiguradorPrograma;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Principal implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private static final Logger logger = JavaLogger.obterLogger(Principal.class.getName());
    public static final String NOME_PROGRAMA = "Heber-Modelo";
    private static final ConfiguradorPrograma configurador = ConfiguradorPrograma.getInstance();

    public static void main(String[] args) {
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
                logger.severe("Falha ao tentar criar o arquivo .env. Erro: %s".formatted(e.getMessage()));
            }
        }
    }

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(Math.toIntExact(configurador.pegarValorConfiguracao("programa", "porta", long.class)));
    }
}
