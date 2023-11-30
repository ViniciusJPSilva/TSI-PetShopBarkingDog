package br.vjps.tsi.psbd.model;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/**
 * Classe que representa um Cliente.
 * 
 * @author Vinicius J P Silva
 * 
 * @see Calendar
 * 
 */
@Entity
@Table(name = Client.TABLE_NAME)
public class Client {

	public static final String TABLE_NAME = "client", EMAIL_COLUMN_NAME = "email", PASSWD_COLUMN_NAME = "password";

	@Id
	@SequenceGenerator(name = "client_id", sequenceName = "client_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id")
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String cpf;

	@NotBlank(message = "O nome é um campo obrigatório.")
	@Size(min = 5, message = "O nome do cliente deve ter pelo menos 5 caracteres.")
	private String name;

	@NotBlank
	@Column(name = EMAIL_COLUMN_NAME, unique = true)
	private String email;

	@Column(name = PASSWD_COLUMN_NAME)
	@Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres.")
	private String password;

	@NotBlank
	private String phone;

	@Past(message = "Forneça uma data válida.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar birthDate;
	
	private String token;
	
    private boolean confirmed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Calendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate) {
		this.birthDate = birthDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
