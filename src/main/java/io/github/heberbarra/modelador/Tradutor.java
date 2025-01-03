package io.github.heberbarra.modelador;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Tradutor {

    private static Tradutor tradutorInstance;
    private final MessageSource messageSource;

    public Tradutor(@Autowired MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String traduzirMensagem(String codigoMensagem) {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(codigoMensagem, null, locale);
    }

    public static Tradutor getTradutorInstance() {
        if (tradutorInstance == null) {
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Principal.class);
            tradutorInstance = applicationContext.getBean(Tradutor.class);
        }

        return tradutorInstance;
    }
}
