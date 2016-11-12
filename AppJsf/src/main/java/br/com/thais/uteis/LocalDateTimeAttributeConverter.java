package br.com.thais.uteis;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe para conversão de atributos LocalDateTime e Timestamp
 * 
 * @author Thaís
 * @since 12 de nov de 2016 - 17:46:55
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	/*
	 * Converte um valor LocalDateTime passado por parâmetro em Timestamp antes
	 * de persistir no banco (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.
	 * Object)
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		if (localDateTime != null)
			return Timestamp.valueOf(localDateTime);
		return null;
	}

	/*
	 * Converte um valor Timestamp passado por parâmetro em LocalDateTime quando
	 * captura o dado do banco para a entidade. (non-Javadoc)
	 * 
	 * @see
	 * javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.
	 * Object)
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		if (timestamp != null)
			return timestamp.toLocalDateTime();
		return null;
	}
}
