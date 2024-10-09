package org.modelador.atualizador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;
import org.modelador.calculadorhash.CalculadorHash;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.configurador.Configurador;
import org.modelador.logger.JavaLogger;

public class Atualizador {

    public static final URL URL_ARQUIVO_JAR;
    public static final URL URL_ARQUIVO_SHA256;
    private static final Logger logger = JavaLogger.obterLogger(Atualizador.class.getName());
    private final Configurador configurador;
    private final ComparadorVersao comparadorVersao;

    static {
        try {
            URI arquivoJar =
                    new URI("https://github.com/HeberBarra/heber-modelo/releases/latest/download/heber-modelo.jar");
            URI arquivoSha256 = new URI("https://github.com/HeberBarra/heber-modelo/releases/latest/download/SHA256SUM");
            URL_ARQUIVO_JAR = arquivoJar.toURL();
            URL_ARQUIVO_SHA256 = arquivoSha256.toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            logger.severe("Ocorreu um erro ao tentar criar as URLs necessárias. %s%n".formatted(e.getMessage()));
            logger.severe("Encerrando o programa...\n");
            System.exit(CodigoSaida.ERRO_CRIACAO_URLS.getCodigo());
            throw new RuntimeException();
        }
    }

    public Atualizador() {
        configurador = Configurador.getInstance();
        PegadorVersaoRemota pegadorVersaoRemota = new PegadorVersaoRemota();
        String versaoPrograma = Atualizador.class.getPackage().getImplementationVersion();
        String versaoRemota = pegadorVersaoRemota.pegarVersaoRemota();

        if (versaoRemota.isEmpty()) {
            versaoRemota = versaoPrograma;
        }

        comparadorVersao = new ComparadorVersao(versaoPrograma, versaoRemota);
    }

    public boolean verificarAtualizacao() {
        return comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;
    }

    public void atualizar() {
        if (!verificarAtualizacao()) {
            logger.info("Não há nenhuma atualização disponível.\n");
            return;
        }

        logger.info("Atualização disponível.\n");
        if (configurador.pegarValorConfiguracao("atualizador", "atualizacao_automatica", boolean.class)) {
            baixarAtualizacao();
            return;
        }

        logger.info("Atualização automática está desativada, utilize --update para atualizar o programa.");
    }

    public void baixarAtualizacao() {
        File pastaTemporaria = new File("./tmp");
        if (pastaTemporaria.mkdir()) {
            logger.info("Pasta temporária para downloads foi criada com sucesso.\n");
        }

        baixarArquivo(pastaTemporaria, URL_ARQUIVO_JAR, "heber-modelo.jar");
        baixarArquivo(pastaTemporaria, URL_ARQUIVO_SHA256, "SHA256SUM");

        String hashArquivoJar = "";
        try {
            byte[] dados = Files.readAllBytes(new File("./tmp/heber-modelo.jar").toPath());
            CalculadorHash calculadorHash = new CalculadorHash();
            hashArquivoJar = calculadorHash.calcularHash256(dados);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        String hashChecksum;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./tmp/SHA256SUM"))) {
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                if (linha.contains("heber-modelo.jar")) {
                    break;
                }
            }

            if (linha == null) {
                logger.warning("Não foi possível verificar o arquivo da atualização. Cancelando...\n");
                return;
            }

            hashChecksum = linha.split(" ")[0];
        } catch (IOException e) {
            logger.warning("Ocorreu um erro ao tentar ler o arquivo de verificação. %s%n".formatted(e.getMessage()));
            logger.warning("Cancelando a atualização... \n");
            return;
        }

        if (!hashChecksum.equals(hashArquivoJar)) {
            logger.warning("Falha na verificação do arquivo.\n");
            logger.warning("Cancelando a operação...\n");
            return;
        }

        try {
            Files.move(
                    Path.of("./tmp/heber-modelo.jar"), Path.of("./heber-modelo.jar"), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Programa atualizado com sucesso.\n");
            logger.info("É necessário reiniciar o programa para que as atualizações tenham efeito.\n");
        } catch (IOException e) {
            logger.warning("Erro ao tentar mover o arquivo atualizado do programa.%s%n".formatted(e.getMessage()));
        }

        limparArquivosTemporarios(pastaTemporaria);
    }

    private void limparArquivosTemporarios(File pastaTemporaria) {
        File[] arquivos = pastaTemporaria.listFiles();

        if (arquivos == null) {
            if (pastaTemporaria.delete()) {
                logger.info("Pasta %s apagada com sucesso.%n".formatted(pastaTemporaria.getName()));
            }
            return;
        }

        for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                limparArquivosTemporarios(arquivo);
                continue;
            }

            if (arquivo.delete()) {
                logger.info("Arquivo %s apagado com sucesso%n".formatted(arquivo.getName()));
            }
        }

        if (pastaTemporaria.delete()) {
            logger.info("Pasta %s apagada com sucesso.%n".formatted(pastaTemporaria.getName()));
        }
    }

    private void baixarArquivo(File pastaTemporaria, URL urlArquivo, String nomeArquivo) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(pastaTemporaria.getPath() + "/" + nomeArquivo)) {
            ReadableByteChannel byteChannel = Channels.newChannel(urlArquivo.openStream());
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            logger.warning("Ocorreu um erro ao tentar baixar o arquivo %s. %s%n"
                    .formatted(urlArquivo.getPath(), e.getMessage()));
        }
    }
}
