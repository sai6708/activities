package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.services.DownloadItemService;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText userNameET;
    EditText password;
    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toast.makeText(getApplicationContext(),"Token is : "+token,Toast.LENGTH_LONG).show();

        btnLogin=(Button)findViewById(R.id.btnLogin);
        userNameET=(EditText)findViewById(R.id.userNameET);
        password=(EditText)findViewById(R.id.passwordET);

        if(!new SharedPrefManager(MainActivity.this).getLoginUser().equals("")){
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameET.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"You must provide your userName and password for login.",Toast.LENGTH_LONG).show();
                    return;
                }

                final String token = new SharedPrefManager(MainActivity.this).getFirebaseToken();

                vendorLogin(userNameET.getText().toString().trim(),password.getText().toString().trim(),token);

            }
        });
        if(!new SharedPrefManager(MainActivity.this).getHasDownloaded()){
            startService(new Intent(this,DownloadItemService.class));
        }


    }


    public void vendorLogin(final String username,final String password, final String token){
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
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
                        new SharedPrefManager(MainActivity.this).setVendorInfo(info);

                        new SharedPrefManager(MainActivity.this).setLogin(username, password);
//                        Toast.makeText(getApplicationContext(),"Toast is: "+username +" "+password +" "+autokey,Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"You provided wrong credentials.Please try again.",Toast.LENGTH_LONG).show();
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



    public void register(View view){
        Intent browserIntent = new Intent(MainActivity.this, Registervendor.class);
        startActivity(browserIntent);
    }
}
