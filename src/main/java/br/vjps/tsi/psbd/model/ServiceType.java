package br.vjps.tsi.psbd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Classe que representa um tipo de serviço.
 *  
 * @author Vinicius J P Silva
 */
@Entity
public class ServiceType {
	
	@Id
	@SequenceGenerator(
			name = "service_type_id",
			sequenceName = "service_type_seq",
			allocationSize = 1
		)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_type_id")
	private Long id;
	
	@NotBlank
	@Size(min = 3, message = "O nome do serviço deve ter pelo menos 3 caracteres.")
	private String name;
	
	@NotNull
	@DecimalMin(value = "0.01", message = "O custo do serviço deve ser maior que 0 (zero) reais.")
	private Double cost; // Valor - Custo

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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
}
