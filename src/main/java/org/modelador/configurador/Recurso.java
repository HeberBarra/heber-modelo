package org.modelador.configurador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.logger.JavaLogger;

/**
 * Provê acesso facilitado a um arquivo dentro de resources/ gerando uma cópia temporária do recurso solicitado.
 * Não há necessidade de criar um objeto dessa classe.
 * @since v0.0.2-SNAPSHOT
 * */
public class Recurso {

    private static final Logger logger = JavaLogger.obterLogger(Recurso.class.getName());

    /**
     * Provê o recurso solicitado como um {@link InputStream}. Caso não seja possível pegar o recurso solicitado,
     * o programa é encerrado com o código de saída apropriado: {@link CodigoSaida#RECURSO_NAO_ENCONTRADO}.
     * @param caminhoRecurso o caminho até o recurso solicitado, deve-se omitir src/main/resources/ e resources/
     * @return uma {@link InputStream} com os dados do recurso solicitado
     * */
    public static InputStream pegarRecurso(String caminhoRecurso) {
        ClassLoader classLoader = Recurso.class.getClassLoader();
        InputStream recurso = classLoader.getResourceAsStream(caminhoRecurso);

        if (recurso == null) {
            logger.severe("Falha ao tentar ler o arquivo: %s. Recurso não encontrado.".formatted(caminhoRecurso));
            logger.severe("Encerrando o programa");
            System.exit(CodigoSaida.RECURSO_NAO_ENCONTRADO.getCodigo());
        }

        return recurso;
    }

    /**
     * Provê o recurso solicitado como um {@link File}. Encerra o programa com o código de erro apropriado caso não
     * seja possível criar o arquivo temporário: {@link CodigoSaida#ERRO_CRIACAO_ARQUIVO_TEMP}.
     * @param caminhoRecurso o caminho até o recurso solicitado
     * @return um {@link File} que contém os dados do recurso solicitado
     * @see #pegarRecurso(String)
     * */
    public static File pegarArquivoRecurso(String caminhoRecurso) {
        StringBuilder nomeArquivo = new StringBuilder();
        String[] partesArquivo = caminhoRecurso.split("\\.");
        String extensaoArquivo = "";
        File arquivoRecurso = null;

        for (int i = 0; i < partesArquivo.length; i++) {
            if (i == partesArquivo.length - 1) {
                extensaoArquivo = partesArquivo[i];
            }

            nomeArquivo.append(partesArquivo[i]);
        }

        try (InputStream recursoStream = pegarRecurso(caminhoRecurso)) {
            arquivoRecurso = File.createTempFile(nomeArquivo.toString(), extensaoArquivo);
            arquivoRecurso.deleteOnExit();
            Files.copy(recursoStream, arquivoRecurso.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return arquivoRecurso;
        } catch (IOException e) {
            logger.severe("Falha ao tentar criar arquivo temporário. %n%s".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...");
            System.exit(CodigoSaida.ERRO_CRIACAO_ARQUIVO_TEMP.getCodigo());
        }

        return arquivoRecurso;
    }

    /**
     * Provê o recurso como um {@link Path} que possui os dados do recurso solicitado
     * @param caminhoRecurso o caminho até o recurso solicitado
     * @return um {@link Path} que possui os dados do recurso solicitado
     * */
    public static Path pegarCaminhoRecurso(String caminhoRecurso) {
        return pegarArquivoRecurso(caminhoRecurso).toPath();
    }
}
