package com.iic.lunchtime.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ifeins on 2/24/15.
 */
public class LunchFetcher extends IntentService {

  private static final String SERVICE_NAME = "LunchFetcher";

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public LunchFetcher() {
    super(SERVICE_NAME);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    
  }
}
