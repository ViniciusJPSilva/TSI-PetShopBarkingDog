package br.vjps.tsi.psbd.utility;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Uma classe utilitária para gerar tokens de confirmação.
 * 
 * @author Vinicius J P Silva
 */
public class TokenGenerator {
	
	/**
     * Gera um token utilizando um gerador de números aleatórios criptograficamente seguro.
     *
     * @return Um token gerado aleatoriamente, codificado no formato Base64.
     */
	public static String generateToken() {
		// Cria um array para armazenar os bytes do token
        byte[] tokenBytes = new byte[24];

        // Gera bytes aleatórios usando um gerador de números aleatórios seguro
        new SecureRandom().nextBytes(tokenBytes);

        // Codifica os bytes gerados em uma string Base64 sem preenchimento
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
	}
}
