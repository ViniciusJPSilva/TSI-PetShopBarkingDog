package br.vjps.tsi.psbd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.vjps.tsi.psbd.dao.ClientDAO;
import br.vjps.tsi.psbd.dao.DAO;
import br.vjps.tsi.psbd.model.Client;
import br.vjps.tsi.psbd.system.SystemSettings;
import br.vjps.tsi.psbd.utility.EmailSender;
import br.vjps.tsi.psbd.utility.TokenGenerator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Classe responsável por controlar e gerenciar as funcionalidades disponíveis
 * para os clientes.
 * 
 * @author Vinicius J P Silva
 */
@Controller
public class ClientController {

	/**
	 * Retorna a página de formulário de login do cliente.
	 *
	 * @return O nome da página do formulário de login do cliente.
	 */
	@RequestMapping("client-login-page")
	public String loginPage() {
		return "client/client-login";
	}

	/**
	 * Processa a tentativa de login do cliente.
	 *
	 * @param client             O cliente que está tentando fazer login.
	 * @param session            A sessão HTTP para armazenar informações de login.
	 * @param redirectAttributes Atributos de redirecionamento para mensagens flash.
	 * @return O nome da página a ser exibida após o login bem-sucedido ou a
	 *         redireção para o formulário de login em caso de falha.
	 */
	@RequestMapping("client-login")
	public String login(Client client, HttpSession session, RedirectAttributes redirectAttributes) {
		try(ClientDAO clientDAO = new ClientDAO()) {
			if ((client = clientDAO.validate(client)) != null) {
				
				if(client.isConfirmed()) {
					session.setAttribute("logged", client);
					session.setAttribute("isClient", true);
					session.setMaxInactiveInterval(SystemSettings.INACTIVITY_TIMEOUT * 60);
		
					return "client/client-menu";
				} else {
					redirectAttributes.addFlashAttribute("message", "É necessário confirmar o registro antes de logar! Verifique seu e-mail.");
					return "redirect:client-login-page";
				}
			}
		} catch (Exception e) {
		}

		redirectAttributes.addFlashAttribute("message", "E-mail e/ou senha incorretos");
		return "redirect:client-login-page";
	}

	/**
	 * Realiza o logout do cliente, invalidando a sessão.
	 *
	 * @param session A sessão HTTP a ser invalidada.
	 * @return Redireciona para a página de login após o logout.
	 */
	@RequestMapping("client-logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:client-login-page";
	}

	/**
	 * Retorna a página do menu do cliente.
	 *
	 * @return O nome da página do menu do cliente.
	 */
	@RequestMapping("client-menu")
	public String menu() {
		return "client/client-menu";
	}

	/**
	 * Retorna a página de formulário de registro do cliente.
	 *
	 * @return O nome da página do formulário de registro do cliente.
	 */
	@RequestMapping("client-register")
	public String register() {
		return "client/client-registration";
	}

	/**
     * Confirma o registro do cliente usando um token de confirmação.
     *
     * @param token               O token de confirmação enviado ao cliente.
     * @param redirectAttributes  Atributos de redirecionamento para mensagens flash.
     * @return Redireciona para a página de login após a confirmação bem-sucedida ou exibe uma mensagem de erro.
     */
	@GetMapping("/client-confirm-registration")
	public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
		Client client = null;
		try(ClientDAO clientDAO = new ClientDAO()) {
			client = clientDAO.getByToken(token);
		} catch (Exception e) {
		}

		if (client != null) {
			client.setConfirmed(true);

			DAO<Client> dao = new DAO<>(Client.class);
			dao.update(client);

			redirectAttributes.addFlashAttribute("message", "Seu registro foi confirmado com sucesso!");
			return "redirect:client-login-page";
		} else {
			redirectAttributes.addFlashAttribute("message", "Token inválido!");
			return "redirect:client-login-page";
		}
	}

	/**
     * Cria um novo cliente e inicia o processo de confirmação do cadastro.
     *
     * @param client              O cliente a ser criado e registrado.
     * @param bindingResult      Resultados da validação do formulário.
     * @param redirectAttributes  Atributos de redirecionamento para mensagens flash e dados do cliente.
     * @return Redireciona para a página de registro ou login com mensagens apropriadas, dependendo do resultado.
     */
	@RequestMapping("client-create")
	public String create(@Valid Client client, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasFieldErrors())
			return "client/client-registration";

		final String EMAIL_TITLE = "Confirmação de Cadastro - Cão Q-Late";
		redirectAttributes.addFlashAttribute("client", client);

		try (ClientDAO clientDAO = new ClientDAO()){
			if (clientDAO.getByRegisteredUniqueData(client.getCpf(), client.getEmail()) != null) {
				redirectAttributes.addFlashAttribute("message", "CPF e/ou email já cadastrados");
				return "redirect:client-register";
			}
			
			// Gerando token e enviando o email
			if (SystemSettings.EMAIL_MODE != SystemSettings.EMAIL_SENDING_MODE.DO_NOT_SEND)
				createTokenAndSendEmail(client, EMAIL_TITLE);
			else
				client.setConfirmed(true);

			new DAO<>(Client.class).add(client);

			redirectAttributes.addFlashAttribute("message", "Um link de confirmação foi enviado para o seu e-mail");
			return "redirect:client-login-page";
		} catch (Exception e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message", "Ocorreu um erro. Tente novamente.");
		return "redirect:client-register";
	}

	/**
	 * Gera um token de confirmação, associa-o ao cliente e envia um e-mail de confirmação.
	 *
	 * @param client      O cliente para o qual o token está sendo gerado e o e-mail está sendo enviado.
	 * @param emailTitle  O título do e-mail de confirmação.
	 */
	private void createTokenAndSendEmail(Client client, String emailTitle) {
		client.setToken(TokenGenerator.generateToken());
		client.setConfirmed(false);

		String confirmationLink = String.format("%s/client-confirm-registration?token=%s", SystemSettings.BASE_URL,
				client.getToken());

		String emailMessage = String.format(
				"Prezado(a) %s,\nSeu cadastro no sistema do PetShop - Cão Q-Late está quase finalizado!\nPor gentileza, clique no link para confirmar o cadastro:\n%s",
				client.getName(), confirmationLink);

		EmailSender.sendAsync(emailMessage, emailTitle,
				(SystemSettings.EMAIL_MODE == SystemSettings.EMAIL_SENDING_MODE.SEND_TO_TEST)
						? SystemSettings.TEST_EMAIL
						: client.getEmail());
	}
	
	/**
	 * Retorna a página de alteração dos dados do cliente.
	 *
	 * @return O nome da página do formulário de alteração do cliente.
	 */
	@RequestMapping("client-change-data-page")
	public String changeDataPage(Client client, Model model, HttpSession session) {
		model.addAttribute("client", (Client) session.getAttribute("logged"));
		return "client/client-change-data";
	}

    /**
     * Atualiza os dados de um cliente.
     *
     * @param oldPassword         A senha antiga do cliente para verificação.
     * @param client              O cliente com os novos dados a serem atualizados.
     * @param bindingResult       Resultados da validação do formulário.
     * @param session             A sessão HTTP contendo as informações do cliente logado.
     * @param model               O modelo que pode ser usado para adicionar atributos à visualização.
     * @param redirectAttributes  Atributos de redirecionamento para mensagens flash.
     * @return Redireciona para a página de alteração de dados com mensagens apropriadas, ou para a página de mensagens de sucesso.
     */
	@RequestMapping("client-update")
	public String update(@RequestParam String oldPassword, @ModelAttribute @Valid Client client, BindingResult bindingResult, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasFieldErrors()) 
	        return "client/client-change-data";
		
		// Checando a senha
		if(!oldPassword.equalsIgnoreCase(((Client) session.getAttribute("logged")).getPassword())) {
			redirectAttributes.addFlashAttribute("message", "Senha incorreta.");
			return "redirect:client-change-data-page";
		}
		
		DAO<Client> dao = new DAO<Client>(Client.class);
		Client old = dao.findById(client.getId());
		
		client.setToken(old.getToken());
		client.setConfirmed(true);
		
		dao.update(client);
		
		session.setAttribute("logged", client);
		
		model.addAttribute("message", "Dados atualizados com sucesso! ");
		return "messages/message";
	}
}
