package com.iic.lunchtime.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by ifeins on 2/24/15.
 */
public class LunchtimeDBHelper extends OrmLiteSqliteOpenHelper {

  private static final String DB_NAME = "lunchtime.db";

  private static final int DB_VERSION = 2;

  private final MigrationEngine migrationEngine;

  public LunchtimeDBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    this.migrationEngine = new MigrationEngine(context);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    migrationEngine.migrate(database, 0, 1);
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    migrationEngine.migrate(database, oldVersion, newVersion);
  }
}
