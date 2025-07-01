/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 * <p>
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 * <p>
 * https://choosealicense.com/licenses/mit/
 * <p>
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
package io.github.heberbarra.modelador.infrastructure.security;

import io.github.heberbarra.modelador.application.logging.JavaLogger;
import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.web.PathPatternRequestMatcherBuilderFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguradorSecurity {

    private final Logger logger = JavaLogger.obterLogger(ConfiguradorSecurity.class.getName());

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.formLogin(
                form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/index", true));

        httpSecurity.logout(logout -> {
            try {
                logout.logoutRequestMatcher(requestMatcherBuilder().getObject().matcher("/logout"));
            } catch (Exception e) {
                logger.severe("Falha ao tentar criar configurador de logout. Erro: %s".formatted(e.getMessage()));
            }
        });

        return httpSecurity.build();
    }

    @Bean
    public PathPatternRequestMatcherBuilderFactoryBean requestMatcherBuilder() {
        return new PathPatternRequestMatcherBuilderFactoryBean();
    }
}
