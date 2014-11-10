package com.phunware.homework.android;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.phunware.homework.android.api.HomeworkApi;

import android.app.Application;

public class HomeworkApplication extends Application {
	private static HomeworkApplication mInstance;
	private HomeworkApi mApi;

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();

		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(options)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheSize(5 * 1024 * 1024).build();

		ImageLoader.getInstance().init(configuration);

	}

	public static HomeworkApplication getInstance() {
		return mInstance;
	}

	public HomeworkApi getApi() {
		if (mApi == null) {
			mApi = new HomeworkApi(this);
		}
		return mApi;
	}

}
