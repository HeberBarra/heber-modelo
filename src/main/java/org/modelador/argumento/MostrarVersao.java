package org.modelador.argumento;

import java.util.List;
import org.modelador.codigosaida.CodigoSaida;

public class MostrarVersao extends Argumento {

    public MostrarVersao() {
        super();
        this.descricao = "Mostra a versão do programa";
        this.flagsPermitidas = List.of("-v", "--version", "--versao");
    }

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
