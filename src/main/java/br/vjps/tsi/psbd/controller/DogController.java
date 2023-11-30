package br.vjps.tsi.psbd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.vjps.tsi.psbd.dao.DAO;
import br.vjps.tsi.psbd.dao.DogDAO;
import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.model.Dog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Classe responsável por controlar e gerenciar as 
 * funcionalidades de manipulação dos dados de Cachorros.
 * 
 * @author Vinicius J P Silva
 */
@Controller
public class DogController {

	/**
	 * Retorna a página de formulário de registro de cães.
	 *
	 * @return O nome da página do formulário de registro do cão.
	 */
	@RequestMapping("dog-register")
	public String register() {
		return "dog/dog-registration";
	}
	
	/**
     * Cria um novo cachorro e associa ao cliente.
     *
     * @param dog                 O cachorro a ser criado e registrado.
     * @param bindingResult       Resultados da validação do formulário.
     * @param redirectAttributes  Atributos de redirecionamento para mensagens flash e dados do cliente.
     * @return Redireciona para a página de registro com mensagens apropriadas, dependendo do resultado.
     */
	@RequestMapping("dog-create")
	public String create(@Valid Dog dog, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
		if (bindingResult.hasFieldErrors()) 
			return "dog/dog-registration";
		
		dog.setClient((Client) session.getAttribute("logged"));
		try (DogDAO dogDAO = new DogDAO()) {
			for(Dog dogSearch : dogDAO.getDogsByClient(dog.getClient()))
				if(dog.getName().equalsIgnoreCase(dogSearch.getName())) {
					redirectAttributes.addFlashAttribute("message", String.format("O cão '%s' já está cadastrado!", dog.getName()));
					return "redirect:dog-register";
				}
			
			new DAO<Dog>(Dog.class).add(dog); 
			
			redirectAttributes.addFlashAttribute("message", "Cachorro cadastrado com sucesso!");
			return "redirect:dog-register";
		} catch (Exception e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message", "Ocorreu um erro. Tente novamente.");
		return "redirect:dog-register";
	}
}
