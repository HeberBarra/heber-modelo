/**
 * Copyright (C) 2025 Heber Ferreira Barra, João Gabriel de Cristo, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.test.recurso;

import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.recurso.AcessadorRecursos;
import io.github.heberbarra.modelador.recurso.Recurso;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

public class TestRecurso {

    private static final Logger logger = JavaLogger.obterLogger(TestRecurso.class.getName());

    @Test
    protected void testPegarInputStreamRecurso() {
        Recurso recurso = new AcessadorRecursos();

        try (InputStream streamRecurso = recurso.pegarRecurso("recursos/TestRecurso.txt")) {
            assert streamRecurso != null;
        } catch (IOException e) {
            logger.warning("Falha ao tentar acessar recurso de teste. Erro: %s".formatted(e.getMessage()));
        }
    }

    @Test
    protected void testPegarFileRecurso() {
        Recurso recurso = new AcessadorRecursos();
        File arquivoRecurso = recurso.pegarArquivoRecurso("recursos/TestRecurso.txt");

        assert arquivoRecurso != null;
    }

    @Test
    protected void testPathRecurso() {
        Recurso recurso = new AcessadorRecursos();
        Path pathRecurso = recurso.pegarCaminhoRecurso("recursos/TestRecurso.txt");

        assert pathRecurso != null;
    }

    @Test
    protected void testLerRecurso() {
        Recurso recurso = new AcessadorRecursos();
        String textoArquivoEsperado = "Hello, World!";

        try (InputStream recursoStream = recurso.pegarRecurso("recursos/TestRecurso.txt")) {
            byte[] bytesArquivo = recursoStream.readAllBytes();
            String textoArquivo = new String(bytesArquivo, StandardCharsets.UTF_8);

            assert textoArquivoEsperado.equals(textoArquivo);
        } catch (IOException e) {
            logger.warning("Falha ao tentar ler recurso de teste. Erro: %s".formatted(e.getMessage()));
        }
    }
}
