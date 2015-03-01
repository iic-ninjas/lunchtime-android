package com.iic.lunchtime.models;

import com.iic.lunchtime.dal.LunchDAO;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 * Created by ifeins on 2/24/15.
 */
@DatabaseTable(tableName = "lunches", daoClass = LunchDAO.class)
public class Lunch {

  @ForeignCollectionField
  private ForeignCollection<Vote> votes;

  @DatabaseField(generatedId = true)
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

  public int getApiId() {
    return apiId;
  }

  public Date getDate() {
    return date;
  }

  public ForeignCollection<Vote> getVotes() {
    return votes;
  }

  public void setVotes(ForeignCollection<Vote> votes) {
    this.votes = votes;
  }
}
