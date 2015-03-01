package com.iic.lunchtime.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iic.lunchtime.serializers.DateDeserializer;
import java.util.Date;
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

  private static LunchtimeAPI createAPIClient() {
    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
    RestAdapter restAdapter = new RestAdapter.Builder().
        setEndpoint(LunchtimeAPI.API_BASE).
        setConverter(new GsonConverter(gson)).
        build();
    return restAdapter.create(LunchtimeAPI.class);
  }

}
