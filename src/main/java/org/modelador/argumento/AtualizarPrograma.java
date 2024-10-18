package org.modelador.argumento;

import java.util.List;
import org.modelador.atualizador.Atualizador;
import org.modelador.codigosaida.CodigoSaida;

public class AtualizarPrograma extends Argumento {

    public AtualizarPrograma() {
        this.descricao = "Baixa a última versão do programa, se a mesma não for a versão instalada.";
        this.flagsPermitidas = List.of("--atualizar", "--update");
    }

    @Override
    public void executar() {
        Atualizador atualizador = new Atualizador();
        atualizador.baixarAtualizacao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
