package com.iic.lunchtime.dal;

import com.iic.lunchtime.models.Restaurant;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.util.List;

/**
 * A DAO for the Restaurant model
 * Created by ifeins on 2/28/15.
 */
public class RestaurantDAO extends BaseCustomDAO<Restaurant, Integer> {
  public RestaurantDAO(Class<Restaurant> dataClass) throws SQLException {
    super(dataClass);
  }

  public RestaurantDAO(ConnectionSource connectionSource, Class<Restaurant> dataClass) throws SQLException {
    super(connectionSource, dataClass);
  }

  public RestaurantDAO(ConnectionSource connectionSource, DatabaseTableConfig<Restaurant> tableConfig)
      throws SQLException {
    super(connectionSource, tableConfig);
  }

  public void createIfNotExistByName(Restaurant restaurant) {
    SelectArg restaurantNameArg = new SelectArg(restaurant.getName());
    List<Restaurant> existingRestaurants = queryForEq("name", restaurantNameArg);
    if (existingRestaurants.isEmpty()) {
      create(restaurant);
    }
  }
}
