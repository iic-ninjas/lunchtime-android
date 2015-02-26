package com.iic.lunchtime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.iic.lunchtime.R;
import com.iic.lunchtime.dal.LunchtimeDBHelper;
import com.iic.lunchtime.models.Restaurant;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by ifeins on 2/26/15.
 */
public class RestaurantsListAdapter extends ArrayAdapter<Restaurant> {

  private List<Restaurant> restaurants;

  public RestaurantsListAdapter(Context context) {
    super(context, R.layout.list_item_restaurant);
  }

  @Override
  public int getCount() {
    return getRestaurants().size();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Restaurant restaurant = restaurants.get(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_restaurant, parent, false);
    }

    TextView titleView = (TextView) convertView.findViewById(R.id.list_item_restaurant_title);
    titleView.setText(restaurant.getName());

    ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_restaurant_icon);
    Picasso.with(getContext()).load(restaurant.getLogoUrl()).into(imageView);

    return convertView;
  }

  private List<Restaurant> getRestaurants() {
    if (restaurants == null) {
      LunchtimeDBHelper dbHelper = OpenHelperManager.getHelper(getContext(), LunchtimeDBHelper.class);
      try {
        RuntimeExceptionDao<Restaurant, ?> dao = dbHelper.getRuntimeExceptionDao(Restaurant.class);
        restaurants = dao.queryForAll();
      } finally {
        OpenHelperManager.releaseHelper();
      }
    }

    return restaurants;
  }
}
