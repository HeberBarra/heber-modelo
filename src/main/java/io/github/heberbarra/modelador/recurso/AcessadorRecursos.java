package io.github.heberbarra.modelador.recurso;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.tradutor.TradutorWrapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class AcessadorRecursos implements Recurso {

    private static final Logger logger = JavaLogger.obterLogger(AcessadorRecursos.class.getName());
    private Dotenv dotenv;

    @Override
    public InputStream pegarRecurso(String caminhoRecurso) {
        ClassLoader classLoader = AcessadorRecursos.class.getClassLoader();
        InputStream recurso = classLoader.getResourceAsStream(caminhoRecurso);

        if (recurso == null) {
            logger.severe(TradutorWrapper.tradutor
                            .traduzirMensagem("error.resource.read")
                            .formatted(caminhoRecurso) + "Recurso não encontrado.");
            logger.severe(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            System.exit(CodigoSaida.RECURSO_NAO_ENCONTRADO.getCodigo());
        }

        return recurso;
    }

    @Override
    public File pegarArquivoRecurso(String caminhoRecurso) {
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
            logger.severe(TradutorWrapper.tradutor
                    .traduzirMensagem("error.file.create.temp")
                    .formatted(e.getMessage()));
            logger.severe(TradutorWrapper.tradutor.traduzirMensagem("app.end"));
            System.exit(CodigoSaida.ERRO_CRIACAO_ARQUIVO_TEMP.getCodigo());
        }

        return arquivoRecurso;
    }

    @Override
    public Path pegarCaminhoRecurso(String caminhoRecurso) {
        return pegarArquivoRecurso(caminhoRecurso).toPath();
    }

    @Override
    public String pegarValorVariavelAmbiente(String nomeVariavel) {
        if (dotenv == null) {
            dotenv = Dotenv.load();
        }

        return dotenv.get(nomeVariavel);
    }
}
