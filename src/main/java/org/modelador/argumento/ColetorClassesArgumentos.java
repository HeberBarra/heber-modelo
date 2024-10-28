package org.modelador.argumento;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Coleta todas as classes que herdam de {@link Argumento}.
 * <p>
 * Utiliza o anti pattern <b>Singleton</b> para garantir que a classe não consumirá mais memória do que o necessário
 * <p>
 * NOTE: Futuramente adicionado suporte para coleção de classes que herdam de {@link Argumento} criadas por usuários, para prover melhor suporte a plugins
 * @since v0.0.4-SNAPSHOT
 * */
public class ColetorClassesArgumentos {

    private static final Logger logger = JavaLogger.obterLogger(ColetorClassesArgumentos.class.getName());
    private static ColetorClassesArgumentos coletorClassesArgumentos;
    private final Set<Class<Argumento>> argumentos;

    private ColetorClassesArgumentos() {
        argumentos = new HashSet<>();
    }

    public static synchronized ColetorClassesArgumentos getInstance() {
        if (coletorClassesArgumentos == null) {
            coletorClassesArgumentos = new ColetorClassesArgumentos();
        }

        return coletorClassesArgumentos;
    }

    /**
     * Escaneia o pacote argumento e coleta todas as classes que herdam de {@link Argumento} e salva elas num {@code Set} para evitar duplicatas
     * */
    @SuppressWarnings("unchecked")
    public void coletarArgumentos() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Argumento.class));
        Set<BeanDefinition> beansArgumentos = scanner.findCandidateComponents("org.modelador.argumento");

        for (BeanDefinition beanDefinition : beansArgumentos) {
            try {
                argumentos.add((Class<Argumento>) Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                logger.warning("A classe %s não foi encontrada".formatted(beanDefinition.getBeanClassName()));
            }
        }
    }

    public Set<Class<Argumento>> getArgumentos() {
        return argumentos;
    }
}
