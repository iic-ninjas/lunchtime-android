package com.iic.lunchtime;

import android.app.Application;
import android.content.Intent;
import com.iic.lunchtime.api.LunchtimeAPIManager;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.UserLoggedInEvent;
import com.iic.lunchtime.services.LunchFetcherService;
import com.squareup.otto.Subscribe;

/**
 * Created by ifeins on 2/24/15.
 */
public class LunchtimeApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AppEventBus.getInstance().register(this);

    Intent intent = new Intent(this, LunchFetcherService.class);
    intent.putExtra(LunchFetcherService.EXTRA_LUNCH_DATE, "today");
    startService(intent);
  }

  @Subscribe
  public void onUserLoggedIn(UserLoggedInEvent event) {
    // resetting so next time it will be created with user header
    LunchtimeAPIManager.reset();
  }
}
