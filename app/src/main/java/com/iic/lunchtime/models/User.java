package com.iic.lunchtime.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "users")
public class User {

  @DatabaseField(id = true)
  private int id;

  @DatabaseField
  private String firstName;

  @DatabaseField
  private String lastName;

  @DatabaseField(unique = true)
  private String email;

  @DatabaseField
  private String avatarUrl;

  public User(int id, String firstName, String lastName, String email, String avatarUrl) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.avatarUrl = avatarUrl;
  }

  public User() {
    // needed for ORMLite
  }

}
