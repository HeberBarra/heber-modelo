package org.modelador.argumento;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.modelador.codigosaida.CodigoSaida;
import org.modelador.logger.JavaLogger;

/**
 * Executa funções conforme os argumentos passados pela linha de comando.
 * <p>
 * Cada flag é executada em sequência e pode ser executada apenas uma vez, a menos que encerre o programa.
 * Para evitar repetições as flags que já foram executas são salvas num cache.
 * */
public class ExecutadorArgumentos {

    private static final Logger logger = JavaLogger.obterLogger(ExecutadorArgumentos.class.getName());
    private final ColetorClassesArgumentos coletorClassesArgumentos;
    private final String[] args;
    private final Set<String> cacheArgumentos;

    public ExecutadorArgumentos(String[] args) {
        coletorClassesArgumentos = ColetorClassesArgumentos.getInstance();
        cacheArgumentos = new HashSet<>();
        this.args = args;
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
     * caso o erro seja grave o programa será encerrado com o código de saída apropriado: {@link CodigoSaida}
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
                logger.severe("Não foi possível pegar o construtor da classe %s. Encerrando o programa."
                        .formatted(argumentoClass.getSimpleName()));
                System.exit(CodigoSaida.ERRO_PEGAR_CONSTRUTOR.getCodigo());
                return;
            } catch (InvocationTargetException e) {
                logger.severe("Ocorreu um erro ao criar o objeto da classe %s. Erro: %s. Encerrando o programa"
                        .formatted(argumentoClass.getSimpleName(), e.getMessage()));
                System.exit(CodigoSaida.ERRO_CRIACAO_OBJETO.getCodigo());
                return;
            } catch (InstantiationException e) {
                logger.severe("Não foi possível criar o objeto da classe %s. Encerrando o programa."
                        .formatted(argumentoClass.getSimpleName()));
                System.exit(CodigoSaida.ERRO_CRIACAO_OBJETO.getCodigo());
                return;
            } catch (IllegalAccessException e) {
                logger.severe("O acesso ao método construtor da classe %s foi negado. Encerrando o programa."
                        .formatted(argumentoClass.getSimpleName()));
                System.exit(CodigoSaida.ACESSO_NEGADO.getCodigo());
                return;
            }

            if (argumento.contemFlag(flagArgumento)) {
                argumento.executar();
                return;
            }
        }

        logger.warning("A flag %s não corresponde a nenhuma flag permitida pelo programa.".formatted(flagArgumento));
    }
}
