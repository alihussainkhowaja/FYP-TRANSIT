package com.example.transit;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQuen;
    private Context mCtx;

    public MySingleton(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQuen =getmRequestQuen();
    }

    public RequestQueue getmRequestQuen(){
        if (mRequestQuen == null){
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(),1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQuen = new RequestQueue(cache,network);
            mRequestQuen = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQuen;
    }
    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> request){
        mRequestQuen.add(request);
    }
}
