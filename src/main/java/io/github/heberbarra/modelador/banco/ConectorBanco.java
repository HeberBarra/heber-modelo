package io.github.heberbarra.modelador.banco;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utiliza o anti pattern Singleton para garantir uma conexão única ao banco de dados.
 *
 * @see ConectorBanco#criarEntityManager()
 * @since v0.0.6-SNAPSHOT
 */
public final class ConectorBanco {

    private static ConectorBanco conectorBanco;
    private final ConfiguradorBanco configuradorBanco;
    private EntityManagerFactory entityManagerFactory;

    private ConectorBanco() {
        configuradorBanco = new ConfiguradorBanco();
        configuradorBanco.gerarConfiguracoes();
        entityManagerFactory = Persistence.createEntityManagerFactory("jun", configuradorBanco.getConfiguracoes());
    }

    public static synchronized ConectorBanco getInstance() {
        if (conectorBanco == null) {
            conectorBanco = new ConectorBanco();
        }

        return conectorBanco;
    }

    /**
     * Cria um {@link EntityManager} para permitir o acesso ao banco de dados da aplicação.
     *
     * @return um {@link EntityManager} utilizado para interagir com o banco
     */
    public EntityManager criarEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Gera uma nova configuração complementar e cria um novo EntityManagerFactory com ela.
     */
    public void recarregarConfiguracoes() {
        configuradorBanco.gerarConfiguracoes();
        entityManagerFactory = Persistence.createEntityManagerFactory("jun", configuradorBanco.getConfiguracoes());
    }

    /**
     * Fecha a conexão com o banco.
     */
    public static void fecharConexao() {
        // TODO: Fechar o EntityManager dos DAOs que herdam de uma classe específica
        getInstance().entityManagerFactory.close();
    }
}
