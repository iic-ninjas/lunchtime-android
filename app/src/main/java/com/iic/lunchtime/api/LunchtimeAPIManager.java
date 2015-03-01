package com.iic.lunchtime.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.serializers.DateDeserializer;
import com.iic.lunchtime.services.UserConnector;
import java.util.Date;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ifeins on 3/1/15.
 */
public class LunchtimeAPIManager {

  private static LunchtimeAPI instance;

  public synchronized static LunchtimeAPI getInstance() {
    if (instance == null) {
      instance = createAPIClient();
    }

    return instance;
  }

  public synchronized static void reset() {
    instance = null;
  }

  private static LunchtimeAPI createAPIClient() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

    RequestInterceptor interceptor = new RequestInterceptor() {
      @Override
      public void intercept(RequestFacade request) {
        if (UserConnector.getCurrentUser() != null) {
          String userApiId = Integer.toString(UserConnector.getCurrentUser().getApiId());
          request.addHeader(LunchtimeAPI.USER_HEADER, userApiId);
        }
      }
    };

    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        setRequestInterceptor(interceptor).
        build();
    return restAdapter.create(LunchtimeAPI.class);
  }

}
