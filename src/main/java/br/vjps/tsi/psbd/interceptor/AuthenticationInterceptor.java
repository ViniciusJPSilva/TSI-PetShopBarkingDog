package br.vjps.tsi.psbd.interceptor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Interceptador de requisições para autenticação e controle de acesso a URIs específicas.
 * Implementa a interface HandlerInterceptor do Spring MVC.
 * 
 * @author Vinicius J P Silva
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	// URIs permitidas
	private static final Set<String> ALLOWED_URIS = new HashSet<>(),
			EMPLOYEE_URIS = new HashSet<>(),
			CLIENT_URIS = new HashSet<>();

    static {    	
    	// Permitidas
    	ALLOWED_URIS.add("client-login-page");
    	ALLOWED_URIS.add("client-login");
    	ALLOWED_URIS.add("client-confirm-registration");
    	ALLOWED_URIS.add("client-registration");
    	ALLOWED_URIS.add("client-register");
    	ALLOWED_URIS.add("client-create");
    	ALLOWED_URIS.add("employee-login-page");
    	ALLOWED_URIS.add("employee-login");
    	ALLOWED_URIS.add("message");
    	ALLOWED_URIS.add("404");
    	ALLOWED_URIS.add("error");
    	
    	// Cliente
    	CLIENT_URIS.add("client-menu");
    	CLIENT_URIS.add("dog-register");
    	CLIENT_URIS.add("dog-create");
    	CLIENT_URIS.add("client-logout");
    	CLIENT_URIS.add("client-change-data-page");
    	CLIENT_URIS.add("client-update");
    	CLIENT_URIS.add("schedule-service-page");
    	CLIENT_URIS.add("schedule-service");
    	CLIENT_URIS.add("shedule-service-create");
    	CLIENT_URIS.add("sheduled-services-open");
    	CLIENT_URIS.add("scheduled-service-cancel");
    	CLIENT_URIS.add("scheduled-services-finished");
    	
    	// Funcionário
        EMPLOYEE_URIS.add("employee-menu");
        EMPLOYEE_URIS.add("employee-logout");
        EMPLOYEE_URIS.add("service-type-register");
        EMPLOYEE_URIS.add("service-type-create");
        EMPLOYEE_URIS.add("service-type-table");
        EMPLOYEE_URIS.add("service-type-change-cost");
        EMPLOYEE_URIS.add("scheduled-services-data");
        EMPLOYEE_URIS.add("scheduled-services-pending");
        EMPLOYEE_URIS.add("scheduled-service-finish");
        EMPLOYEE_URIS.add("finished-services-between");
        EMPLOYEE_URIS.add("finished-services-list");
    }
	
    /**
     * Método executado antes do manuseio da requisição, realiza a autenticação e controle de acesso.
     *
     * @param request  A requisição HTTP.
     * @param response A resposta HTTP.
     * @param handler  O objeto que manipula a requisição.
     * @return true se a requisição deve ser processada, false se deve ser interrompida.
     * @throws Exception Exceção lançada em caso de erro.
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI(),
				redirect = "client-login-page";
		
		HttpSession session = request.getSession();

		if (ALLOWED_URIS.stream().anyMatch(uri::endsWith) || uri.contains("resources"))
            return true;
		
		if(EMPLOYEE_URIS.stream().anyMatch(uri::endsWith) && session.getAttribute("logged") != null)
			if(!(Boolean) session.getAttribute("isClient"))
				return true;
			else 
				redirect = "client-menu";
		
		if(CLIENT_URIS.stream().anyMatch(uri::endsWith) && session.getAttribute("logged") != null)
			if((Boolean) session.getAttribute("isClient"))
				return true;
			else 
				redirect = "employee-menu";

		response.sendRedirect(redirect);
		return false;
	}

}