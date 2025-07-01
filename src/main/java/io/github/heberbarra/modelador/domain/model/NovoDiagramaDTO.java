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
package io.github.heberbarra.modelador.domain.model;

import java.util.List;

public class NovoDiagramaDTO {
    private String nome;
    private String autor;
    private String email;
    private List<String> tiposDiagrama;

    public NovoDiagramaDTO() {}

    public NovoDiagramaDTO(String nome, String autor, String email, List<String> tiposDiagrama) {
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTiposDiagrama() {
        return tiposDiagrama;
    }

    public void setTiposDiagrama(List<String> tiposDiagrama) {
        this.tiposDiagrama = tiposDiagrama;
    }
}
