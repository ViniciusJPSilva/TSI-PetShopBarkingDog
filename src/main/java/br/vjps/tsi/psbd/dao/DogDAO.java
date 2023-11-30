package br.vjps.tsi.psbd.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.model.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos cães no banco de dados.
 * Implementa a interface Closeable para garantir o fechamento adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class DogDAO implements Closeable {
	private EntityManager manager;

	/**
     * Construtor padrão que inicializa o EntityManager usando a classe utilitária JPAUtil.
     */
	public DogDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
     * Obtém uma lista de cães pertencentes a um cliente específico.
     *
     * @param client O cliente para o qual recuperar a lista de cães.
     * @return Uma lista de cães pertencentes ao cliente especificado.
     * @throws IllegalArgumentException Se o cliente fornecido for nulo.
     */
	public List<Dog> getDogsByClient(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Dog u where u.client = :client",
				Dog.class);
		query.setParameter("client", client);

		@SuppressWarnings("unchecked")
		List<Dog> dogs = query.getResultList();

		return dogs;
	}
	
	/**
	 * Obtém um objeto Dog pelo nome e cliente associado.
	 *
	 * @param name   O nome do cão.
	 * @param client O cliente associado ao cão.
	 * @return Um objeto Dog correspondente ao nome e cliente fornecidos, ou null se não for encontrado.
	 * @throws IllegalArgumentException Se o cliente ou o nome fornecidos forem nulos.
	 */
	public Dog getDogByNameAndClient(String name, Client client) {
		if (client == null || name == null)
			throw new IllegalArgumentException("Nome e Cliente não podem ser nulos!");

		Query query = manager.createQuery("SELECT u FROM Dog u where u.client = :client AND u.name = :name",
				Dog.class);
		query.setParameter("client", client);
		query.setParameter("name", name);

		@SuppressWarnings("unchecked")
		List<Dog> dogs = query.getResultList();

		return dogs.isEmpty() ? null : dogs.get(0);
	}
	
	/**
     * Fecha o EntityManager quando este objeto DogDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
}
