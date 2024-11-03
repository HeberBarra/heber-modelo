package org.modelador.atividade;

import org.modelador.banco.GenericDAO;

public class AtividadeDAO extends GenericDAO<Atividade> {

    public AtividadeDAO() {
        super(Atividade.class);
    }
}
