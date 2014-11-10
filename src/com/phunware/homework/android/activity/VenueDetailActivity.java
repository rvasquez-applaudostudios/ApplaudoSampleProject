package com.phunware.homework.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.phunware.homework.android.R;
import com.phunware.homework.android.fragment.VenueDetailFragment;
import com.phunware.homework.android.model.Venue;

public class VenueDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singlepane);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {

			Venue venue = (Venue) getIntent().getSerializableExtra(
					VenueDetailFragment.ARG_VENUE);

			Bundle arguments = new Bundle();
			arguments.putSerializable(VenueDetailFragment.ARG_VENUE, venue);

			VenueDetailFragment fragment = new VenueDetailFragment();
			fragment.setArguments(arguments);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
