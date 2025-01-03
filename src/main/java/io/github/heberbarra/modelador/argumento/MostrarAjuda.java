package io.github.heberbarra.modelador.argumento;

import io.github.heberbarra.modelador.Principal;
import io.github.heberbarra.modelador.argumento.coletor.ColetorClassesArgumentos;
import io.github.heberbarra.modelador.codigosaida.CodigoSaida;
import io.github.heberbarra.modelador.logger.JavaLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.fusesource.jansi.Ansi;

/**
 * Mostra as flags disponíveis no programa junto de suas respectivas descrições.
 * @since v0.0.9-SNAPSHOT
 * */
public class MostrarAjuda extends Argumento {

    private static final Logger logger = JavaLogger.obterLogger(MostrarAjuda.class.getName());
    private final ColetorClassesArgumentos coletorClassesArgumentos;

    public MostrarAjuda() {
        coletorClassesArgumentos = ColetorClassesArgumentos.getInstance();
        this.descricao = "Mostra os comandos/flags disponíveis e as suas descrições.";
        this.flagsPermitidas = List.of("--ajuda", "--help", "-h");
    }

    /**
     * Exibe no {@code stdout} todas as flags do programa, junto dos valores permitidos e suas descrições.
     * Após isso encerra o programa
     * */
    @Override
    public void run() {
        Set<Class<Argumento>> argumentos = coletorClassesArgumentos.getArgumentos();

        for (Class<Argumento> classeArgumento : argumentos) {
            try {
                Constructor<Argumento> construtorArgumento = classeArgumento.getConstructor();
                Argumento argumento = construtorArgumento.newInstance();
                imprimirInfo(argumento);
            } catch (NoSuchMethodException e) {
                logger.warning(Principal.tradutor
                        .traduzirMensagem("error.flag.get.constructor")
                        .formatted(classeArgumento.getName(), e.getMessage()));
                System.exit(CodigoSaida.ERRO_PEGAR_CONSTRUTOR.getCodigo());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                logger.warning(Principal.tradutor
                        .traduzirMensagem("error.flag.create.object")
                        .formatted(classeArgumento.getName(), e.getMessage()));
                System.exit(CodigoSaida.ERRO_CRIACAO_OBJETO.getCodigo());
            }
        }

        System.exit(CodigoSaida.OK.getCodigo());
    }

    private void imprimirInfo(Argumento argumento) {
        String nomeArgumento = argumento.getClass().getSimpleName();
        System.out.println(
                Ansi.ansi().a("[").fg(Ansi.Color.CYAN).a(nomeArgumento).reset().a("]"));
        System.out.println(String.join(" ", argumento.getFlagsPermitidas()));
        System.out.println(argumento.getDescricao());
        System.out.println();
    }
}
