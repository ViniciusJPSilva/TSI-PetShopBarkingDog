package br.vjps.tsi.psbd.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Classe responsável por prover métodos estáticos para realizar operações úteis.
 * 
 * @author Vinicius J P Silva
 * 
 */
public final class Utility {

	// Impede a instanciação de objetos desta classe.
	private Utility() {}
	
	/**
     * Converte um objeto Calendar em uma string formatada no formato "dd/MM/yyyy".
     *
     * @param date O objeto Calendar a ser convertido.
     * 
     * @return Uma representação legível da data no formato "dd/MM/yyyy".
     */
	public static String calendarToReadableString(Calendar date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date.getTime());
	}
	
    /**
     * Converte uma string no formato "dd/MM/yyyy" para um objeto Calendar.
     *
     * @param dateString A string a ser convertida.
     * 
     * @return Um objeto Calendar representando a data.
     * 
     * @throws ParseException Se ocorrer um erro durante a análise da string.
     */
    public static Calendar stringToCalendar(String dateString) {
        try {
            Calendar calendar = getTodayCalendar();
            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(dateString));
            return calendar;
        } catch (ParseException e) {
        	return null;
		}
    }
	
	/**
	 * Converte um objeto Calendar em uma string formatada no formato "HH:mm dd/MM/yyyy".
	 *
	 * @param date O objeto Calendar a ser convertido.
	 * 
	 * @return Uma representação legível da data e hora no formato "HH:mm dd/MM/yyyy".
	 */
	public static String calendarToReadableStringDateAndHour(Calendar date) {
	    return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date.getTime());
	}
	
	/**
	 * Obtém uma data futura a partir da data atual, adicionando o número especificado de dias.
	 *
	 * @param daysPassed O número de dias a serem adicionados à data atual.
	 * @return Um objeto Calendar representando a data futura.
	 */
	public static Calendar getFutureDate(int daysPassed) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, daysPassed);
	    return calendar;
	}
	
	/**
	 * Converte um objeto Timestamp para um objeto Calendar configurado no fuso horário UTC.
	 *
	 * @param timestamp O objeto Timestamp a ser convertido.
	 * @return Um objeto Calendar configurado com o valor da data/hora do Timestamp no fuso horário UTC.
	 */
	public static Calendar timestampToCalendar(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(timestamp.getTime());
        
        return calendar;
	}

	/**
	 * Obtém um objeto Calendar configurado para representar o início do dia de hoje,
	 * com hora, minuto, segundo e milissegundo definidos como zero.
	 *
	 * @return Um objeto Calendar configurado para o início do dia de hoje.
	 */
	public static Calendar getTodayCalendar() {
		Calendar today = Calendar.getInstance();

		// Define a hora, minuto, segundo e milissegundo como zero para o início do dia de hoje
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		return today;
	}
	
} // class Utility
