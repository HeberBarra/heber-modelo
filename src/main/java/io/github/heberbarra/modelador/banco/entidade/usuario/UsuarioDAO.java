package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.entidade.GenericDAO;
import jakarta.persistence.Query;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario findByID(long matricula) {
        return entityManager.find(Usuario.class, matricula);
    }

    public Usuario findByName(String nome) {
        String hqlSearch = "from Usuario where nome = :nome";
        Query querySearch = entityManager.createQuery(hqlSearch);
        querySearch.setParameter("nome", nome);

        return (Usuario) querySearch.getResultList().getFirst();
    }
}
