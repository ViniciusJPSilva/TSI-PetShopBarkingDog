package br.vjps.tsi.psbd.system;

/**
 * Classe responsável por agrupar todas as constantes de pré-configurações do sistema, 
 * incluindo informações de configurações de envio de e-mail e outras configurações importantes.
 * 
 * @author Vinicius J P Silva
 */
public final class SystemSettings {
	
	private SystemSettings() {}
	
	
	// Define os modos de envio dos e-mail.
	public static enum EMAIL_SENDING_MODE {
		// MODO PADRÂO: efetua o envio para o e-mail do cliente, cadastrado no banco de dados.
		SEND_TO_CLIENT,
		
		// MODO DE TESTE: não efetua o envio de nenhum e-mail.
		// AO ATIVAR ESSE MODO, OS CADASTROS IRÃO SER EFETIVADOS IMEDIATAMENTE, SEM O ENVIO DO TOKEN.
		DO_NOT_SEND,
		
		// MODO DE TESTE: efetua o envio para o e-mail de teste - TEST_EMAIL - definido abaixo.
		SEND_TO_TEST
	};

	/**
	 *  Define se os e-mails serão enviados aos clientes, ao e-mail de teste ou não serão enviados.
	 *  Utilize os valores disponíveis em EMAIL_SENDING_MODE.
	 */
	public static final EMAIL_SENDING_MODE 
							EMAIL_MODE = EMAIL_SENDING_MODE.SEND_TO_TEST;	
	
	
	// Constantes do sistema.
	public static final String 
							
							// E-mail e senha utilizandos para efetuar o envio dos e-mails.
							SENDER_EMAIL = "", 
							SENDER_EMAIL_PASSWD = "",
							
							/* 
							 * E-mail de teste: os e-mails serão enviados 
							 * para o mesmo, caso o modo de teste esteja ativado.
							 * (EMAIL_SENDING_MODE.SEND_TO_TEST)
							 */
							TEST_EMAIL = "",
							
							/**
							 * Nome do banco de dados onde as tabelas serão criadas e os dados persistidos.
							 */
							DATA_BASE_NAME = "petshop",
							
							/**
							 *  URL base da aplicação, será utilizada para a confirmação de cadastro (envio do token).
							 */
							BASE_URL = "http://localhost:3000/PetShopBarkingDog";
	
	/**
	 * Define o tempo limite de inatividade (em minutos) dos usuários.
	 * Após o tempo determinado o usuário será desconectado automaticamente pelo sistema.
	 */
	public static final int INACTIVITY_TIMEOUT = 2;

}
