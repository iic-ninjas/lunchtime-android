package com.iic.lunchtime.dal;

import com.iic.lunchtime.models.Lunch;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

  public Lunch getTodayLunch() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date now = Calendar.getInstance().getTime();
    try {
      Date today = dateFormat.parse(dateFormat.format(now));
      PreparedQuery<Lunch> query = queryBuilder().where().eq("date", today).prepare();
      return queryForFirst(query);
    } catch (ParseException|SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
