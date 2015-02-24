package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "tags")
public class Tag {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField(canBeNull = false)
  private String name;

}
