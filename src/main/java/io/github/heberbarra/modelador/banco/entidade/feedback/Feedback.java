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
package io.github.heberbarra.modelador.banco.entidade.feedback;

import io.github.heberbarra.modelador.banco.entidade.Entidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbFeedback")
public class Feedback extends Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_feedback")
    private int codigo;

    @Column(name = "descricao_feedback", nullable = false)
    private String descricao;

    private int codigo_atividade;

    private long matricula_professor;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigo_atividade() {
        return codigo_atividade;
    }

    public void setCodigo_atividade(int codigo_atividade) {
        this.codigo_atividade = codigo_atividade;
    }

    public long getMatricula_professor() {
        return matricula_professor;
    }

    public void setMatricula_professor(long matricula_professor) {
        this.matricula_professor = matricula_professor;
    }
}
