package org.modelador.argumento;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.modelador.logger.JavaLogger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

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
