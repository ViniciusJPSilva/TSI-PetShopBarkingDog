package br.vjps.tsi.psbd.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.vjps.tsi.psbd.dao.DAO;
import br.vjps.tsi.psbd.dao.ServiceTypeDAO;
import br.vjps.tsi.psbd.model.ServiceType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Classe responsável por controlar e gerenciar as 
 * funcionalidades de manipulação de Tipos de Serviços.
 * 
 * @author Vinicius J P Silva
 */
@Controller
public class ServiceTypeController {

	/**
	 * Retorna a página de formulário de registro de novos tipos de serviço.
	 *
	 * @return O nome da página do formulário de registro do tipo de serviço.
	 */
	@RequestMapping("service-type-register")
	public String register() {
		return "service-type/service-type-registration";
	}
	
	/**
     * Cria um novo tipo de serviço disponível.
     *
     * @param service             O tipo de serviço a ser criado e registrado.
     * @param bindingResult       Resultados da validação do formulário.
     * @param redirectAttributes  Atributos de redirecionamento para mensagens flash e dados do cliente.
     * @return Redireciona para a página de registro com mensagens apropriadas, dependendo do resultado.
     */
	@RequestMapping("service-type-create")
	public String create(@Valid ServiceType service, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
		if (bindingResult.hasFieldErrors()) 
			return "service-type/service-type-registration";
		
		try (ServiceTypeDAO serviceTypeDao = new ServiceTypeDAO()) {
			if(serviceTypeDao.getServiceTypeByName(service.getName()) != null) {
				redirectAttributes.addFlashAttribute("message", String.format("O serviço '%s' já foi cadastrado!", service.getName()));
				return "redirect:service-type-register";
			}
			
			new DAO<ServiceType>(ServiceType.class).add(service); 
			
			redirectAttributes.addFlashAttribute("message", "Serviço cadastrado com sucesso!");
			return "redirect:service-type-register";
		} catch (Exception e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message", "Ocorreu um erro. Tente novamente.");
		return "redirect:service-type-register";
	}
	
	/**
	 * Retorna a página com a tabela contendo os tipos de serviços cadastrados
	 *
	 * @return O nome da página.
	 */
	@RequestMapping("service-type-table")
	public String list(Model model) {
		DAO<ServiceType> dao = new DAO<>(ServiceType.class);
		
		List<ServiceType> services = dao.list();
		
		if (services.isEmpty()) {
			model.addAttribute("message", "Não há serviços cadastrados! ");
			return "messages/message";
		}
		
		services.sort((s1, s2) -> Long.compare(s1.getId(), s2.getId()));
		
		model.addAttribute("services", services);
		return "service-type/service-type-table";
	}
	
	/**
	 * Atualiza o custo de um tipo de serviço, validando os dados e retornando a página de atualização.
	 *
	 * @param service       O objeto ServiceType a ser atualizado.
	 * @param bindingResult O objeto BindingResult que contém os resultados da validação.
	 * @param model         O objeto Model do Spring MVC para adicionar atributos à resposta.
	 * @return Uma string que representa a página de destino após a atualização.
	 */
	@RequestMapping("service-type-change-cost")
	public String update(@Valid ServiceType service, BindingResult bindingResult, Model model) {
		DAO<ServiceType> dao = new DAO<>(ServiceType.class);
		Double cost = service.getCost();
		
		service = dao.findById(service.getId());
		
		if (bindingResult.hasFieldErrors()) {
			model.addAttribute("service", service);
			return "service-type/service-type-update";
		}
				
		service.setCost(cost);
		
		dao.update(service);
		model.addAttribute("service", service);
		return "service-type/service-type-update";
	}
}
