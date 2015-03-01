package com.iic.lunchtime.models;

import com.iic.lunchtime.dal.RestaurantDAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "restaurants", daoClass = RestaurantDAO.class)
public class Restaurant {

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(columnName = "api_id")
  private int apiId;

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

  public Restaurant(int apiId, String name, String localizedName, String logoUrl, String street, String city,
      float latitude, float longitude) {
    this.apiId = apiId;
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

  public int getId() {
    return id;
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

  public int getApiId() {
    return apiId;
  }
}
