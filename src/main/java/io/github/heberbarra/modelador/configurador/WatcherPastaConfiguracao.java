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

public class WatcherPastaConfiguracao extends Thread {

    private static final Logger logger = JavaLogger.obterLogger(WatcherPastaConfiguracao.class.getName());
    private static final int DELAY_RELOAD = 500;
    private final Configurador configurador;
    private final Path pastaConfiguracao;

    public WatcherPastaConfiguracao() {
        configurador = ConfiguradorPrograma.getInstance();
        pastaConfiguracao = Path.of(new PastaConfiguracaoPrograma().decidirPastaConfiguracao());
    }

    public void recarregarConfiguracao() {
        configurador.lerConfiguracao();
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
                logger.info("Configurações recarregadas");
            }

            isKeyValid = watchKey.reset();
        } while (isKeyValid);
    }
}
