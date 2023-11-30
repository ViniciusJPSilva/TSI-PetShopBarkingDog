package br.vjps.tsi.psbd.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import br.vjps.tsi.psbd.model.Service;
import br.vjps.tsi.psbd.model.ServiceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos tipos de serviço no banco de dados.
 * Implementa a interface Closeable para garantir o fechamento adequado do EntityManager.
 *
 * @author Vinicius J P Silva
 */
public class ServiceTypeDAO implements Closeable {
	private EntityManager manager;

	/**
     * Construtor padrão que inicializa o EntityManager usando a classe utilitária JPAUtil.
     */
	public ServiceTypeDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
     * Obtém um tipo de serviço com base no seu nome.
     *
     * @param name O nome do tipo de serviço a ser recuperado.
     * @return O tipo de serviço correspondente ao nome fornecido ou null se não encontrado.
     * @throws IllegalArgumentException Se o nome fornecido for nulo.
     */
	public ServiceType getServiceTypeByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM ServiceType u where u.name = :name",
				Service.class);
		query.setParameter("name", name);

		@SuppressWarnings("unchecked")
		List<ServiceType> services = query.getResultList();

		return services.isEmpty() ? null : services.get(0);
	}
	
	/**
     * Fecha o EntityManager quando este objeto ServiceTypeDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
}
