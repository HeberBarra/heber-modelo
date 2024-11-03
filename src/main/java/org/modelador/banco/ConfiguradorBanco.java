package org.modelador.banco;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;

/**
 * Gera uma configuração complementar ao persistence.xml, utilizando as variáveis de ambiente para passar segredos de forma segura.
 * @since v0.0.6-SNAPSHOT
 * @see UsuarioBanco
 * */
public class ConfiguradorBanco {

    private final Map<String, String> configuracoes;
    private UsuarioBanco usuarioBanco;
    private final Dotenv dotenv;

    public ConfiguradorBanco() {
        super();
        configuracoes = new HashMap<>();
        usuarioBanco = UsuarioBanco.ESTUDANTE;
        dotenv = Dotenv.load();
    }

    public ConfiguradorBanco(UsuarioBanco usuarioBanco) {
        this();
        this.usuarioBanco = usuarioBanco;
    }

    /**
     * Gera a configuração complementar e salva na propriedade {@code configuracoes}
     * */
    public void gerarConfiguracoes() {
        configuracoes.put("jakarta.persistence.jdbc.user", usuarioBanco.getNomeUsuario());
        configuracoes.put("jakarta.persistence.jdbc.password", dotenv.get(usuarioBanco.getNomeVariavelSenha()));
        configuracoes.put("jakarta.persistence.jdbc.url", criarUrlBanco());
    }

    /**
     * Cria a URL apropriada para a conexão com o banco de dados
     * @return a url de conexão como {@link String}
     * */
    private String criarUrlBanco() {
        String host = dotenv.get("MYSQL_HOST");
        String port = dotenv.get("MYSQL_PORT");
        String nomeBanco = "db_HeberModelo";

        return "jdbc:mysql://%s:%s/%s".formatted(host, port, nomeBanco);
    }

    public Map<String, String> getConfiguracoes() {
        return configuracoes;
    }

    public UsuarioBanco getUsuarioBanco() {
        return usuarioBanco;
    }

    public void setUsuarioBanco(UsuarioBanco usuarioBanco) {
        this.usuarioBanco = usuarioBanco;
    }
}
