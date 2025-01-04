package io.github.heberbarra.modelador.test.token;

import io.github.heberbarra.modelador.token.GeradorToken;
import org.junit.jupiter.api.Test;

public class TestGeradorToken {

    @Test
    public void testGeradorTokenGetToken() {
        GeradorToken geradorToken = new GeradorToken();
        geradorToken.gerarToken();

        String primeiroToken = geradorToken.getToken();
        String segundoToken = geradorToken.getToken();

        assert primeiroToken.equals(segundoToken);
    }

    @Test
    public void testGeradorTokenGeraTokensDiferentes() {
        GeradorToken geradorToken = new GeradorToken();

        geradorToken.gerarToken();
        String primeiroToken = geradorToken.getToken();

        geradorToken.gerarToken();
        String segundoToken = geradorToken.getToken();

        assert !primeiroToken.equals(segundoToken);
    }
}
