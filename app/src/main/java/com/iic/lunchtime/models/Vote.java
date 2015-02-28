package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "votes")
public class Vote {

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(columnName = "api_id")
  private int apiId;

  @DatabaseField(canBeNull = false, foreign = true)
  private Lunch lunch;

  @DatabaseField(canBeNull = false, foreign = true)
  private User user;

  @DatabaseField(canBeNull = false, foreign = true)
  private Restaurant restaurant;

  public Vote() {
    // no-arg ctor for ORMLite
  }

  public Vote(int apiId, Lunch lunch, User user, Restaurant restaurant) {
    this.apiId = apiId;
    this.lunch = lunch;
    this.user = user;
    this.restaurant = restaurant;
  }
}
