package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "users")
public class User {

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(columnName = "api_id")
  private int apiId;

  @DatabaseField
  private String firstName;

  @DatabaseField
  private String lastName;

  @DatabaseField(unique = true)
  private String email;

  @DatabaseField
  private String avatarUrl;

  public User(int apiId, String firstName, String lastName, String email, String avatarUrl) {
    this.apiId = apiId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.avatarUrl = avatarUrl;
  }

  public User() {
    // needed for ORMLite
  }

}
