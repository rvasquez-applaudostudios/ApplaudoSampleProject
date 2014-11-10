package com.phunware.homework.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.phunware.homework.android.R;
import com.phunware.homework.android.fragment.VenueDetailFragment;
import com.phunware.homework.android.fragment.VenueListFragment;
import com.phunware.homework.android.model.Venue;

public class MainActivity extends FragmentActivity implements
		VenueListFragment.VenueListener {

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singlepane);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.fragment_container,
							VenueListFragment.getInstance()).commit();
		}

		if (findViewById(R.id.venue_detail_container) != null) {
			mTwoPane = true;
			((VenueListFragment) getSupportFragmentManager().findFragmentById(
					R.id.venue_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onVenueSelected(Venue venue) {
		if (mTwoPane) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.venue_detail_container, VenueDetailFragment.getInstance(venue)).commit();

		} else {
			Intent detailIntent = new Intent(this, VenueDetailActivity.class);
			detailIntent.putExtra(VenueDetailFragment.ARG_VENUE, venue);
			startActivity(detailIntent);
		}
	}
}
