package br.vjps.tsi.psbd.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos funcionários no banco de dados.
 * Implementa a interface Closeable para garantir o fechamento adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class EmployeeDAO implements Closeable {
	private EntityManager manager;

	/**
     * Construtor padrão que inicializa o EntityManager usando a classe utilitária JPAUtil.
     */
	public EmployeeDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
     * Valida as credenciais de um funcionário para fins de autenticação.
     *
     * @param employee O funcionário cujas credenciais estão sendo validadas.
     * @return O funcionário autenticado ou null se as credenciais forem inválidas.
     * @throws IllegalArgumentException Se o funcionário fornecido for nulo.
     */
	public Employee validate(Employee employee) {
		if (employee == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Employee u where u.login = :login and u.password = :password",
				Client.class);
		query.setParameter("login", employee.getLogin());
		query.setParameter("password", employee.getPassword());

		@SuppressWarnings("unchecked")
		List<Employee> employees = query.getResultList();

		return employees.isEmpty() ? null : employees.get(0);
	}
	
	/**
     * Obtém um funcionário com base no seu login.
     *
     * @param login O login do funcionário a ser recuperado.
     * @return O funcionário correspondente ao login fornecido ou null se não encontrado.
     * @throws IllegalArgumentException Se o login fornecido for nulo.
     */
	public Employee getByLogin(String login) {
		if(login == null)
			throw new IllegalArgumentException("Login não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Employee u where u.login = :login",
				Client.class);
		query.setParameter("login", login);

		@SuppressWarnings("unchecked")
		List<Employee> employees = query.getResultList();

		return employees.isEmpty() ? null : employees.get(0);
	}
	
	/**
     * Fecha o EntityManager quando este objeto EmployeeDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
}
