package br.vjps.tsi.psbd.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import br.vjps.tsi.psbd.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos clientes no
 * banco de dados. Implementa a interface Closeable para garantir o fechamento
 * adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class ClientDAO implements Closeable {
	private EntityManager manager;

	/**
	 * Construtor padrão que inicializa o EntityManager usando a classe utilitária
	 * JPAUtil.
	 */
	public ClientDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
	 * Valida as credenciais de um cliente para fins de autenticação.
	 *
	 * @param client O cliente cujas credenciais estão sendo validadas.
	 * @return O cliente autenticado ou null se as credenciais forem inválidas.
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public Client validate(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Client u where u.email = :email and u.password = :password",
				Client.class);
		query.setParameter("email", client.getEmail());
		query.setParameter("password", client.getPassword());

		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}

	/**
	 * Obtém um cliente com base no número de CPF.
	 *
	 * @param cpf O número de CPF do cliente a ser recuperado.
	 * @return O cliente correspondente ao CPF fornecido ou null se não encontrado.
	 * @throws IllegalArgumentException Se o CPF fornecido for nulo.
	 */
	public Client getByCPF(String cpf) {
		if (cpf == null)
			throw new IllegalArgumentException("CPF não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Client u where u.cpf = :cpf", Client.class);
		query.setParameter("cpf", cpf);

		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}

	/**
     * Obtém um cliente com base no token de confirmação.
     *
     * @param token O token de confirmação associado ao cliente.
     * @return O cliente correspondente ao token fornecido ou null se não encontrado.
     * @throws IllegalArgumentException Se o token fornecido for nulo.
     */
	public Client getByToken(String token) {
		if (token == null)
			throw new IllegalArgumentException("Token não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Client u where u.token = :token", Client.class);
		query.setParameter("token", token);

		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}

	/**
     * Obtém um cliente com base no CPF ou no endereço de e-mail, garantindo unicidade.
     *
     * @param cpf   O número de CPF do cliente.
     * @param email O endereço de e-mail do cliente.
     * @return O cliente correspondente ao CPF ou e-mail fornecido ou null se não encontrado.
     * @throws IllegalArgumentException Se o CPF ou e-mail fornecidos forem nulos.
     */
	public Client getByRegisteredUniqueData(String cpf, String email) {
		if (cpf == null || email == null)
			throw new IllegalArgumentException("CPF e/ou email não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Client u where u.cpf = :cpf OR u.email = :email",
				Client.class);
		query.setParameter("cpf", cpf);
		query.setParameter("email", email);

		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}

	/**
     * Fecha o EntityManager quando este objeto ClientDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
}
