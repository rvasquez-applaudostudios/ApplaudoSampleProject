package com.phunware.homework.android.fragment;

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.phunware.homework.android.R;
import com.phunware.homework.android.model.ScheduleItem;
import com.phunware.homework.android.model.Venue;

public class VenueDetailFragment extends Fragment {
	public static final String ARG_VENUE = "venue";
	private static final String ADDRESS_FORMAT = "%s \r\n%s, %s %s";
	private static final String SCHEDULE_FORMAT = "%s %s to %s";
	private  String[] mDaysOfWeek;

	private ImageView mImage;
	private TextView mAddress;
	private TextView mSchedules;
	private TextView mName;

	private Venue mVenue;

	public static VenueDetailFragment getInstance(Venue venue) {
		VenueDetailFragment fragment = new VenueDetailFragment();
		Bundle b = new Bundle();
		b.putSerializable(ARG_VENUE, venue);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments().containsKey(ARG_VENUE)) {
			mVenue = (Venue) getArguments().getSerializable(ARG_VENUE);
		}
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_venue_detail,
				container, false);

		mImage = (ImageView) rootView.findViewById(R.id.img_venue_detail);
		mAddress = (TextView) rootView
				.findViewById(R.id.txt_venue_detail_address);
		mSchedules = (TextView) rootView
				.findViewById(R.id.txt_venue_detail_schedules);
		mName = (TextView) rootView.findViewById(R.id.txt_venue_detail_title);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mDaysOfWeek = getResources().getStringArray(R.array.days_of_week);
		mName.setText(mVenue.getName());
		mAddress.setText(String.format(ADDRESS_FORMAT, mVenue.getAddress(),
				mVenue.getCity(), mVenue.getState(), mVenue.getZip()));

		ImageLoader.getInstance().displayImage(mVenue.getImageUrl(), mImage);

		if (mVenue.getSchedule() != null && mVenue.getSchedule().size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (ScheduleItem item : mVenue.getSchedule()) {
				sb.append(String.format(SCHEDULE_FORMAT,
						getDayOfWeek(item.getStartDate()),
						item.getStartDateString(), item.getEndDateString()));
				sb.append("\r\n");
			}

			mSchedules.setText(sb.toString());
		} else {
			mSchedules.setText("No schedules availables.");
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_venue_detail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_venue_detail_share:
			shareVenue();
			return true;

		case R.id.menu_venue_detail_call:
			callVenue();
			return true;
		
		case R.id.menu_venue_detail_tickets:
			getTickets();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void callVenue() {
		Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mVenue.getPhone()));
		startActivity(callIntent);
	}

	private void shareVenue() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mVenue.getName());
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, mVenue.getAddress());
		startActivity(Intent.createChooser(shareIntent, getActivity()
				.getString(R.string.share_via)));
	}
	
	private void getTickets(){
		if(mVenue.getTicketLink()!=null && !mVenue.getTicketLink().isEmpty()){
			Intent ticketsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mVenue.getTicketLink()));
			startActivity(ticketsIntent);
		}
		else{
			Toast.makeText(getActivity(), R.string.no_tickets_available, Toast.LENGTH_SHORT).show();
		}
	}

	private String getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH) + 1;
		int month = c.get(Calendar.MONTH) + 1;
		String day = mDaysOfWeek[dayOfWeek] + " " + month + "/" + dayOfMonth;
		return day;
	}
}
