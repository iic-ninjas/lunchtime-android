package com.iic.lunchtime.api;

import java.util.Date;
import java.util.List;
import retrofit.http.GET;

/**
 * Created by ifeins on 2/24/15.
 */
public interface LunchtimeAPI {

  public static final String API_BASE = "http://iic-lunchtime.herokuapp.com";

  @GET("/restaurants.json")
  List<Models.Restaurant> getRestaurants();

  @GET("/lunches/today.json")
  Models.Lunch getTodayLunch();

  public static class Models {
    public static class Restaurant {
      public int id;
      public String name;
      public String localized_name;
      public String logo_url;
      public Location location;

      public static class Location {
        public String street;
        public String city;
        public float latitude;
        public float longitude;
      }
    }

    public static class Lunch {
      public int id;
      public Date date;
      public List<Vote> votes;
    }

    public static class User {
      public int id;
      public String first_name;
      public String last_name;
      public String email;
      public String avatar_url;
    }

    public static class Vote {
      public int id;
      public int restaurant_id;
      public User user;
    }
  }
}
