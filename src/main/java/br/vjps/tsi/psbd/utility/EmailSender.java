package br.vjps.tsi.psbd.utility;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.vjps.tsi.psbd.system.SystemSettings;

/**
 * A classe fornece métodos para enviar emails usando um servidor SMTP, como o Gmail. 
 * Utiliza a biblioteca Apache Commons Email para simplificar o envio de emails.
 * 
 * @author Vinícius J P Silva
 */
public final class EmailSender {

    private EmailSender() {}
    
    /**
     * Envia um email de forma assíncrona em uma thread separada. Isso permite que o envio de email
     * ocorra em segundo plano sem bloquear a execução do programa principal.
     *
     * @param message       O conteúdo da mensagem a ser enviada no email.
     * @param subject       O assunto do email.
     * @param receiverEmail O endereço de email do destinatário.
     */
    public static void sendAsync(final String message, final String subject, final String receiverEmail) {
        Runnable emailTask = new Runnable() {
            @Override
            public void run() {
                send(message, subject, receiverEmail);
            }
        };

        Thread thread = new Thread(emailTask);
        thread.start();
    }
	
    /**
     * Envia um email com a mensagem especificada para o destinatário especificado.
     *
     * @param message       O conteúdo da mensagem a ser enviada.
     * @param subject       O assunto do email.
     * @param receiverEmail O endereço de email do destinatário.
     */
	public static void send(String message, String subject, String receiverEmail) {
		try {
			SimpleEmail email = new SimpleEmail();
			
            email.setHostName("smtp.googlemail.com");
            
            email.setAuthentication(SystemSettings.SENDER_EMAIL, SystemSettings.SENDER_EMAIL_PASSWD);
            email.setSmtpPort(587); // Use a porta TLS apropriada (587 para TLS)

            // Ativar TLS
            email.setStartTLSEnabled(true);
            email.setSSLOnConnect(true);
            
            email.setFrom(SystemSettings.SENDER_EMAIL);
            
            email.setSubject(subject);
	        email.setMsg(message);
	        email.addTo(receiverEmail);
	        
	        email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
	}
}