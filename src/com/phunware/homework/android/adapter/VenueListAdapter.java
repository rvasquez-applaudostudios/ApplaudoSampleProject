package com.phunware.homework.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.phunware.homework.android.R;
import com.phunware.homework.android.model.Venue;

public class VenueListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Venue> mItems;

	public VenueListAdapter(Context context, List<Venue> venues) {
		this.mInflater = LayoutInflater.from(context);
		this.mItems = venues;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Venue getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			view = mInflater.inflate(R.layout.item_venue, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		bindViews(mItems.get(position), holder);

		return view;
	}

	private void bindViews(Venue venue, ViewHolder holder) {
		holder.name.setText(venue.getName());
		holder.address.setText(venue.getAddress());
		ImageLoader.getInstance().displayImage(venue.getImageUrl(), holder.image);
	}

	class ViewHolder {
		TextView name;
		TextView address;
		ImageView image;

		public ViewHolder(View view) {
			this.name = (TextView) view.findViewById(R.id.txt_venue_item_title);
			this.address = (TextView) view.findViewById(R.id.txt_venue_item_address);
			this.image = (ImageView) view.findViewById(R.id.img_venue_item);
		}
	}

}
