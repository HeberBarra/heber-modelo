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
package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.entidade.Entidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbUsuario")
public class Usuario extends Entidade {

    public Usuario() {}

    public Usuario(long matricula, String email, String nome, String senha, String tipo) {
        this.matricula = matricula;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    @Id
    @Column(name = "matricula_usuario")
    private long matricula;

    @Column(name = "email_usuario", unique = true, nullable = false)
    private String email;

    @Column(name = "nome_usuario", unique = true, nullable = false)
    private String nome;

    @Column(name = "senha_usuario", nullable = false)
    private String senha;

    @Column(name = "tipo_usuario")
    private String tipo;

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
