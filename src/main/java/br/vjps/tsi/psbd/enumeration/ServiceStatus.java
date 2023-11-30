package br.vjps.tsi.psbd.enumeration;

/**
 * Enumeração que representa os possíveis status de um Serviço.
 * 
 * @author Vinicius J P Silva
 */
public enum ServiceStatus {
	OPEN("Em aberto"),
	FINISHED("Finalizado"),
	CANCELED("Cancelado");
	
	private String description;
	
	private ServiceStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Obtém um objeto do tipo ServiceStatus com base na descrição especificada.
	 *
	 * @param description A descrição do status de serviço a ser buscado.
	 * @return O ServiceStatus correspondente à descrição fornecida ou null se não encontrado.
	 */
	public static ServiceStatus getByDescription(String description) {
		for(ServiceStatus serviceStatus : ServiceStatus.values())
			if(serviceStatus.description.equalsIgnoreCase(description))
				return serviceStatus;
		return null;
	}
}
