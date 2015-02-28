package com.iic.lunchtime.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "lunches")
public class Lunch {

  @ForeignCollectionField
  ForeignCollection<Vote> votes;

  @DatabaseField(id = true)
  private int id;

  @DatabaseField(columnName = "api_id")
  private int apiId;

  @DatabaseField(canBeNull = false)
  private Date date;

  public Lunch(int apiId, Date date) {
    this.apiId = apiId;
    this.date = date;
  }

  public Lunch() {
    // for ORMLITE
  }

  public void setVotes(ForeignCollection<Vote> votes) {
    this.votes = votes;
  }
}
