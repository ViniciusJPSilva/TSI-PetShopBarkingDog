package br.vjps.tsi.psbd.component;

import java.io.IOException;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.vjps.tsi.psbd.dao.DAO;
import br.vjps.tsi.psbd.dao.EmployeeDAO;
import br.vjps.tsi.psbd.model.Employee;
import jakarta.transaction.Transactional;

/**
 * Carregador de Dados Iniciais para o sistema, executado durante o evento de inicialização do contexto da aplicação.
 *
 * @author Vinícius J P Silva
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	 /**
     * Executa a lógica de carga de dados iniciais quando o contexto da aplicação é atualizado.
     *
     * @param event O evento de atualização do contexto da aplicação.
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Verifica se os dados já foram carregados para evitar duplicatas
        if (alreadyLoaded()) return;

        // Adiciona um funcionário administrador
        Employee admin = new Employee();
        admin.setName("Administrador");
        admin.setLogin("admin");
        admin.setPassword("admin");

        new DAO<Employee>(Employee.class).add(admin);
    }

    /**
     * Verifica se os dados iniciais já foram carregados no banco de dados.
     *
     * @return true se os dados já foram carregados, false caso contrário.
     */
    private boolean alreadyLoaded() {
    	// Consulta o banco de dados para verificar se já existe um funcionário com o login "admin"
    	try(EmployeeDAO employeeDAO = new EmployeeDAO()) {
    		if(employeeDAO.getByLogin("admin") != null)
    			return true;
    	} catch (IOException e) {
			e.printStackTrace();
		} 
        
        return false;
    }
}