package mrhot.in.mrhotforbusiness.activities.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Anuran on 3/23/2017.
 */

public class DownloadItemService extends Service {
    List<Item> allItems=new ArrayList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/getAllItems.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray data=jsonObject.optJSONArray("data");
                for(int i=0;i<data.length();i++){
                    JSONObject jsonObject1= null;
                    try {
                        jsonObject1 = data.getJSONObject(i);
                        String itemId,itemName,itemNick,itemPrice,desc,cateory,fastingitem,spicy,jain,quantity;
                        itemId=jsonObject1.optString("itemId");
                        itemName=jsonObject1.optString("itemName");
                        itemNick=jsonObject1.optString("itemNick");
                        itemPrice=jsonObject1.optString("itemPrice");
                        desc=jsonObject1.optString("description");
                        cateory=jsonObject1.optString("category");
                        fastingitem=jsonObject1.optString("fastingitem");
                        spicy=jsonObject1.optString("spicy");
                        jain=jsonObject1.optString("jain");
                        quantity=jsonObject1.optString("quantity");
                        Item item=new Item(itemId,itemName,itemNick,itemPrice,desc,cateory,fastingitem,spicy,jain,quantity);
                        allItems.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Gson gson=new Gson();
                String allitems=gson.toJson(allItems);
                new SharedPrefManager(DownloadItemService.this).setAllItem(allitems);
                new SharedPrefManager(DownloadItemService.this).setHasDownloaded(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        return super.onStartCommand(intent, flags, startId);
    }
}
