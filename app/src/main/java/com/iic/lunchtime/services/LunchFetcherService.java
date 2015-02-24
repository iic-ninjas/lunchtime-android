package com.iic.lunchtime.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.dal.LunchtimeDBHelper;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.serializers.RestaurantDeserializer;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.misc.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ifeins on 2/24/15.
 */
public class LunchFetcherService extends IntentService {

  public static final String EXTRA_LUNCH_DATE = "com.iic.lunchtime.extras.lunch_date";

  private static final String LOG_TAG = LunchFetcherService.class.getSimpleName();

  private static final String SERVICE_NAME = "LunchFetcherService";

  private LunchtimeAPI api;

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public LunchFetcherService() {
    super(SERVICE_NAME);
    createAPIClient();
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    fetchRestaurnts();
    fetchLunch(intent.getStringExtra(EXTRA_LUNCH_DATE));
  }

  private void createAPIClient() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Restaurant.class, new RestaurantDeserializer()).create();
    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        build();

    api = restAdapter.create(LunchtimeAPI.class);
  }

  private void fetchLunch(String date) {
    if (date.equals("today")) {
      Lunch lunch = api.getTodayLunch();
    }
  }

  private void fetchRestaurnts() {
    final List<Restaurant> restaurants = api.getRestaurants();
    final LunchtimeDBHelper dbHelper = OpenHelperManager.getHelper(getBaseContext(), LunchtimeDBHelper.class);

    try {
      TransactionManager.callInTransaction(dbHelper.getConnectionSource(), new Callable<Void>() {
        @Override
        public Void call() throws Exception {
          RuntimeExceptionDao<Restaurant, ?> dao = dbHelper.getRuntimeExceptionDao(Restaurant.class);
          for (Restaurant restaurant : restaurants) {
            dao.createIfNotExists(restaurant);
          }

          return null;
        }
      });
    } catch (SQLException e) {
      Log.e(LOG_TAG, e.getMessage(), e);
    } finally {
      OpenHelperManager.releaseHelper();
    }
  }
}
