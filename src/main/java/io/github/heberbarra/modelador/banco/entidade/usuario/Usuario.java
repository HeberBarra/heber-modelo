package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.entidade.Entidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbUsuario")
public class Usuario extends Entidade {

    @Id
    @Column(name = "matricula_usuario")
    private long matricula;

    @Column(name = "email_usuario", unique = true, nullable = false)
    private String email;

    @Column(name = "nome_usuario", unique = true, nullable = false)
    private String nome;

    @Column(nullable = false)
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
