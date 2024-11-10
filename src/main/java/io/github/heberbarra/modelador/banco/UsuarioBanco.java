package io.github.heberbarra.modelador.banco;

/**
 * Define os usuários permitidos no banco de dados, e a variável de ambiente que contém a senha do mesmo.
 * @since v0.0.6-SNAPSHOT
 * */
public enum UsuarioBanco {
    ESTUDANTE("estudante", "SENHA_ESTUDANTE"),
    PROFESSOR("professor", "SENHA_PROFESSOR");

    UsuarioBanco(String nomeUsuario, String nomeVariavelSenha) {
        this.nomeUsuario = nomeUsuario;
        this.nomeVariavelSenha = nomeVariavelSenha;
    }

    private final String nomeUsuario;
    private final String nomeVariavelSenha;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNomeVariavelSenha() {
        return nomeVariavelSenha;
    }
}
