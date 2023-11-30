package br.vjps.tsi.psbd.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Objeto de Acesso a Dados (DAO) genérico para operações CRUD no banco de dados utilizando JPA.
 * 
 * @author Vinicius J P Silva
 * 
 * @param <T> O tipo de entidade com a qual o DAO irá interagir.
 */
public class DAO<T> {
	private Class<T> tClass;

	/**
     * Construtor que recebe a classe da entidade para ser manipulada pelo DAO.
     *
     * @param tClass A classe da entidade.
     */
	public DAO(Class<T> tClass) {
		this.tClass = tClass;
	}

	/**
     * Adiciona uma nova entidade ao banco de dados.
     *
     * @param t A entidade a ser adicionada.
     */
	public void add(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();

		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		manager.close();
	}

	/**
     * Atualiza uma entidade no banco de dados.
     *
     * @param t A entidade a ser atualizada.
     */
	public void update(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();

		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();
		manager.close();
	}

	/**
     * Remove uma entidade do banco de dados.
     *
     * @param t A entidade a ser removida.
     */
	public void remove(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();

		manager.getTransaction().begin();
		manager.remove(manager.merge(t));
		manager.getTransaction().commit();
		manager.close();
	}

	/**
     * Busca uma entidade pelo seu identificador único (ID) no banco de dados.
     *
     * @param id O identificador único da entidade.
     * @return A entidade correspondente ao ID fornecido ou null se não encontrada.
     */
	public T findById(Long id) {
		try(EntityManager manager = new JPAUtil().getEntityManager()) {
			return manager.find(tClass, id);
		}
	}
	
	/**
     * Retorna uma lista de todas as entidades do tipo especificado no banco de dados.
     *
     * @return Uma lista contendo todas as entidades do tipo especificado.
     */
	public List<T> list(){
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(tClass);
		query.select(query.from(tClass));
		List<T> list = manager.createQuery(query).getResultList();
		manager.close();
		return list;
	}
}
