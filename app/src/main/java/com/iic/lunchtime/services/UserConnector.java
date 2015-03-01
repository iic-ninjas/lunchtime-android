package com.iic.lunchtime.services;

import com.iic.lunchtime.api.LunchtimeAPI;
import com.iic.lunchtime.api.LunchtimeAPIManager;
import com.iic.lunchtime.converters.UserConverter;
import com.iic.lunchtime.dal.UserDAO;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.UserLoggedInEvent;
import com.iic.lunchtime.models.User;

/**
 * Created by ifeins on 3/1/15.
 */
public class UserConnector {

  private UserConverter userConverter;

  private UserDAO userDAO;

  private static User currentUser;

  public UserConnector(UserConverter userConverter, UserDAO userDAO) {
    this.userConverter = userConverter;
    this.userDAO = userDAO;
  }

  public User signInWithFacebook(String fbAccessToken) {
    LunchtimeAPI.Models.UserLoginParams params = new LunchtimeAPI.Models.UserLoginParams();
    params.access_token = fbAccessToken;
    LunchtimeAPI.Models.User apiUser = LunchtimeAPIManager.getInstance().signIn(params);

    User user = userDAO.findOrCreateByEmail(userConverter.toDatabaseModel(apiUser));
    setCurrentUser(user);

    AppEventBus.getInstance().post(new UserLoggedInEvent());

    return user;
  }

  public static synchronized User getCurrentUser() {
    return currentUser;
  }

  public static synchronized void setCurrentUser(User user) {
    currentUser = user;
  }
}
