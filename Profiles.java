package mrhot.in.mrhotforbusiness.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.adapters.ItemListAdapter;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.ChangeProfile;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by prabhat on 04-09-2017.
 */

public class Profiles extends AppCompatActivity {

    Gson gson=new Gson();
    static String id;
    static String JsonString,jsonstring;
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    static TextView tv1,tv2;
    Context context=Profiles.this;
    Profiles adapter;
    ImageView img;

    public Profiles(){}

    public Profiles(Context context) {
        this.context = context;
        this.adapter = this;
    }

    Button bchange;
    String descript="Testdesc",realname="testdesc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // new NewClass().execute();
        tv1=(TextView)findViewById(R.id.tfcname);
        tv2=(TextView)findViewById(R.id.tfcdescription);
        String info=new SharedPrefManager(Profiles.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();
        img=(ImageView)findViewById(R.id.Profiles_image);
        AddImage.Downloadimage(img,id,Profiles.this);

        Seethis();


        bchange=(Button)findViewById(R.id.tfcchange);
        bchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profiles.this, ChangeProfile.class);
                i.putExtra("desc",descript);
                i.putExtra("name",realname);
                startActivity(i);
            }
        });


    }



    public void Seethis(){

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/newPrabhat.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("server");
                    String name,desc;

                    JSONObject jo = dataArray.getJSONObject(0);

                    name = jo.getString("name");
                    desc=jo.getString("description");
                    descript=desc;
                    realname=name;

                    tv1.setText(name);
                    tv2.setText(desc);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }



}
