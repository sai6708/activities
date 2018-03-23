package mrhot.in.mrhotforbusiness.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;

/**
 * Created by prabhat on 13-09-2017.
 */

public class MySingleton {

    private static MySingleton mInstances;
    private RequestQueue requestQueue;
    private static Context mctx;

    private MySingleton(Context context){
        mctx=context;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstances(Context context){
        if(mInstances==null)
            mInstances=new MySingleton(context);
        return mInstances;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }


}
