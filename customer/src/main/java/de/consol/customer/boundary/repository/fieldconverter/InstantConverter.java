package de.consol.customer.boundary.repository.fieldconverter;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InstantConverter implements AttributeConverter<Instant, Date> {

  @Override
  public Date convertToDatabaseColumn(final Instant entityAttribute) {
    return Optional.ofNullable(entityAttribute)
        .map(Date::from)
        .orElse(null);
  }

  @Override
  public Instant convertToEntityAttribute(final Date databaseColumn) {
    return Optional.ofNullable(databaseColumn)
        .map(Date::toInstant)
        .orElse(null);
  }
}