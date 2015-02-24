package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "lunches")
public class Lunch {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField(canBeNull = false)
  private Date date;

}
