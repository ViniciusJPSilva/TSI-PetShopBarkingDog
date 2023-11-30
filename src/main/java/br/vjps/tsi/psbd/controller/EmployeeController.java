package br.vjps.tsi.psbd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.vjps.tsi.psbd.dao.EmployeeDAO;
import br.vjps.tsi.psbd.model.Employee;
import br.vjps.tsi.psbd.system.SystemSettings;
import jakarta.servlet.http.HttpSession;

/**
 * Classe responsável por controlar e gerenciar as funcionalidades disponíveis
 * para os funcionários.
 * 
 * @author Vinicius J P Silva
 */
@Controller
public class EmployeeController {

	/**
	 * Retorna a página de formulário de login do funcionário.
	 *
	 * @return O nome da página do formulário de login do funcionário.
	 */
	@RequestMapping("employee-login-page")
	public String loginPage() {
		return "employee/employee-login";
	}

	/**
	 * Processa a tentativa de login do Funcionário.
	 *
	 * @param employee           O funcionário que está tentando fazer login.
	 * @param session            A sessão HTTP para armazenar informações de login.
	 * @param redirectAttributes Atributos de redirecionamento para mensagens flash.
	 * @return O nome da página a ser exibida após o login bem-sucedido ou a
	 *         redireção para o formulário de login em caso de falha.
	 */
	@RequestMapping("employee-login")
	public String login(Employee employee, HttpSession session, RedirectAttributes redirectAttributes) {
		try (EmployeeDAO employeeDAO = new EmployeeDAO()) {
			if ((employee = employeeDAO.validate(employee)) != null) {
				session.setAttribute("logged", employee);
				session.setAttribute("isClient", false);

				session.setMaxInactiveInterval(SystemSettings.INACTIVITY_TIMEOUT * 60);

				return "employee/employee-menu";
			}
		} catch (Exception e) {
		}

		redirectAttributes.addFlashAttribute("message", "Login e/ou senha incorretos");
		return "redirect:employee-login-page";
	}

	/**
	 * Retorna a página do menu do funcionário.
	 *
	 * @return O nome da página do menu do cliente.
	 */
	@RequestMapping("employee-menu")
	public String menu() {
		return "employee/employee-menu";
	}

	/**
	 * Realiza o logout de um funcionário, invalidando a sessão e redirecionando
	 * para a página de login de funcionário.
	 *
	 * @param session O objeto HttpSession que representa a sessão do funcionário.
	 * @return Uma string que representa a página de destino após o logout.
	 */
	@RequestMapping("employee-logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:employee-login-page";
	}
}
