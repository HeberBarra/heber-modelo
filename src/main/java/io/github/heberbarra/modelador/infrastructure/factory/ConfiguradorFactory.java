package io.github.heberbarra.modelador.infrastructure.factory;

import static io.github.heberbarra.modelador.infrastructure.configurador.Configurador.ARQUIVO_CONFIGURACOES;
import static io.github.heberbarra.modelador.infrastructure.configurador.Configurador.ARQUIVO_PALETA;

import io.github.heberbarra.modelador.domain.configurador.ICombinadorConfiguracoes;
import io.github.heberbarra.modelador.domain.configurador.IConfigurador;
import io.github.heberbarra.modelador.domain.configurador.IPastaConfiguracao;
import io.github.heberbarra.modelador.infrastructure.configurador.CombinadorConfiguracoes;
import io.github.heberbarra.modelador.infrastructure.configurador.Configurador;
import io.github.heberbarra.modelador.infrastructure.configurador.CriadorConfiguracoes;
import io.github.heberbarra.modelador.infrastructure.configurador.LeitorConfiguracao;
import io.github.heberbarra.modelador.infrastructure.configurador.PastaConfiguracaoPrograma;
import io.github.heberbarra.modelador.infrastructure.configurador.VerificadorConfiguracaoPrograma;
import io.github.heberbarra.modelador.infrastructure.conversor.ConversorTomlPrograma;
import io.github.heberbarra.modelador.infrastructure.conversor.IConversorTOMLString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ConfiguradorFactory {

    private static IConfigurador configurador;

    @Bean
    public static IConfigurador build() {
        if (configurador == null) {
            ICombinadorConfiguracoes combinadorConfiguracoes = new CombinadorConfiguracoes();
            IConversorTOMLString conversorTOMLString = new ConversorTomlPrograma();
            IPastaConfiguracao pastaConfiguracao = new PastaConfiguracaoPrograma();
            CriadorConfiguracoes criadorConfiguracoes = new CriadorConfiguracoes();
            LeitorConfiguracao leitorConfiguracao = new LeitorConfiguracao(
                    pastaConfiguracao.decidirPastaConfiguracao(), ARQUIVO_CONFIGURACOES, ARQUIVO_PALETA);
            VerificadorConfiguracaoPrograma verificadorConfiguracaoPrograma = new VerificadorConfiguracaoPrograma();

            configurador = new Configurador(
                    combinadorConfiguracoes,
                    conversorTOMLString,
                    pastaConfiguracao,
                    criadorConfiguracoes,
                    leitorConfiguracao,
                    verificadorConfiguracaoPrograma);
        }

        return configurador;
    }
}
