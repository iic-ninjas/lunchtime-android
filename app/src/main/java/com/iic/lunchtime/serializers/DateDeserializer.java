package com.iic.lunchtime.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ifeins on 2/25/15.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

  private final SimpleDateFormat dateFormat;

  public DateDeserializer() {
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  }

  @Override
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    String dateStr = json.getAsString();

    try {
      return dateFormat.parse(dateStr);
    } catch (ParseException e) {
      throw new RuntimeException("Could not parse date", e);
    }
  }
}
