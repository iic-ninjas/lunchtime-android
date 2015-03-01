package com.iic.lunchtime.dal;

import com.iic.lunchtime.models.User;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ifeins on 3/1/15.
 */
public class UserDAO extends BaseCustomDAO<User, Integer> {
  public UserDAO(Class<User> dataClass) throws SQLException {
    super(dataClass);
  }

  public UserDAO(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
    super(connectionSource, dataClass);
  }

  public UserDAO(ConnectionSource connectionSource, DatabaseTableConfig<User> tableConfig) throws SQLException {
    super(connectionSource, tableConfig);
  }

  public User findOrCreateByEmail(User user) {
    SelectArg userEmailArg = new SelectArg(user.getEmail());
    List<User> users = queryForEq("email", userEmailArg);
    if (users.isEmpty()) {
      int id = create(user);
      return queryForId(id);
    } else {
      return users.get(0);
    }
  }
}
