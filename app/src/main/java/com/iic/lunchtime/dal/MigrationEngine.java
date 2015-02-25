package com.iic.lunchtime.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ifeins on 2/24/15.
 */
public class MigrationEngine {

  private static final String LOG_TAG = MigrationEngine.class.getSimpleName();

  private Context context;

  public MigrationEngine(Context context) {
    this.context = context;
  }

  public void migrate(SQLiteDatabase database, int oldVersion, int newVersion) {
    for (int i = oldVersion + 1; i <= newVersion; i++) {
      // execSQL can only receive one statement at a time, so readMigrationStatements returns an array of statements
      String[] statements = readMigrationStatements(i);
      for (String statement : statements) {
        database.execSQL(statement);
      }
    }
  }

  private String[] readMigrationStatements(int dbVersion) {
    InputStreamReader reader = null;
    try {
      String migrationPath = new StringBuilder("migrations/").append(dbVersion).append(".sql").toString();
      reader = new InputStreamReader(context.getAssets().open(migrationPath));
      String content = CharStreams.toString(reader).trim();
      return content.split(";");
    } catch (IOException e) {
      Log.e(LOG_TAG, e.getMessage(), e);
      throw new RuntimeException("Could not read migration for db version: " + dbVersion);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          Log.e(LOG_TAG, e.getMessage(), e);
          // not throwing exception so we were able to read the migration content
        }
      }
    }
  }
}
