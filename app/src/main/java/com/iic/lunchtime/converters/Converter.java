package com.iic.lunchtime.converters;

/**
 * Converter between api model to database model
 * Created by ifeins on 2/25/15.
 */
public interface Converter<T1, T2> {
  T2 toDatabaseModel(T1 apiModel);
  T1 fromDatabaseModel(T2 dbModel);
}
