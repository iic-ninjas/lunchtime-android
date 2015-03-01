package com.iic.lunchtime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import bolts.Task;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.iic.lunchtime.R;
import com.iic.lunchtime.dal.RestaurantDAO;
import com.iic.lunchtime.models.Restaurant;
import com.iic.lunchtime.services.VoteUpdater;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * An adapter for displaying restaurants in a list.
 * Created by ifeins on 2/26/15.
 */
public class RestaurantsListAdapter extends BaseAdapter {

  private final Context context;

  private final RestaurantDAO dao;

  private final VoteUpdater voteUpdater;

  private List<Restaurant> restaurants;

  public RestaurantsListAdapter(Context context, RestaurantDAO dao, VoteUpdater voteUpdater) {
    this.context = context;
    this.dao = dao;
    this.voteUpdater = voteUpdater;
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
  public Object getItem(int position) {
    return restaurants.get(position);
  }

  @Override
  public long getItemId(int position) {
    return restaurants.get(position).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Restaurant restaurant = restaurants.get(position);

    ViewHolder holder;
    if (convertView != null) {
      holder = (ViewHolder) convertView.getTag();
    } else {
      convertView = LayoutInflater.from(context).inflate(R.layout.list_item_restaurant, parent, false);
      holder = new ViewHolder(convertView, this);
      convertView.setTag(holder);
    }

    holder.position = position;
    holder.titleView.setText(restaurant.getName());
    Picasso.with(context).
        load(restaurant.getLogoUrl()).
        placeholder(R.drawable.ic_restaurant_logo_default).
        into(holder.imageView);

    return convertView;
  }

  public void onRestaurantVoted(int position) {
    final Restaurant restaurant = restaurants.get(position);
    Task.callInBackground(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        voteUpdater.vote(restaurant);
        return null;
      }
    });
  }

  private List<Restaurant> getRestaurants() {
    if (restaurants == null) {
      restaurants = dao.queryForAll();
    }

    return restaurants;
  }

  static class ViewHolder {

    private final RestaurantsListAdapter delegate;

    @InjectView(R.id.list_item_restaurant_title)
    TextView titleView;

    @InjectView(R.id.list_item_restaurant_icon)
    ImageView imageView;

    @InjectView(R.id.list_item_restaurant_vote_btn)
    Button voteButton;

    int position;

    private ViewHolder(View view, RestaurantsListAdapter adapter) {
      ButterKnife.inject(this, view);
      this.delegate = adapter;
    }

    @OnClick(R.id.list_item_restaurant_vote_btn)
    public void onVoteClicked() {
      this.delegate.onRestaurantVoted(position);
    }
  }
}
