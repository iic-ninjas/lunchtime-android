package com.iic.lunchtime.converters;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.models.Restaurant;

/**
 * Created by ifeins on 2/25/15.
 */
public class RestaurantConverter implements Converter<LunchtimeAPI.Models.Restaurant, Restaurant> {
  @Override
  public Restaurant toDatabaseModel(LunchtimeAPI.Models.Restaurant apiModel) {
    return new Restaurant(apiModel.id, apiModel.name, apiModel.localized_name,
        apiModel.logo_url, apiModel.street, apiModel.city, apiModel.latitude, apiModel.longitude);
  }

  @Override
  public LunchtimeAPI.Models.Restaurant fromDatabaseModel(Restaurant dbModel) {
    return null;
  }
}
