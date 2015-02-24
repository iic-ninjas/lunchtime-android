package com.iic.lunchtime.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.models.Restaurant;
import java.util.List;
import retrofit.RestAdapter;

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
    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(LunchtimeAPI.API_BASE).build();
    LunchtimeAPI api = restAdapter.create(LunchtimeAPI.class);
    List<Restaurant> restaurants = api.getRestaurants();
    Log.v(LOG_TAG, "Fetched " + restaurants.size() + " restaurants");
  }
}
