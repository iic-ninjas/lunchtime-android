package com.iic.lunchtime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.iic.lunchtime.R;
import com.iic.lunchtime.dal.LunchtimeDBHelper;
import com.iic.lunchtime.models.Restaurant;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;
import java.io.Closeable;
import java.util.List;

/**
 * Created by ifeins on 2/26/15.
 */
public class RestaurantsListAdapter extends ArrayAdapter<Restaurant> implements Closeable {

  private RuntimeExceptionDao<Restaurant, ?> dao;

  private LunchtimeDBHelper dbHelper;

  private List<Restaurant> restaurants;

  public RestaurantsListAdapter(Context context) {
    super(context, R.layout.list_item_restaurant);

    dbHelper = OpenHelperManager.getHelper(getContext(), LunchtimeDBHelper.class);
    dao = dbHelper.getRuntimeExceptionDao(Restaurant.class);
  }

  @Override
  public void notifyDataSetChanged() {
    restaurants = null;

    super.notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return getRestaurants().size();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Restaurant restaurant = restaurants.get(position);

    ViewHolder holder;
    if (convertView != null) {
      holder = (ViewHolder) convertView.getTag();
    } else {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_restaurant, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }

    holder.titleView.setText(restaurant.getName());
    Picasso.with(getContext()).
        load(restaurant.getLogoUrl()).
        placeholder(R.drawable.ic_restaurant_logo_default).
        into(holder.imageView);

    return convertView;
  }

  private List<Restaurant> getRestaurants() {
    if (restaurants == null) {
      restaurants = dao.queryForAll();
    }

    return restaurants;
  }

  @Override
  public void close() {
    OpenHelperManager.releaseHelper();
    dao = null;
    dbHelper = null;
  }

  static class ViewHolder {
    @InjectView(R.id.list_item_restaurant_title)
    TextView titleView;

    @InjectView(R.id.list_item_restaurant_icon)
    ImageView imageView;

    @InjectView(R.id.list_item_restaurant_vote_btn)
    Button voteButton;

    private ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
