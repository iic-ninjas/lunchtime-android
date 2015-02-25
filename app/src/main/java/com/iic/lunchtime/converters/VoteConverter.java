package com.iic.lunchtime.converters;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.models.User;
import com.iic.lunchtime.models.Vote;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Created by ifeins on 2/25/15.
 */
public class VoteConverter implements Converter<LunchtimeAPI.Models.Vote, Vote> {

  private final RuntimeExceptionDao<Restaurant, Integer> restaurantDAO;

  private final Lunch lunch;

  private final UserConverter userConverter;

  public VoteConverter(RuntimeExceptionDao<Restaurant, Integer> restaurantDAO, Lunch lunch) {
    this.restaurantDAO = restaurantDAO;
    this.lunch = lunch;
    this.userConverter = new UserConverter();
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
