/**
 * Copyright (C) 2025 Heber Ferreira Barra, João Gabriel de Cristo, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.test.argumento;

import io.github.heberbarra.modelador.argumento.Argumento;
import io.github.heberbarra.modelador.argumento.coletor.ColetorClassesArgumentos;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;

public class TestColetorArgumento {

    private static final Logger logger = JavaLogger.obterLogger(TestColetorArgumento.class.getName());

    protected @Nullable ColetorClassesArgumentos criarColetorClassesArgumentos() {
        try {
            Constructor<ColetorClassesArgumentos> coletorClassesArgumentosConstructor =
                    ColetorClassesArgumentos.class.getDeclaredConstructor();
            coletorClassesArgumentosConstructor.setAccessible(true);

            return coletorClassesArgumentosConstructor.newInstance();
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException e) {
            logger.warning(e.getMessage());
        }

        return null;
    }

    @Test
    protected void testColetarClasses() {
        ColetorClassesArgumentos coletorClassesArgumentos = criarColetorClassesArgumentos();
        assert coletorClassesArgumentos != null;

        coletorClassesArgumentos.coletarArgumentos();
        assert !coletorClassesArgumentos.getArgumentos().isEmpty();
    }

    @Test
    protected void testClassesColetadasArgumentos() {
        ColetorClassesArgumentos coletorClassesArgumentos = criarColetorClassesArgumentos();
        assert coletorClassesArgumentos != null;

        coletorClassesArgumentos.coletarArgumentos();

        for (Class<?> classeColetada : coletorClassesArgumentos.getArgumentos()) {
            assert classeColetada.getSuperclass() == Argumento.class;
        }
    }
}
