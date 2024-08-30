package org.modelador.configurador.verificador;

import java.io.File;

public class LeitorArquivoVerificacao<T extends JsonVerificador> {

    public File arquivoVerificador;
    private T informacoesJson;

    public LeitorArquivoVerificacao(File arquivoVerificador) {
        this.arquivoVerificador = arquivoVerificador;
    }

    public void lerArquivo() {}

    public File getArquivoVerificador() {
        return arquivoVerificador;
    }

    protected void setArquivoVerificador(File arquivoVerificador) {
        this.arquivoVerificador = arquivoVerificador;
    }
}
