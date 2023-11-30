package br.vjps.tsi.psbd.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import br.vjps.tsi.psbd.enumeration.ServiceStatus;
import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.model.Service;
import br.vjps.tsi.psbd.utility.Utility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

public class ServiceDAO implements Closeable {
	private EntityManager manager;

	/**
     * Construtor padrão que inicializa o EntityManager usando a classe utilitária JPAUtil.
     */
	public ServiceDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
     * Obtém um serviço agendado com base na sua data de agendamento.
     *
	 * @param date A data para a qual se deseja obter o serviço agendado.
	 * @return O serviço agendado para a data especificada, ou null se não houver serviços agendados.
	 * @throws IllegalArgumentException Se a data fornecida for nula.
     */
	public Service getServicesSheduledByDate(Calendar date) {
		if (date == null)
			throw new IllegalArgumentException("Data não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Service u WHERE u.scheduledDate = :scheduledDate AND u.status = :status",
				Service.class);
		query.setParameter("scheduledDate", date, TemporalType.DATE);
		query.setParameter("status", ServiceStatus.OPEN);

		@SuppressWarnings("unchecked")
		List<Service> services = query.getResultList();

		return services.isEmpty() ? null : services.get(0);
	}
	
	/**
	 * Obtém uma lista de serviços agendados pendentes a partir de uma determinada data.
	 *
	 * @param date A data a partir da qual os serviços pendentes serão obtidos.
	 * @return Uma lista de serviços agendados pendentes.
	 * @throws IllegalArgumentException Se a data fornecida for nula.
	 */
	public List<Service> getPendingServices(Calendar date) {
		if (date == null)
			throw new IllegalArgumentException("Data não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Service u WHERE u.scheduledDate >= :date AND u.status = :status",
				Service.class);
		query.setParameter("date", date, TemporalType.DATE);
		query.setParameter("status", ServiceStatus.OPEN);

		@SuppressWarnings("unchecked")
		List<Service> services = query.getResultList();

		return services;
	}
	
	/**
	 * Obtém uma lista de serviços finalizados em um intervalo de datas.
	 *
	 * @param start A data de início do intervalo.
	 * @param end   A data de término do intervalo.
	 * @return Uma lista de serviços finalizados no intervalo de datas especificado.
	 * @throws IllegalArgumentException Se a data de início ou a data de término fornecida for nula.
	 */
	public List<Service> getFinishedServicesBetweenDates(Calendar start, Calendar end) {
		if (start == null || end == null)
			throw new IllegalArgumentException("Data não pode ser nulo!");

		Query query = manager.createQuery("SELECT u FROM Service u WHERE u.releaseDate >= :start AND u.releaseDate <= :end",
				Service.class);
		query.setParameter("start", start, TemporalType.DATE);
		query.setParameter("end", end, TemporalType.DATE);

		@SuppressWarnings("unchecked")
		List<Service> services = query.getResultList();

		return services;
	}
	
	/**
	 * Obtém uma lista de serviços finalizados ou cancelados associados a um cliente.
	 *
	 * @param client O cliente associado aos serviços.
	 * @return Uma lista de serviços finalizados ou cancelados associados ao cliente especificado.
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public List<Service> getFinishedAndCanceledServicesByClient(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Client não pode ser nulo!");

		Query query = manager.createQuery("SELECT s FROM Service s INNER JOIN s.dog d INNER JOIN d.client c WHERE s.status != :status AND c = :client",
				Service.class);
		query.setParameter("status", ServiceStatus.OPEN);
		query.setParameter("client", client);

		@SuppressWarnings("unchecked")
		List<Service> services = query.getResultList();

		return services;
	}
	
	/**
	 * Obtém uma lista de serviços agendados abertos e futuros para um cliente específico.
	 *
	 * @param client O cliente para o qual os serviços agendados futuros e abertos serão obtidos.
	 * @return Uma lista de serviços agendados abertos e futuros para o cliente.
	 * @throws IllegalArgumentException Se o ID do cliente fornecido for nulo.
	 */
	public List<Service> getFutureOpenScheduledServicesByClient(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Client não pode ser nulo!");

		Query query = manager.createQuery("SELECT s FROM Service s INNER JOIN s.dog d INNER JOIN d.client c WHERE s.scheduledDate >= :today AND s.status = :status AND c = :client",
				Service.class);
		query.setParameter("today", Utility.getTodayCalendar());
		query.setParameter("status", ServiceStatus.OPEN);
		query.setParameter("client", client);

		@SuppressWarnings("unchecked")
		List<Service> services = query.getResultList();

		return services;
	}
	
	/**
     * Fecha o EntityManager quando este objeto ServiceDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
	
}
	
