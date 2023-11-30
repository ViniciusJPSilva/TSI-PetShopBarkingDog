package br.vjps.tsi.psbd.dao;

import br.vjps.tsi.psbd.system.SystemSettings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * Classe utilitária para obter o EntityManager da unidade de persistência "petshop".
 * 
 * @author Vinicius J P Silva
 */
public class JPAUtil {

	private static EntityManagerFactory manager = Persistence.createEntityManagerFactory(SystemSettings.DATA_BASE_NAME);

	/**
     * Obtém uma instância do EntityManager para interações com o banco de dados.
     *
     * @return O EntityManager associado à unidade de persistência "petshop".
     */
	public EntityManager getEntityManager() {
		return manager.createEntityManager();
	}
}
