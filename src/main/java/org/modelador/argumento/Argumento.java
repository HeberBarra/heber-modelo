package org.modelador.argumento;

import java.util.List;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;

/**
 * Define a lógica que será executado para determinadas opções/flags passadas pela linha de comando,
 * para evitar problemas as classes que herdarem desta devem definir as flags permitidas e
 * sua descrição já no seu construtor.
 * <p>
 * Também recomenda-se chamar o construtor dessa classe utilizando super() para evitar problemas
 * e graves diferenças entre as filhas
 * @since v0.0.4-SNAPSHOT
 * */
public abstract class Argumento {

    protected Logger logger;
    protected List<String> flagsPermitidas;
    protected String descricao;

    public Argumento() {
        this.logger = JavaLogger.obterLogger(this.getClass().getName());
    }

    /**
     * A lógica que deverá ser executado quando a flag for passada para o programa
     * */
    public abstract void executar();

    /**
     * Verifica a flag passado está na lista de flags permitidas
     * @param flag a flag procurada
     * @return {@code true} caso a flag tenha sido encontrada
     * */
    public boolean contemFlag(String flag) {
        return flagsPermitidas.contains(flag);
    }

    public List<String> getFlagsPermitidas() {
        return flagsPermitidas;
    }

    public void setFlagsPermitidas(List<String> flagsPermitidas) {
        this.flagsPermitidas = flagsPermitidas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
