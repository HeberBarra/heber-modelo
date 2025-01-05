package io.github.heberbarra.modelador.argumento.executador;

import io.github.heberbarra.modelador.argumento.Argumento;
import io.github.heberbarra.modelador.argumento.coletor.ColetorClassesArgumentos;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import io.github.heberbarra.modelador.tradutor.Tradutor;
import io.github.heberbarra.modelador.tradutor.TradutorFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Executa funções conforme os argumentos passados pela linha de comando.
 * <p>
 * Cada flag é executada em sequência e pode ser executada apenas uma vez, a menos que encerre o programa.
 * Para evitar repetições as flags que já foram executas são salvas num cache.
 * @since v0.0.4-SNAPSHOT
 * */
public class ExecutadorArgumentos {

    private static final Logger logger = JavaLogger.obterLogger(ExecutadorArgumentos.class.getName());
    private final ColetorClassesArgumentos coletorClassesArgumentos;
    private final String[] args;
    private final Set<String> cacheArgumentos;
    private final Tradutor tradutor;

    public ExecutadorArgumentos(String[] args) {
        coletorClassesArgumentos = ColetorClassesArgumentos.getInstance();
        cacheArgumentos = new HashSet<>();
        this.args = args;
        TradutorFactory tradutorFactory = new TradutorFactory();
        tradutor = tradutorFactory.criarObjeto();
    }

    /**
     * Executa as flags passadas de forma sequencial
     * */
    public void executarFlags() {
        coletorClassesArgumentos.coletarArgumentos();

        for (String argumento : args) {
            executarFlagArgumento(argumento);
        }
    }

    /**
     * Tenta executar a lógica associada a flag passada, caso ocorra algum erro é gerado um log informando o erro,
     * caso o erro seja grave o programa será encerrado com um dos seguintes códigos de saída:
     * {@link CodigoSaida#ERRO_PEGAR_CONSTRUTOR}, {@link CodigoSaida#ERRO_CRIACAO_OBJETO}, {@link CodigoSaida#ACESSO_NEGADO}
     * <p>
     * Caso uma flag seja passada duas ou mais vezes, a lógica associada será executada apenas uma vez
     * @param flagArgumento a flag a ser executada
     * */
    private void executarFlagArgumento(String flagArgumento) {
        if (cacheArgumentos.contains(flagArgumento)) return;

        cacheArgumentos.add(flagArgumento);
        Set<Class<Argumento>> argumentos = coletorClassesArgumentos.getArgumentos();
        for (Class<Argumento> argumentoClass : argumentos) {
            Argumento argumento;
            try {
                Constructor<Argumento> argumentoConstructor = argumentoClass.getConstructor();
                argumento = argumentoConstructor.newInstance();
            } catch (NoSuchMethodException e) {
                logger.severe(tradutor.traduzirMensagem("error.flag.get.constructor")
                        .formatted(argumentoClass.getSimpleName(), e.getMessage()));
                logger.severe(tradutor.traduzirMensagem("app.end"));
                System.exit(CodigoSaida.ERRO_PEGAR_CONSTRUTOR.getCodigo());
                return;
            } catch (InvocationTargetException e) {
                logger.severe(tradutor.traduzirMensagem("error.flag.create.object")
                        .formatted(argumentoClass.getSimpleName(), e.getMessage()));
                logger.severe(tradutor.traduzirMensagem("app.end"));
                System.exit(CodigoSaida.ERRO_CRIACAO_OBJETO.getCodigo());
                return;
            } catch (InstantiationException e) {
                logger.severe(tradutor.traduzirMensagem("error.flag.create.object")
                        .formatted(argumentoClass.getSimpleName()));
                logger.severe(tradutor.traduzirMensagem("app.end"));
                System.exit(CodigoSaida.ERRO_CRIACAO_OBJETO.getCodigo());
                return;
            } catch (IllegalAccessException e) {
                logger.severe(tradutor.traduzirMensagem("error.flag.access.denied.constructor")
                        .formatted(argumentoClass.getSimpleName(), e.getMessage()));
                logger.severe(tradutor.traduzirMensagem("app.end"));
                System.exit(CodigoSaida.ACESSO_NEGADO.getCodigo());
                return;
            }

            if (argumento.contemFlag(flagArgumento)) {
                argumento.run();
                return;
            }
        }

        logger.warning(tradutor.traduzirMensagem("error.flag.unknown").formatted(flagArgumento));
    }
}
