package com.iic.lunchtime.converters;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.models.User;

/**
 * Created by ifeins on 2/25/15.
 */
public class UserConverter implements Converter<LunchtimeAPI.Models.User, User> {
  @Override
  public User toDatabaseModel(LunchtimeAPI.Models.User apiModel) {
    return new User(apiModel.id, apiModel.first_name, apiModel.last_name, apiModel.email, apiModel.avatar_url);
  }

  @Override
  public LunchtimeAPI.Models.User fromDatabaseModel(User dbModel) {
    return null;
  }
}
