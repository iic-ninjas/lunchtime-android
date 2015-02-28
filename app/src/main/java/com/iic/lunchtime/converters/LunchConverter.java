package com.iic.lunchtime.converters;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.dal.LunchDAO;
import com.iic.lunchtime.dal.RestaurantDAO;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Vote;
import com.j256.ormlite.dao.ForeignCollection;

/**
 * Created by ifeins on 2/25/15.
 */
public class LunchConverter implements Converter<LunchtimeAPI.Models.Lunch, Lunch> {

  private final LunchDAO lunchDAO;

  private final RestaurantDAO restaurantDAO;

  private final UserConverter userConverter;

  public LunchConverter(LunchDAO lunchDAO,
      RestaurantDAO restaurantDAO,
      UserConverter userConverter) {
    this.lunchDAO = lunchDAO;
    this.restaurantDAO = restaurantDAO;
    this.userConverter = userConverter;
  }

  @Override
  public Lunch toDatabaseModel(LunchtimeAPI.Models.Lunch apiModel) {
    Lunch lunch = new Lunch(apiModel.id, apiModel.date);
    VoteConverter voteConverter = new VoteConverter(lunch, restaurantDAO, userConverter);

    ForeignCollection<Vote> votes = lunchDAO.getEmptyForeignCollection("votes");
    for (LunchtimeAPI.Models.Vote vote : apiModel.votes) {
      votes.add(voteConverter.toDatabaseModel(vote));
    }
    lunch.setVotes(votes);

    return lunch;
  }

  @Override
  public LunchtimeAPI.Models.Lunch fromDatabaseModel(Lunch dbModel) {
    return null;
  }
}
