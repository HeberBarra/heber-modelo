package io.github.heberbarra.modelador.configurador;

import io.github.heberbarra.modelador.logger.JavaLogger;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;
import org.tomlj.TomlTable;

public class WatcherPastaConfiguracao extends Thread {

    private static final Logger logger = JavaLogger.obterLogger(WatcherPastaConfiguracao.class.getName());
    private static final int DELAY_RELOAD = 500;
    private final ConfiguradorPrograma configurador;
    private final CriadorConfiguracoes criadorConfiguracoes;
    private final LeitorConfiguracao leitorConfiguracao;
    private final VerificadorConfiguracaoPrograma verificadorConfiguracaoPrograma;
    private final Path pastaConfiguracao;

    public WatcherPastaConfiguracao() {
        configurador = ConfiguradorPrograma.getInstance();
        criadorConfiguracoes = configurador.getCriadorConfiguracoes();
        leitorConfiguracao = configurador.getLeitorConfiguracao();
        verificadorConfiguracaoPrograma = new VerificadorConfiguracaoPrograma();
        pastaConfiguracao = Path.of(new PastaConfiguracaoPrograma().decidirPastaConfiguracao());
    }

    public void recarregarConfiguracao() {
        TomlTable configuracoes = leitorConfiguracao.lerArquivoConfiguracoesSemSalvar();
        TomlTable paleta = leitorConfiguracao.lerArquivoPaletaSemSalvar();

        verificadorConfiguracaoPrograma.verificarArquivoConfiguracao(
                criadorConfiguracoes.getConfiguracaoPadrao(), configuracoes);
        verificadorConfiguracaoPrograma.verificarArquivoPaleta(criadorConfiguracoes.getPaletaPadrao(), paleta);

        if (verificadorConfiguracaoPrograma.configuracoesContemErrosGraves()) {
            logger.warning("As configurações do programa não serão recarregadas, pois elas contém erros graves.");
        } else {
            configurador.lerConfiguracao();
            logger.info("Configurações recarregadas");
        }
    }

    @SuppressWarnings("CatchMayIgnoreException")
    @Override
    public void run() {
        WatchService watcher;
        WatchKey watchKey;
        try {
            watcher = FileSystems.getDefault().newWatchService();
            pastaConfiguracao.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            logger.warning(("Falha ao tentar criar o observador da pasta de configuração. "
                            + "Recarregamento automático das configurações ficará indisponível. Erro: %s")
                    .formatted(e.getMessage()));
            return;
        }

        boolean isKeyValid;
        do {
            try {
                watchKey = watcher.take();
            } catch (InterruptedException e) {
                return;
            }

            for (WatchEvent<?> event : watchKey.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind != StandardWatchEventKinds.ENTRY_MODIFY) {
                    return;
                }

                logger.info("Configurações mudaram");

                try {
                    Thread.sleep(DELAY_RELOAD);
                } catch (InterruptedException e) {
                }

                recarregarConfiguracao();
            }
            isKeyValid = watchKey.reset();
        } while (isKeyValid);
    }
}
