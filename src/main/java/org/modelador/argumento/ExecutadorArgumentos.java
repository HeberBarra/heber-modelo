package org.modelador.argumento;

import org.modelador.codigosaida.CodigoSaida;
import org.modelador.logger.JavaLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

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

    public void executarFlags() {
        coletorClassesArgumentos.coletarArgumentos();

        for (String argumento: args) {
            executarFlagArgumento(argumento);
        }
    }

    private void executarFlagArgumento(String flagArgumento) {
        if (cacheArgumentos.contains(flagArgumento))
            return;

        cacheArgumentos.add(flagArgumento);
        Set<Class<Argumento>> argumentos = coletorClassesArgumentos.getArgumentos();
        for (Class<Argumento> argumentoClass: argumentos) {
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
