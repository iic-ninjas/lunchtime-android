package com.iic.lunchtime.converters;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.dal.RestaurantDAO;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.models.User;
import com.iic.lunchtime.models.Vote;

/**
 * Created by ifeins on 2/25/15.
 */
public class VoteConverter implements Converter<LunchtimeAPI.Models.Vote, Vote> {

  private final RestaurantDAO restaurantDAO;

  private final Lunch lunch;

  private final UserConverter userConverter;

  public VoteConverter(Lunch lunch, RestaurantDAO restaurantDAO, UserConverter userConverter) {
    this.restaurantDAO = restaurantDAO;
    this.lunch = lunch;
    this.userConverter = userConverter;
  }

  @Override
  public Vote toDatabaseModel(LunchtimeAPI.Models.Vote apiModel) {
    Restaurant restaurant = restaurantDAO.queryForId(apiModel.restaurant_id);
    User user = this.userConverter.toDatabaseModel(apiModel.user);
    return new Vote(apiModel.id, this.lunch, user, restaurant);
  }

  @Override
  public LunchtimeAPI.Models.Vote fromDatabaseModel(Vote dbModel) {
    return null;
  }
}
