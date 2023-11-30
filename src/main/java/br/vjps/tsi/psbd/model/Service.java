package br.vjps.tsi.psbd.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import br.vjps.tsi.psbd.enumeration.ServiceStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;

/**
 * Classe que representa um Serviço (agendamento).
 *  
 * @author Vinicius J P Silva
 * 
 * @see ServiceType
 * @see Dog
 * @see ServiceStatus
 * 
 * @see Calendar
 */
@Entity
public class Service {
	public static final int NUMBER_OF_SERVICES_FOR_DISCOUNT = 3;
	public static final Double DISCOUNT_PERCENTAGE = 0.1;
	
	@Id
	@SequenceGenerator(
			name = "service_id",
			sequenceName = "service_seq",
			allocationSize = 1
		)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id")
	private Long id;
	
	@DateTimeFormat(pattern="dd/MM/yyy")
	private Calendar scheduledDate;
	
	/**
	 * Poderão ser selecionados mais de um serviço, contudo não podem haver repetições.
	 */
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<ServiceType> serviceTypes = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Dog dog;
	
	@Enumerated(EnumType.STRING)
	private ServiceStatus status;
	
	@DecimalMin(value = "0.0", message = "O custo do serviço deve ser maior ou igual à 0 (zero) reais.")
	private Double total;
	
	@DateTimeFormat(pattern="dd/MM/yyy")
	private Calendar releaseDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Calendar scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Set<ServiceType> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(Set<ServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Calcula o custo total dos serviços selecionados, aplicando desconto se o número de serviços atender ou exceder o limite para desconto.
	 *
	 * @return O custo total dos serviços, considerando qualquer desconto aplicável.
	 */
	public Double calculateTotal() {
		total = serviceTypes.stream().mapToDouble(ServiceType::getCost).sum();
		if(serviceTypes.size() >= NUMBER_OF_SERVICES_FOR_DISCOUNT) total -= total * DISCOUNT_PERCENTAGE;
		return total;
	}
}
