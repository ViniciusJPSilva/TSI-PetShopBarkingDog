package br.vjps.tsi.psbd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Classe que representa um Funcionário.
 *  
 * @author Vinicius J P Silva
 */
@Entity
public class Employee {

	@Id
	@SequenceGenerator(
			name = "employee_id",
			sequenceName = "employee_seq",
			allocationSize = 1
		)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
	private Long id;
	
	@NotBlank(message = "O nome é um campo obrigatório.")
	@Size(min = 5, message = "O nome do funcionário deve ter pelo menos 5 caracteres.")
	private String name;
	
	@NotBlank(message = "O login é um campo obrigatório.")
	@Size(min = 5, message = "O login do funcionário deve ter pelo menos 5 caracteres.")
	@Column(unique = true)
	private String login;
	
	@Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres.")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
