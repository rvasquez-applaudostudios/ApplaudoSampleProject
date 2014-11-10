package com.phunware.homework.android.api;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {
	private final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Listener<T> listener;

	public GsonRequest(String url, Class<T> classType,
			Map<String, String> headers, Listener<T> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		this.clazz = classType;
		this.headers = headers;
		this.listener = listener;
	}

	public GsonRequest(String url, Class<T> classType, Listener<T> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		headers = null;
		this.clazz = classType;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			json = "{\"venues\":" + json + "}";
			T parsedObject = gson.fromJson(json, clazz);
			return Response.success(parsedObject,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}