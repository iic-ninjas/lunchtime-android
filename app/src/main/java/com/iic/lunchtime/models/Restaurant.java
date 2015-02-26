package com.iic.lunchtime.models;

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
  private String localizedName;

  @DatabaseField(columnName = "logo_url")
  private String logoUrl;

  @DatabaseField(canBeNull = false)
  private String street;

  @DatabaseField(canBeNull = false)
  private String city;

  @DatabaseField(canBeNull = false)
  private float latitude;

  @DatabaseField(canBeNull = false)
  private float longitude;

  public Restaurant(int id, String name, String localizedName, String logoUrl, String street, String city,
      float latitude, float longitude) {
    this.id = id;
    this.name = name;
    this.localizedName = localizedName;
    this.logoUrl = logoUrl;
    this.street = street;
    this.city = city;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Restaurant() {
    // needed for ORMLite
  }

  public String getName() {
    return name;
  }

  public String getLocalizedName() {
    return localizedName;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }
}
