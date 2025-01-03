package io.github.heberbarra.modelador.impressor;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class ColetorCodigosSaida {

    private static final Logger logger = JavaLogger.obterLogger(ColetorCodigosSaida.class.getName());
    public static final String PACOTE_ENUMS_CODIGOS_SAIDA = "io.github.heberbarra.modelador.codigosaida";
    private final Set<DadosCodigoSaida> dadosCodigosSaida;

    public ColetorCodigosSaida() {
        dadosCodigosSaida = new HashSet<>();
    }

    public void coletarCodigosEnum() {
        List<Class<Enum<?>>> enums = coletarEnums();
        List<CodigoSaida> codigosSaida = new ArrayList<>();
        for (Class<Enum<?>> enumeration : enums) {
            for (Enum<?> enumConstant : enumeration.getEnumConstants()) {
                if (enumConstant instanceof CodigoSaida codigo) {
                    codigosSaida.add(codigo);
                }
            }
        }

        for (CodigoSaida codigoSaida : codigosSaida) {
            dadosCodigosSaida.add(new DadosCodigoSaida(codigoSaida.toString(), codigoSaida.getCodigo()));
        }
    }

    @SuppressWarnings("unchecked")
    private List<Class<Enum<?>>> coletarEnums() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Enum.class));
        Set<BeanDefinition> beansEnums = scanner.findCandidateComponents(PACOTE_ENUMS_CODIGOS_SAIDA);

        List<Class<Enum<?>>> enums = new ArrayList<>();
        for (BeanDefinition beanDefinition : beansEnums) {
            try {
                enums.add((Class<Enum<?>>) Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                logger.warning(Principal.tradutor
                        .traduzirMensagem("error.class.notfound")
                        .formatted(beanDefinition.getBeanClassName()));
            }
        }

        return enums;
    }

    public Set<DadosCodigoSaida> getDadosCodigosSaida() {
        return dadosCodigosSaida;
    }
}
