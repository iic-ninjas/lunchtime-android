package com.iic.lunchtime.events;

/**
 * Created by ifeins on 2/26/15.
 */
public class LunchFetchedEvent {

  private String date;

  public LunchFetchedEvent(String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }
}
