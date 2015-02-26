package com.iic.lunchtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.iic.lunchtime.adapters.RestaurantsListAdapter;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.LunchFetchedEvent;
import com.squareup.otto.Subscribe;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestaurantsListFragment extends Fragment {

  private static final String LOG_TAG = RestaurantsListFragment.class.getSimpleName();

  private ListView listView;

  private RestaurantsListAdapter listAdapter;

  public RestaurantsListFragment() {
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
