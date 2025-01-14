package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.tradutor.TradutorWrapper;
import java.util.List;
import java.util.logging.Logger;

/**
 * Mostra a versão do programa
 *
 * @since v0.0.4-SNAPSHOT
 */
public class MostrarVersao extends Argumento {

    private static final Logger logger = JavaLogger.obterLogger(MostrarVersao.class.getName());

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
     *
     * @see CodigoSaida
     */
    @Override
    public void run() {
        String versao = Principal.class.getPackage().getImplementationVersion();

        if (versao == null) {
            logger.info(TradutorWrapper.tradutor.traduzirMensagem("error.flag.get.version"));
            System.exit(CodigoSaida.ERRO_PEGAR_VERSAO.getCodigo());
        }

        System.out.println(versao);
        System.exit(CodigoSaida.OK.getCodigo());
    }
}
