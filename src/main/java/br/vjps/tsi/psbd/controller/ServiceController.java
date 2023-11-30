package br.vjps.tsi.psbd.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.vjps.tsi.psbd.dao.DAO;
import br.vjps.tsi.psbd.dao.DogDAO;
import br.vjps.tsi.psbd.dao.ServiceDAO;
import br.vjps.tsi.psbd.enumeration.ServiceStatus;
import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.model.Dog;
import br.vjps.tsi.psbd.model.Service;
import br.vjps.tsi.psbd.model.ServiceType;
import br.vjps.tsi.psbd.utility.Utility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Classe responsável por controlar e gerenciar as 
 * funcionalidades de manipulação de Serviços Agendados.
 * 
 * @author Vinicius J P Silva
 */
@Controller
public class ServiceController {

	/**
	 * Retorna a página de formulário de registro de novos tipos de serviço.
	 *
	 * @return O nome da página do formulário de registro do tipo de serviço.
	 */
	@RequestMapping("schedule-service-page")
	public String register(Model model, HttpSession session) {

		try (DogDAO dogDAO = new DogDAO()) {
			List<Dog> dogs = dogDAO.getDogsByClient((Client) session.getAttribute("logged"));

			if (dogs.isEmpty()) {
				model.addAttribute("message", "Não há cães cadastrados! ");
				return "messages/message";
			}

			model.addAttribute("dogs", dogs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<ServiceType> servicesTypes = new DAO<ServiceType>(ServiceType.class).list();

		if (servicesTypes.isEmpty()) {
			model.addAttribute("message", "Não há serviços disponíveis! ");
			return "messages/message";
		}

		model.addAttribute("serviceTypes", servicesTypes);
		return "service/schedule-service";
	}

	/**
	 * Manipula a requisição para agendar um serviço.
	 *
	 * @param service            O objeto Service contendo as informações do serviço
	 *                           a ser agendado.
	 * @param selectedDogName    O nome do cão selecionado para o serviço.
	 * @param request            O objeto HttpServletRequest que contém os
	 *                           parâmetros da requisição.
	 * @param redirectAttributes O objeto RedirectAttributes para adicionar
	 *                           atributos para redirecionamento.
	 * @param session            O objeto HttpSession que representa a sessão do
	 *                           cliente.
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("shedule-service-create")
	public String shedule(@ModelAttribute @Valid Service service, @RequestParam String selectedDogName,
			HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
		Client client = (Client) session.getAttribute("logged");

		try (DogDAO dogDao = new DogDAO()) {
			service.setDog(dogDao.getDogByNameAndClient(selectedDogName, client));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Calendar today = Utility.getTodayCalendar();
		if (!service.getScheduledDate().after(today)) {
			redirectAttributes.addFlashAttribute("message", "Forneça uma data futura.");
			return "redirect:schedule-service-page";
		}

		String[] servicesSelected = request.getParameterValues("services");

		DAO<ServiceType> serviceTypeDao = new DAO<ServiceType>(ServiceType.class);
		Set<ServiceType> serviceTypes = new HashSet<>();
		if (servicesSelected != null)
			for (String serviceId : servicesSelected)
				serviceTypes.add(serviceTypeDao.findById(Long.parseLong(serviceId)));
		else {
			redirectAttributes.addFlashAttribute("message", "Selecione um ou mais serviços.");
			return "redirect:schedule-service-page";
		}

		service.setServiceTypes(serviceTypes);

		try (ServiceDAO serviceDAO = new ServiceDAO()) {
			if (serviceDAO.getServicesSheduledByDate(service.getScheduledDate()) != null) {
				redirectAttributes.addFlashAttribute("message",
						"A data selecionada está indisponível para agendamento!");
				return "redirect:schedule-service-page";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		service.setStatus(ServiceStatus.OPEN);

		DAO<Service> dao = new DAO<Service>(Service.class);
		dao.add(service);

		redirectAttributes.addFlashAttribute("message", "Serviço agendado com sucesso!");
		return "redirect:schedule-service-page";
	}

	/**
	 * Lista os serviços agendados em aberto para o cliente logado.
	 *
	 * @param model   O objeto Model do Spring MVC para adicionar atributos à
	 *                resposta.
	 * @param session O objeto HttpSession que representa a sessão do cliente.
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("sheduled-services-open")
	public String listOpened(Model model, HttpSession session) {
		Client client = (Client) session.getAttribute("logged");

		List<Service> services = null;
		try (ServiceDAO serviceDao = new ServiceDAO()) {
			services = serviceDao.getFutureOpenScheduledServicesByClient(client);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (services == null || services.isEmpty()) {
			model.addAttribute("message", "Não há agendamentos em aberto! ");
			return "messages/message";
		}

		services.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));

		model.addAttribute("services", services);
		return "service/scheduled-service-table";
	}

	/**
	 * Cancela um serviço agendado, alterando seu status para CANCELADO.
	 *
	 * @param service O objeto Service a ser cancelado.
	 */
	@ResponseBody
	@RequestMapping("scheduled-service-cancel")
	public void cancel(Service service) {
		DAO<Service> dao = new DAO<>(Service.class);
		service = dao.findById(service.getId());
		
		service.setReleaseDate(Utility.getTodayCalendar());
		service.setTotal(0.0);
		
		service.setStatus(ServiceStatus.CANCELED);
		dao.update(service);
	}

	/**
	 * Exibe a página de dados de serviços agendados.
	 *
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("scheduled-services-data")
	public String listByData() {
		return "service/scheduled-services-data";
	}
	
	/**
	 * Retorna a página de visualização para consultar serviços finalizados em um intervalo de datas.
	 *
	 * @return Uma string que representa a página de visualização para consultar serviços finalizados entre datas.
	 */
	@RequestMapping("finished-services-between")
	public String servicesBetweenData() {
		return "service/finished-services-between";
	}
	
	@RequestMapping("finished-services-list")
	public String finishedList(@RequestParam String startDate, @RequestParam String endDate, Model model) {

		List<Service> services = null;
		try (ServiceDAO serviceDAO = new ServiceDAO()) {
			if ((services = serviceDAO.getFinishedServicesBetweenDates(Utility.stringToCalendar(startDate), Utility.stringToCalendar(endDate))).isEmpty()) {
				model.addAttribute("message", "Não há serviços prestados/cancelados entre as datas informadas!");
				return "service/finished-services-between";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		services.stream().forEach(Service::calculateTotal);

		model.addAttribute("services", services);
		return "service/finished-services-list";
	}

	/**
	 * Lista os serviços agendados pendentes a partir de uma data especificada.
	 *
	 * @param stringDate A data no formato de string a partir da qual os
	 *                   agendamentos pendentes serão listados.
	 * @param model      O objeto Model do Spring MVC para adicionar atributos à
	 *                   resposta.
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("scheduled-services-pending")
	public String pending(@RequestParam String stringDate, Model model) {

		List<Service> services = null;
		try (ServiceDAO serviceDAO = new ServiceDAO()) {
			if ((services = serviceDAO.getPendingServices(Utility.stringToCalendar(stringDate))).isEmpty()) {
				model.addAttribute("message", "Não há agendamentos em aberto a partir da data informada!");
				return "messages/message";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		services.stream().forEach(Service::calculateTotal);

		model.addAttribute("services", services);
		return "service/scheduled-services-pending";
	}
	
	/**
	 * Lista os serviços finalizados e cancelados do cliente.
	 * 
	 * @param model      O objeto Model do Spring MVC para adicionar atributos à
	 *                   resposta.
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("scheduled-services-finished")
	public String finished(Model model, HttpSession session) {
		Client client = (Client) session.getAttribute("logged");
		
		List<Service> services = null;
		try (ServiceDAO serviceDAO = new ServiceDAO()) {
			if ((services = serviceDAO.getFinishedAndCanceledServicesByClient(client)).isEmpty()) {
				model.addAttribute("message", "Não há finalizados ou cancelados à serem exibidos!");
				return "messages/message";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("services", services);
		return "service/scheduled-services-finished";
	}

	/**
	 * Conclui e finaliza um serviço agendado, atualizando seu status para
	 * FINALIZADO e registrando a data de conclusão.
	 *
	 * @param id    O ID do serviço a ser finalizado.
	 * @param model O objeto Model do Spring MVC para adicionar atributos à
	 *              resposta.
	 * @return Uma string que representa a página de destino após a conclusão da
	 *         operação.
	 */
	@RequestMapping("scheduled-service-finish")
	public String finish(@RequestParam String id, Model model) {
		DAO<Service> dao = new DAO<>(Service.class);
		Service service = dao.findById(Long.parseLong(id));
		service.calculateTotal();
		service.setStatus(ServiceStatus.FINISHED);
		service.setReleaseDate(Utility.getTodayCalendar());
		dao.update(service);

		model.addAttribute("message", "Serviço lançado e finalizado com sucesso!");
		return "messages/message";
	}

}
