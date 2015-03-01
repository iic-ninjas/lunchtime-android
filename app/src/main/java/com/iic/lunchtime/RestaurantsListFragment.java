package com.iic.lunchtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.iic.lunchtime.adapters.RestaurantsListAdapter;
import com.iic.lunchtime.converters.UserConverter;
import com.iic.lunchtime.converters.VoteConverter;
import com.iic.lunchtime.dal.LunchDAO;
import com.iic.lunchtime.dal.LunchtimeDBHelper;
import com.iic.lunchtime.dal.RestaurantDAO;
import com.iic.lunchtime.events.AppEventBus;
import com.iic.lunchtime.events.LunchFetchedEvent;
import com.iic.lunchtime.models.Lunch;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.services.VoteUpdater;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.otto.Subscribe;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestaurantsListFragment extends Fragment {

  private static final String LOG_TAG = RestaurantsListFragment.class.getSimpleName();

  private ListView listView;

  private RestaurantsListAdapter listAdapter;

  private LunchtimeDBHelper dbHelper;

  public RestaurantsListFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    dbHelper = OpenHelperManager.getHelper(getActivity(), LunchtimeDBHelper.class);

    listView = (ListView) rootView.findViewById(R.id.listView);
    listAdapter = createAdapter();
    listView.setAdapter(listAdapter);

    AppEventBus.getInstance().register(this);

    return rootView;
  }

  @Override
  public void onDestroyView() {
    OpenHelperManager.releaseHelper();
    dbHelper = null;

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

  private RestaurantsListAdapter createAdapter() {
    RestaurantDAO dao = dbHelper.getDao(Restaurant.class);
    LunchDAO lunchDAO = dbHelper.getDao(Lunch.class);
    Lunch lunch = lunchDAO.getTodayLunch();
    UserConverter userConverter = new UserConverter();
    VoteConverter voteConverter = new VoteConverter(lunch, dao, userConverter);
    VoteUpdater voteUpdater = new VoteUpdater(lunch, voteConverter);

    return new RestaurantsListAdapter(getActivity(), dao, voteUpdater);
  }
}
