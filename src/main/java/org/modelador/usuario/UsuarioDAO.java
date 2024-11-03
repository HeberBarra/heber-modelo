package org.modelador.usuario;

import org.modelador.banco.GenericDAO;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByID(int matricula) {
        return super.findByID(matricula);
    }
}
