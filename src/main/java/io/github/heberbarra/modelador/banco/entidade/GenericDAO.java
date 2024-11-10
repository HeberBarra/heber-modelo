package io.github.heberbarra.modelador.banco.entidade;

import io.github.heberbarra.modelador.banco.ConectorBanco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

/**
 * Classe base de todos os DAOs do programa.
 * @since v0.0.6-SNAPSHOT
 * @see Entidade
 * */
public abstract class GenericDAO<E extends Entidade> {

    protected Class<E> classeEntidade;
    protected EntityManager entityManager;

    public GenericDAO(Class<E> classeEntidade) {
        this.classeEntidade = classeEntidade;
        this.entityManager = ConectorBanco.getInstance().criarEntityManager();
    }

    public void criar(E entidade) {
        entityManager.getTransaction().begin();
        entityManager.persist(entidade);
        entityManager.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<E> list() {
        String hqlList = "from %s".formatted(classeEntidade.getSimpleName());
        Query hqlQuery = entityManager.createQuery(hqlList);
        return (List<E>) hqlQuery.getResultList();
    }

    public E findByID(int id) {
        return entityManager.find(classeEntidade, id);
    }

    public void update(E entidade) {
        entityManager.getTransaction().begin();
        entityManager.merge(entidade);
        entityManager.getTransaction().commit();
    }

    public void delete(E entidade) {
        entityManager.getTransaction().begin();
        entityManager.remove(entidade);
        entityManager.getTransaction().commit();
    }

    public void fecharConexao() {
        entityManager.close();
    }
}
