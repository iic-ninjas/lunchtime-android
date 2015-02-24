package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "votes")
public class Vote {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField(canBeNull = false, foreign = true)
  private Lunch lunch;

  @DatabaseField(canBeNull = false, foreign = true)
  private User user;

  @DatabaseField(canBeNull = false, foreign = true)
  private Restaurant restaurant;

}
