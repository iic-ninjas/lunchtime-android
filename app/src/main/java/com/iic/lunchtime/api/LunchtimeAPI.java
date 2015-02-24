package com.iic.lunchtime.api;

import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import java.util.List;
import retrofit.http.GET;

/**
 * Created by ifeins on 2/24/15.
 */
public interface LunchtimeAPI {

  public static final String API_BASE = "http://iic-lunchtime.herokuapp.com";

  @GET("/restaurants.json")
  List<Restaurant> getRestaurants();

  @GET("/lunches/today.json")
  Lunch getTodayLunch();
}
