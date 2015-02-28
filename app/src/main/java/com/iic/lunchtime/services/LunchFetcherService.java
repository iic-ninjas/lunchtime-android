package com.iic.lunchtime.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.converters.LunchConverter;
import com.iic.lunchtime.converters.RestaurantConverter;
import com.iic.lunchtime.converters.UserConverter;
import com.iic.lunchtime.dal.LunchDAO;
import com.iic.lunchtime.dal.LunchtimeDBHelper;
import com.iic.lunchtime.dal.RestaurantDAO;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.LunchFetchedEvent;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.serializers.DateDeserializer;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.misc.TransactionManager;
import com.squareup.otto.Produce;
import java.sql.SQLException;
import java.util.Date;
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

  private final UserConverter userConverter;

  private LunchtimeAPI api;

  private RestaurantConverter restaurantConverter;

  private String lastFetchDate;


  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public LunchFetcherService() {
    super(SERVICE_NAME);
    createAPIClient();

    this.restaurantConverter = new RestaurantConverter();
    this.userConverter = new UserConverter();
    AppEventBus.getInstance().register(this);
  }

  @Produce
  public LunchFetchedEvent produceLunchFetchEvent() {
    if (lastFetchDate != null) {
      Log.d(LOG_TAG, "produceLunchFetchEvent called after data was fetched");
      return new LunchFetchedEvent(lastFetchDate);
    } else {
      Log.d(LOG_TAG, "produceLunchFetchEvent called before data was fetched");
      return null;
    }
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    LunchtimeDBHelper dbHelper = OpenHelperManager.getHelper(getBaseContext(), LunchtimeDBHelper.class);

    try {
      fetchRestaurants(dbHelper);
      fetchLunch(intent.getStringExtra(EXTRA_LUNCH_DATE), dbHelper);
    } finally {
      OpenHelperManager.releaseHelper();
    }
  }

  private void createAPIClient() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        build();

    api = restAdapter.create(LunchtimeAPI.class);
  }

  private void fetchLunch(String date, LunchtimeDBHelper dbHelper) {
    LunchDAO lunchDAO = dbHelper.getDao(Lunch.class);
    RestaurantDAO restaurantDAO = dbHelper.getDao(Restaurant.class);
    LunchConverter converter = new LunchConverter(lunchDAO, restaurantDAO, this.userConverter);

    if (date.equals("today")) {
      LunchtimeAPI.Models.Lunch lunch = api.getTodayLunch();
      lunchDAO.createIfNotExistsByDate(converter.toDatabaseModel(lunch));
    }

    lastFetchDate = date;
    AppEventBus.getInstance().post(new LunchFetchedEvent(lastFetchDate));
    Log.d(LOG_TAG, "Finished fetching lunch");
  }

  private void fetchRestaurants(final LunchtimeDBHelper dbHelper) {
    final List<LunchtimeAPI.Models.Restaurant> restaurants = api.getRestaurants();

    try {
      // wrapping in transaction for performance
      TransactionManager.callInTransaction(dbHelper.getConnectionSource(), new Callable<Void>() {
        @Override
        public Void call() throws Exception {
          RestaurantDAO dao = dbHelper.getDao(Restaurant.class);
          for (LunchtimeAPI.Models.Restaurant restaurant : restaurants) {
            dao.createIfNotExistByName(restaurantConverter.toDatabaseModel(restaurant));
          }
          Log.d(LOG_TAG, "Finished importing " + restaurants.size() + " restaurants");
          return null;
        }
      });
    } catch (SQLException e) {
      throw new RuntimeException("Could not import restaurants", e);
    }
  }
}
