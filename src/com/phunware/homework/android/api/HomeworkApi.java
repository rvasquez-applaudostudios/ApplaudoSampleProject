package com.phunware.homework.android.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

public class HomeworkApi {
	private final static String API_URL = "https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
	private RequestQueue mRequestQueue;

	public HomeworkApi(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public Request<VenueResponse> getVenues(Listener<VenueResponse> listener,
			ErrorListener errorListener) {
		GsonRequest<VenueResponse> gsonRequest = new GsonRequest<VenueResponse>(
				API_URL, VenueResponse.class, listener, errorListener);
		return mRequestQueue.add(gsonRequest);
	}

}
