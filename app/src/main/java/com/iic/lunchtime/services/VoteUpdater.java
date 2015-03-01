package com.iic.lunchtime.services;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.api.LunchtimeAPIManager;
import com.iic.lunchtime.converters.VoteConverter;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.models.Vote;
import com.j256.ormlite.dao.ForeignCollection;

/**
 * Class used to create and remove user's votes from a restaurant.
 * Created by ifeins on 2/28/15.
 */
public class VoteUpdater {

  private final Lunch lunch;

  private final VoteConverter voteConverter;

  public VoteUpdater(Lunch lunch, VoteConverter voteConverter) {
    this.lunch = lunch;
    this.voteConverter = voteConverter;
  }

  public void vote(Restaurant restaurant) {
    LunchtimeAPI.Models.Vote vote = new LunchtimeAPI.Models.Vote();
    vote.restaurant_id = restaurant.getApiId();

    vote = LunchtimeAPIManager.getInstance().createVote(lunch.getApiId(), vote);
    ForeignCollection<Vote> votes = lunch.getVotes();
    votes.add(voteConverter.toDatabaseModel(vote));
  }

  public void unvote(Restaurant restaurant) {

  }

}
