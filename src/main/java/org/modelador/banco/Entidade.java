package org.modelador.banco;

import jakarta.persistence.Id;

/**
 * Define a base das classes que representam uma tabela do banco de dados.
 * <p>
 * Utiliza um ID numérico, com incremento automático, como chave primária.
 * @since v0.0.6-SNAPSHOT
 * @see GenericDAO
 * */
public abstract class Entidade {

    @Id
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
