package com.phunware.homework.android.api;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.phunware.homework.android.model.Venue;

public class VenueResponse {
	@SerializedName("venues")
	private List<Venue> mVenues;

	public VenueResponse() {
	}

	public List<Venue> getVenues() {
		return mVenues;
	}

	public void setVenues(List<Venue> venues) {
		this.mVenues = venues;
	}

}
