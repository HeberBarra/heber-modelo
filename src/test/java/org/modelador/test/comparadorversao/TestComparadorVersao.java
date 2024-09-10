package org.modelador.test.comparadorversao;

import org.junit.jupiter.api.Test;
import org.modelador.atualizador.ComparadorVersao;

public class TestComparadorVersao {

    @Test
    protected void testVersaoIguais() {
        ComparadorVersao comparadorVersao = new ComparadorVersao("v0.0.1", "v0.0.1");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.IGUAL;

        comparadorVersao.setVersaoPrograma("v0.0.1-SNAPSHOT");
        comparadorVersao.setVersaoRemota("v0.0.1-SNAPSHOT");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.IGUAL;
    }

    @Test
    protected void testVersaoRemotaMaior() {
        ComparadorVersao comparadorVersao = new ComparadorVersao("v0.0.1", "v0.0.2");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;

        comparadorVersao.setVersaoRemota("v0.0.2-SNAPSHOT");
        comparadorVersao.setVersaoPrograma("v0.0.1-SNAPSHOT");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;

        comparadorVersao.setVersaoRemota("v0.0.1-BETA");
        comparadorVersao.setVersaoPrograma("v0.0.2-SNAPSHOT");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;

        comparadorVersao.setVersaoRemota("v0.0.1");
        comparadorVersao.setVersaoPrograma("v0.0.2-SNAPSHOT");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;

        comparadorVersao.setVersaoRemota("v0.0.1");
        comparadorVersao.setVersaoPrograma("v0.1.0-BETA");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MAIOR;
    }

    @Test
    protected void testVersaoRemotaMenor() {
        ComparadorVersao comparadorVersao = new ComparadorVersao("v0.0.2", "v0.0.1");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MENOR;

        comparadorVersao.setVersaoPrograma("v0.0.2-SNAPSHOT");
        comparadorVersao.setVersaoRemota("v0.0.1-SNAPSHOT");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MENOR;

        comparadorVersao.setVersaoRemota("v0.1.0-SNAPSHOT");
        comparadorVersao.setVersaoPrograma("v0.0.1-BETA");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MENOR;

        comparadorVersao.setVersaoRemota("v0.0.1-BETA");
        comparadorVersao.setVersaoPrograma("v0.0.2-PRE_RELEASE");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MENOR;

        comparadorVersao.setVersaoRemota("v0.0.2-ALPHA");
        comparadorVersao.setVersaoPrograma("v0.1.0-ALPHA");
        assert comparadorVersao.compararVersoes() == ComparadorVersao.MENOR;
    }
}
