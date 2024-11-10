package io.github.heberbarra.modelador.atualizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
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

/**
 * Pega a versão semântica da última release do repositório remoto.
 * @since v0.0.3-SNAPSHOT
 * */
public class PegadorVersaoRemota {

    private static final Logger logger = JavaLogger.obterLogger(PegadorVersaoRemota.class.getName());
    private static final URL URL_RELEASES_GITHUB;

    static {
        try {
            URI releasesGithub = new URI("https://api.github.com/repos/HeberBarra/%s/releases/latest"
                    .formatted(Principal.NOME_PROGRAMA.toLowerCase()));
            URL_RELEASES_GITHUB = releasesGithub.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            logger.severe("A URL contém um erro grave. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(CodigoSaida.ERRO_CRIACAO_URLS.getCodigo());
            throw new RuntimeException();
        }
    }

    /**
     * Envia uma request HTTPS ao repositório do GitHub e retorna a response obtida. Caso ocorra algum erro durante a
     * request o programa pode ser encerrando com um dos seguintes erros: {@link CodigoSaida#ERRO_CONEXAO},
     * {@link CodigoSaida#PROTOCOLO_INVALIDO}, {@link CodigoSaida#ERRO_LEITURA_RESPONSE}, {@link CodigoSaida#ERRO_CONVERSAO_RESPONSE}
     * <p>
     * Caso a request tenha sido bem sucedida retorna o corpo da response, caso contrário retorna uma string vazia.
     * @return o corpo da response obtida ou string vazia se tiver ocorrido algum erro
     * */
    private String pegarResponseGitHub() {
        HttpsURLConnection connection;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            connection = (HttpsURLConnection) URL_RELEASES_GITHUB.openConnection();
        } catch (IOException e) {
            logger.severe("Um erro ocorreu ao tentar se conectar ao GitHub. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(CodigoSaida.ERRO_CONEXAO.getCodigo());
            return "";
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            logger.severe("Protocolo inválido. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(CodigoSaida.PROTOCOLO_INVALIDO.getCodigo());
            return "";
        }

        try {
            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                logger.warning("Código diferente de 200. Código: %d%n".formatted(connection.getResponseCode()));
                return "";
            }
        } catch (IOException e) {
            logger.warning("Um erro ocorreu ao tentar se conectar ao GitHub. %s%n".formatted(e.getMessage()));
            logger.warning("Verifique sua conexão de internet.");
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
            System.exit(CodigoSaida.ERRO_LEITURA_RESPONSE.getCodigo());
            return "";
        }
    }

    /**
     * Envia uma request ao repositório remoto e pega o atributo versão da response.
     * @return a última versão semântica do programa
     * @see PegadorVersaoRemota#pegarResponseGitHub()
     * */
    public String pegarVersaoRemota() {
        String resposta = pegarResponseGitHub();

        if (resposta.isEmpty()) {
            return "";
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonRespostaHttp jsonRespostaHttp = objectMapper.readValue(resposta, JsonRespostaHttp.class);
            return jsonRespostaHttp.getName();
        } catch (JsonProcessingException e) {
            logger.severe("Ocorreu um erro ao tentar converter a resposta para json. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(CodigoSaida.ERRO_CONVERSAO_RESPONSE.getCodigo());
        }
        return "";
    }
}
