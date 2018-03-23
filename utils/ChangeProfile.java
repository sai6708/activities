package mrhot.in.mrhotforbusiness.activities.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.AddImage;
import mrhot.in.mrhotforbusiness.activities.HomeActivity;
import mrhot.in.mrhotforbusiness.activities.Profiles;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;

public class ChangeProfile extends AppCompatActivity {

    String predesc,prename,name,description;
    EditText ed1,ed2;
    Gson gson=new Gson();
    Button btn;
    ImageView img;
    Button imgedit;
    Context context=ChangeProfile.this;
    String id;
    //AddImage addImage=new AddImage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        String info=new SharedPrefManager(ChangeProfile.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();
        Log.v("Checking id",id);
        ed1=(EditText)findViewById(R.id.ChngProName);
        ed2=(EditText)findViewById(R.id.ChngProDesc);

        Seethis();
        img=(ImageView)findViewById(R.id.ChngProImg);
        AddImage.Downloadimage(img,id,ChangeProfile.this);
        imgedit=(Button)findViewById(R.id.ChngProImgedit);
        btn=(Button)findViewById(R.id.ChngProSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=ed1.getText().toString();
                description=ed2.getText().toString();
                changeinfo();

                Toast.makeText(ChangeProfile.this,"Values Updated.",Toast.LENGTH_LONG).show();

                Intent i=new Intent(ChangeProfile.this, HomeActivity.class);
                startActivity(i);

            }
        });
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangeProfile.this, AddImage.class);
                startActivity(intent);
            }
        });
    }

    public void changeinfo(){

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/ChangeProfile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("server");
                    String name,desc;

                    JSONObject jo = dataArray.getJSONObject(0);

                    name = jo.getString("name");
                    desc=jo.getString("description");


                    Log.v("name and dec=",name+""+desc);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something went wrong while updating.",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",id);
                map.put("name",name);
                map.put("description",description);
                return map;
            }
        };
        requestQueue.add(stringRequest);


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

                    ed1.setText(name);
                    ed2.setText(desc);


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
