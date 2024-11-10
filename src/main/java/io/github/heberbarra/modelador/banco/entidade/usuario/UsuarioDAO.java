package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.entidade.GenericDAO;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByID(int matricula) {
        return super.findByID(matricula);
    }
}
