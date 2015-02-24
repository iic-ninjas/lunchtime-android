package com.iic.lunchtime.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.serializers.RestaurantDeserializer;
import java.util.List;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ifeins on 2/24/15.
 */
public class LunchFetcherService extends IntentService {

  private static final String LOG_TAG = LunchFetcherService.class.getSimpleName();

  private static final String SERVICE_NAME = "LunchFetcherService";

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public LunchFetcherService() {
    super(SERVICE_NAME);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    fetchRestaurnts();
  }

  private void fetchRestaurnts() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Restaurant.class, new RestaurantDeserializer()).create();
    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        build();

    LunchtimeAPI api = restAdapter.create(LunchtimeAPI.class);
    List<Restaurant> restaurants = api.getRestaurants();
    Log.v(LOG_TAG, "Fetched " + restaurants.size() + " restaurants");
  }
}
