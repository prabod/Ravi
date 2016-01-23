package com.emergelk.ravindrab.Newsfeed.app;


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.emergelk.ravindrab.Newsfeed.Volley.LruBitmapCache;
import com.parse.Parse;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    LruBitmapCache mLruBitmapCache;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "JpDHcZPYDuJ8yj3GcmVAPoyWkUxZCCbep54HtOs2", "m55mmi6azeW2tPYB9us0kosAmTEwktaNwfigtxoG");

        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}