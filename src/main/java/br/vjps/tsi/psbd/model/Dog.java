package br.vjps.tsi.psbd.model;

import br.vjps.tsi.psbd.enumeration.DogSize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; 

/**
 * Classe que representa um Cachorro.
 *  
 * @author Vinicius J P Silva
 * 
 * @see Client
 * @see DogSize
 */
@Entity
public class Dog {

	@Id
	@SequenceGenerator(
			name = "dog_id",
			sequenceName = "dog_seq",
			allocationSize = 1
		)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dog_id")
	private Long id;
	
	@NotBlank
	@Size(min = 2, message = "O nome do cão deve ter pelo menos 2 caracteres.")
	private String name;
	
	@NotBlank
	@Size(min = 2, message = "A raça do cão deve ter pelo menos 2 caracteres.")
	private String breed; // Raça

	@Enumerated(EnumType.STRING)
	private DogSize size;
	
	@ManyToOne
    @JoinColumn(name = "client")
    private Client client;

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

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public DogSize getSize() {
		return size;
	}

	public void setSize(String description) {
		this.size = DogSize.getByDescription(description);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
		
}
