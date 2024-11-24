package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.atualizador.AtualizadorPrograma;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import java.util.List;

/**
 * Baixa a última atualização do programa, se houver.
 * @since v0.0.4-SNAPSHOT
 * */
public class AtualizarPrograma extends Argumento {

    public AtualizarPrograma() {
        this.descricao = "Baixa a última versão do programa, se a mesma não for a versão instalada.";
        this.flagsPermitidas = List.of("--atualizar", "--update");
    }

    /**
     * Atualiza o programa para a versão mais recente e encerra o programa,
     * para garantir que o usuário utilize a atualizada
     * */
    @Override
    public void run() {
        AtualizadorPrograma atualizador = new AtualizadorPrograma();
        atualizador.baixarAtualizacao();
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
