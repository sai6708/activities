package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;


public class Registervendor3 extends AppCompatActivity implements View.OnClickListener {
    EditText minamount;
    ProgressDialog loading;
    EditText deliverycharges;
    EditText freedelivery;
    String minamount1;
    String deliverycharges1;
    String freedelivery1;
    public String jsonsend;
    public String[] data1;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registervendor3);
        minamount = (EditText) findViewById(R.id.minamount);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        deliverycharges = (EditText) findViewById(R.id.delivery);
        freedelivery = (EditText) findViewById(R.id.freedelivery);
        data1 = Registorvendor2.data;

        setTitle("Register yourself");
        JsonArray json = new JsonArray();

            for (int i = 0; i < data1.length; i++) {

                json.add(data1[i]);
            }


        jsonsend=json.toString();
    }






    private void adddata() {
        loading = ProgressDialog.show(this, "Please wait...", "Registering you on Mr. Hot...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.mrhot.in/androidApp/sendtovendortable.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registervendor3.this, response, Toast.LENGTH_LONG).show();
                        loading.dismiss();
                        vendorLogin(Registervendor.username2,Registervendor.password1,new SharedPrefManager(Registervendor3.this).getFirebaseToken());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registervendor3.this, error.toString(), Toast.LENGTH_LONG).show();
                        Intent go=new Intent(Registervendor3.this,MainActivity.class);
                        startActivity(go);

                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("areas",jsonsend);
                params.put("minamount",minamount1);
                params.put("freedelivery",freedelivery1);
                params.put("deliverycharges",deliverycharges1);
                params.put("user",Registervendor.username2);
                params.put("password",Registervendor.password1);
                params.put("phone",Registervendor.phone1);
                params.put("tiffinname",Registervendor.tiffinname1);
                params.put("city",Registervendor.cityname);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if (v == submit) {
            minamount1=minamount.getText().toString();
            freedelivery1=freedelivery.getText().toString();
            deliverycharges1=deliverycharges.getText().toString();
            adddata();

        }
    }
    public void vendorLogin(final String username,final String password, final String token){
        final ProgressDialog progressDialog=new ProgressDialog(Registervendor3.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Have patience while we are trying to log you in......");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/vendorLoginjd.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.optJSONArray("data");
                    JSONObject successObj=jsonArray.getJSONObject(0);
                    String success=successObj.optString("success");
                    if(success.equals("1")){
                        JSONObject vendorInfoObj=jsonArray.getJSONObject(1);
                        String id,city,name,username,contact,autokey,qualityRating,varietyRating,priceRating;
                        id=vendorInfoObj.optString("vendorid");
                        city=vendorInfoObj.optString("city");
                        name=vendorInfoObj.optString("name");
                        username=vendorInfoObj.optString("username");
                        contact=vendorInfoObj.optString("contact");
                        autokey=vendorInfoObj.optString("autokey");
                        qualityRating=vendorInfoObj.optString("qualityRating");
                        varietyRating=vendorInfoObj.optString("varietyRating");
                        priceRating=vendorInfoObj.optString("priceRating");

                        Vendor vendor=new Vendor(id,city,name,username,contact,autokey,qualityRating,varietyRating,priceRating);
                        Gson gson=new Gson();
                        String info=gson.toJson(vendor);
                        new SharedPrefManager(Registervendor3.this).setVendorInfo(info);

                        new SharedPrefManager(Registervendor3.this).setLogin(username, password);
//                        Toast.makeText(getApplicationContext(),"Toast is: "+username +" "+password +" "+autokey,Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                        Intent intent=new Intent(Registervendor3.this,live.class);
                        //intent.putExtra("id",id);
                        //intent.putExtra("name",name);
                        //intent.putExtra("flag",0);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Registervendor3.this,"You provided wrong credentials.Please try again.",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                map.put("token",token);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}