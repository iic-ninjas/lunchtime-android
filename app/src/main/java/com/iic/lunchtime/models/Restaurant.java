package com.iic.lunchtime.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "restaurants")
public class Restaurant {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField(canBeNull = false, unique = true)
  private String name;

  @DatabaseField(columnName = "localized_name", canBeNull = false)
  @SerializedName("localized_name")
  private String localizedName;

  @DatabaseField(columnName = "logo_url")
  @SerializedName("logo_url")
  private String logoUrl;

  @DatabaseField(canBeNull = false)
  private String street;

  @DatabaseField(canBeNull = false)
  private String city;

  @DatabaseField(canBeNull = false)
  private float latitude;

  @DatabaseField(canBeNull = false)
  private float longitude;

}
