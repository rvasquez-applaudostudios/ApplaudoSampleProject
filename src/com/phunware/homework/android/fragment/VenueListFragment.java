package com.phunware.homework.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.phunware.homework.android.HomeworkApplication;
import com.phunware.homework.android.R;
import com.phunware.homework.android.adapter.VenueListAdapter;
import com.phunware.homework.android.api.VenueResponse;
import com.phunware.homework.android.model.Venue;

public class VenueListFragment extends Fragment {

	private VenueListener mCallback;
	private Request<VenueResponse> mRequest;
	private VenueListAdapter mAdapter;
	private boolean activateOnItemClick = false;

	private ListView mListView;
	private ProgressBar mProgress;

	public static VenueListFragment getInstance() {
		return new VenueListFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_venue_list, container,false);

		mListView = (ListView) view.findViewById(R.id.lv_venues);
		mProgress = (ProgressBar) view.findViewById(R.id.progress_venue_list);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mProgress.setVisibility(View.VISIBLE);
		mRequest = HomeworkApplication.getInstance().getApi()
				.getVenues(new Listener<VenueResponse>() {

					@Override
					public void onResponse(VenueResponse response) {
						mRequest = null;
						mAdapter = new VenueListAdapter(getActivity(), response.getVenues());
						mListView.setAdapter(mAdapter);
						if(activateOnItemClick){
							mListView.setSelection(0);
						}
						mProgress.setVisibility(View.GONE);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Toast.makeText(getActivity().getApplicationContext(),
								R.string.network_error, Toast.LENGTH_SHORT).show();
					}

				});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long id) {
				if (mCallback != null) {
					Venue venue = mAdapter.getItem(position);
					mCallback.onVenueSelected(venue);
				}

			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof VenueListener)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallback = (VenueListener) activity;
	}

	public interface VenueListener {
		void onVenueSelected(Venue venue);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mRequest != null) {
			mRequest.cancel();
		}
	}

	public void setActivateOnItemClick(boolean b) {
		this.activateOnItemClick = b;
	}
}
