package com.iic.lunchtime.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.converters.VoteConverter;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.models.Vote;
import com.iic.lunchtime.serializers.DateDeserializer;
import com.j256.ormlite.dao.ForeignCollection;
import java.util.Date;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Class used to create and remove user's votes from a restaurant.
 * Created by ifeins on 2/28/15.
 */
public class VoteUpdater {

  private final Lunch lunch;

  private final VoteConverter voteConverter;

  private LunchtimeAPI api;

  public VoteUpdater(Lunch lunch, VoteConverter voteConverter) {
    this.lunch = lunch;
    this.voteConverter = voteConverter;
    createAPIClient();
  }

  public void vote(Restaurant restaurant) {
    LunchtimeAPI.Models.Vote vote = new LunchtimeAPI.Models.Vote();
    vote.restaurant_id = restaurant.getApiId();

    vote = api.createVote(lunch.getApiId(), vote);
    ForeignCollection<Vote> votes = lunch.getVotes();
    votes.add(voteConverter.toDatabaseModel(vote));
  }

  public void unvote(Restaurant restaurant) {

  }

  private void createAPIClient() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        build();

    api = restAdapter.create(LunchtimeAPI.class);
  }
}
