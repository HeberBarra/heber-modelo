package io.github.heberbarra.modelador.tradutor;

import io.github.heberbarra.modelador.Principal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TradutorFactory {

    private final ApplicationContext applicationContext;

    public TradutorFactory() {
        applicationContext = new AnnotationConfigApplicationContext(Principal.class);
    }

    public Tradutor criarObjeto() {
        return applicationContext.getBean(Tradutor.class);
    }
}
