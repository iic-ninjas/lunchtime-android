package com.iic.lunchtime.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.iic.lunchtime.models.Restaurant;
import java.lang.reflect.Type;

/**
 * Created by ifeins on 2/24/15.
 */
public class RestaurantDeserializer implements JsonDeserializer<Restaurant> {

  @Override
  public Restaurant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject result = json.getAsJsonObject();
    JsonElement location = result.get("location");

    result.addProperty("street", location.getAsJsonObject().get("street").getAsString());
    result.addProperty("city", location.getAsJsonObject().get("city").getAsString());
    result.addProperty("latitude", location.getAsJsonObject().get("latitude").getAsFloat());
    result.addProperty("longitude", location.getAsJsonObject().get("longitude").getAsFloat());

    return new Gson().fromJson(result, Restaurant.class);
  }

}
