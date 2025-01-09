package io.github.heberbarra.modelador.banco.entidade.atividade;

import io.github.heberbarra.modelador.banco.entidade.Entidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbAtividade")
public class Atividade extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_atividade")
    private int codigo;

    @Column(name = "nome_atividade", nullable = false)
    private String nome;

    private long matricula_usuario;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getMatricula_usuario() {
        return matricula_usuario;
    }

    public void setMatricula_usuario(long matricula_usuario) {
        this.matricula_usuario = matricula_usuario;
    }
}
