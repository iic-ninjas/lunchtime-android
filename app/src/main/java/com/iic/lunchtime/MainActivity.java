package com.iic.lunchtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.iic.lunchtime.adapters.RestaurantsListAdapter;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.LunchFetchedEvent;
import com.squareup.otto.Subscribe;

public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, new PlaceholderFragment())
          .commit();
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

    private ListView listView;

    private RestaurantsListAdapter listAdapter;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      listView = (ListView) rootView.findViewById(R.id.listView);
      listAdapter = new RestaurantsListAdapter(getActivity());
      listView.setAdapter(listAdapter);

      AppEventBus.getInstance().register(this);

      return rootView;
    }

    @Override
    public void onDestroyView() {
      AppEventBus.getInstance().unregister(this);

      super.onDestroyView();
    }

    @Subscribe
    public void onLunchFetched(LunchFetchedEvent event) {
      if (event != null) {
        Log.d(LOG_TAG, "onLunchFetched called after data was fetched");
        listAdapter.notifyDataSetChanged();
      } else {
        Log.d(LOG_TAG, "onLunchFetched called before data was fetched");
      }
    }
  }
}
