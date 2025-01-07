package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.entidade.GenericDAO;
import jakarta.persistence.Query;

public class UsuarioDAO extends GenericDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByID(int matricula) {
        return super.findByID(matricula);
    }

    public Usuario findByName(String nome) {
        String hqlSearch = "from %s u where u.name = :nome".formatted(Usuario.class.getSimpleName());
        Query querySearch = entityManager.createQuery(hqlSearch);
        querySearch.setParameter(":nome", nome);

        return (Usuario) querySearch.getResultList().getFirst();
    }
}
