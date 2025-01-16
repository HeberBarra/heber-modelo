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
package io.github.heberbarra.modelador.banco;

/**
 * Define os usuários permitidos no banco de dados, e a variável de ambiente que contém a senha do mesmo.
 *
 * @since v0.0.6-SNAPSHOT
 */
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
