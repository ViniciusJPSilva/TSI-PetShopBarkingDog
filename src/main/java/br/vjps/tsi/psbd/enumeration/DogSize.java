package br.vjps.tsi.psbd.enumeration;

/**
 * Enumeração que representa os portes de um cachorro.
 * 
 * @author Vinicius J P Silva
 */
public enum DogSize {
	SMALL("Pequeno", "S"),
	MEDIUM("Médio", "M"),
	LARGE("Grande", "L");
	
	private final String description;
	private final String acronym;
	
	DogSize(String description, String acronym) {
		this.description = description;
		this.acronym = acronym;
	}

	public String getDescription() {
		return description;
	}

	public String getAcronym() {
		return acronym;
	}
	
	/**
	 * Obtém um objeto do tipo DogSize com base na sigla (acrônimo) especificada.
	 *
	 * @param acronym A sigla (acrônimo) do tamanho do cão a ser buscado.
	 * @return O DogSize correspondente à sigla fornecida ou null se não encontrado.
	 */
	public static DogSize getByAcronym(String acronym) {
		for(DogSize dogSize : DogSize.values())
			if(dogSize.acronym.equalsIgnoreCase(acronym))
				return dogSize;
		return null;
	}

	/**
	 * Obtém um objeto do tipo DogSize com base na descrição especificada.
	 *
	 * @param description A descrição do tamanho do cão a ser buscado.
	 * @return O DogSize correspondente à descrição fornecida ou null se não encontrado.
	 */
	public static DogSize getByDescription(String description) {
		for(DogSize dogSize : DogSize.values())
			if(dogSize.description.equalsIgnoreCase(description))
				return dogSize;
		return null;
	}
}
