package com.iic.lunchtime.dal;

import com.iic.lunchtime.models.Lunch;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ifeins on 2/28/15.
 */
public class LunchDAO extends BaseCustomDAO<Lunch, Integer> {
  public LunchDAO(Class<Lunch> dataClass) throws SQLException {
    super(dataClass);
  }

  public LunchDAO(ConnectionSource connectionSource, Class<Lunch> dataClass) throws SQLException {
    super(connectionSource, dataClass);
  }

  public LunchDAO(ConnectionSource connectionSource, DatabaseTableConfig<Lunch> tableConfig) throws SQLException {
    super(connectionSource, tableConfig);
  }

  public void createIfNotExistsByDate(Lunch lunch) {
    List<Lunch> lunches = queryForEq("date", lunch.getDate());
    if (lunches.isEmpty()) {
      create(lunch);
    }
  }
}
