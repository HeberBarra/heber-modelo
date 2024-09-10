package org.modelador.atualizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.modelador.logger.JavaLogger;

public class PegadorVersaoRemota {

    private static final Logger logger = JavaLogger.obterLogger(PegadorVersaoRemota.class.getName());
    private static final URL URL_RELEASES_GITHUB;

    static {
        try {
            URI releasesGithub = new URI("https://api.github.com/repos/HeberBarra/sheepnator/releases/latest");
            URL_RELEASES_GITHUB = releasesGithub.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            logger.severe("A URL contém um erro grave. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(1);
            throw new RuntimeException();
        }
    }

    private String pegarResponseGitHub() {
        HttpsURLConnection connection;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            connection = (HttpsURLConnection) URL_RELEASES_GITHUB.openConnection();
        } catch (IOException e) {
            logger.severe("Um erro ocorreu ao tentar se conectar ao GitHub. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(1);
            return "";
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            logger.severe("Protocolo inválido. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(1);
            return "";
        }

        try {
            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                logger.warning("Código diferente de 200. Código: %d%n".formatted(connection.getResponseCode()));
                return "";
            }
        } catch (IOException e) {
            logger.severe("Um erro ocorreu ao tentar se conectar ao GitHub. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(1);
            return "";
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                stringBuilder.append(linha);
            }

            connection.disconnect();
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.severe("Ocorreu um erro ao tentar ler o corpo da resposta. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(1);
            return "";
        }
    }

    public String pegarVersaoRemota() {
        String resposta = pegarResponseGitHub();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonRespostaHttp jsonRespostaHttp = objectMapper.readValue(resposta, JsonRespostaHttp.class);
            return jsonRespostaHttp.getName();
        } catch (JsonProcessingException e) {
            logger.severe("Ocorreu um erro ao tentar converter a resposta para json. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(1);
        }
        return "";
    }
}
