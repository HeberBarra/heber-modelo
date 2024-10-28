package org.modelador.argumento;

import java.util.List;
import org.modelador.codigosaida.CodigoSaida;

/**
 * Mostra a versão do programa
 * @since v0.0.4-SNAPSHOT
 * */
public class MostrarVersao extends Argumento {

    public MostrarVersao() {
        super();
        this.descricao = "Mostra a versão do programa";
        this.flagsPermitidas = List.of("-v", "--version", "--versao");
    }

    /**
     * Mostra a versão atual do programa no {@code stdin}, caso não seja possível pegar a versão do programa,
     * encerra o programa com o código apropriado.
     * <p>
     * Encerra o programa após mostrar a versão
     * @see CodigoSaida
     * */
    @Override
    public void executar() {
        String versao = MostrarVersao.class.getPackage().getImplementationVersion();

        if (versao == null) {
            logger.info("Não foi possível pegar a versão atual do programa.");
            System.exit(CodigoSaida.ERRO_PEGAR_VERSAO.getCodigo());
        }

        System.out.println(versao);
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
