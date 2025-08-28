/*
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

package io.github.heberbarra.modelador.domain.model;

import java.util.List;

public class NovoDiagramaDTO {
    private String nome;
    private List<String> autor;
    private List<String> email;
    private List<String> tiposDiagrama;

    public NovoDiagramaDTO() {}

    public NovoDiagramaDTO(String nome, List<String> autor, List<String> email, List<String> tiposDiagrama) {
        this.nome = nome;
        this.autor = autor;
        this.email = email;
        this.tiposDiagrama = tiposDiagrama;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getAutor() {
        return autor;
    }

    public void setAutor(List<String> autor) {
        this.autor = autor;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getTiposDiagrama() {
        return tiposDiagrama;
    }

    public void setTiposDiagrama(List<String> tiposDiagrama) {
        this.tiposDiagrama = tiposDiagrama;
    }
}
