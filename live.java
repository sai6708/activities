package mrhot.in.mrhotforbusiness.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class live extends AppCompatActivity {
ToggleButton tbareas, tbcharges, tbmenu, tbpackage;
    Button areas,charges,menu,packaging,submit,save;
    int tb1,tb2,tb3,tb4;
    int active,activate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        tbareas = (ToggleButton) findViewById(R.id.tbmodify);
        tbareas.setChecked(true);
        tbareas.setEnabled(false);
        tbcharges = (ToggleButton) findViewById(R.id.tbmodify2);
        tbmenu = (ToggleButton) findViewById(R.id.tbmodify3);
        tbpackage = (ToggleButton) findViewById(R.id.tbmodify4);
        final String vid = new SharedPrefManager(this).getVendorID();
        vendorLogin(vid);
        areas = (Button)findViewById(R.id.bmodify);
        submit = (Button) findViewById(R.id.activate);
        charges = (Button)findViewById(R.id.bmodify2);
        menu = (Button)findViewById(R.id.bmodify3);
        packaging= (Button)findViewById(R.id.bmodify4);
        save =(Button)findViewById(R.id.save);
        areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(live.this,HomeActivity.class);
                startActivity(it);
            }
        });
        charges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(live.this,HomeActivity.class);
                startActivity(it);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(live.this,ChangeMenuActivity.class);
                startActivity(it);
            }
        });
        packaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(live.this)
                        .setTitle("Packaging Information")
                        .setMessage("We serve in 150mL disposable air tight containers. If you need help in purchasin call 8967946385.")
                        .setPositiveButton("Ok", null).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tbareas.isChecked())
                {
                    tb1=1;
                }
                else
                {
                    tb1=0;
                }
                if(tbcharges.isChecked())
                {
                    tb2=1;
                }
                else
                {
                    tb2=0;
                }
                if(tbmenu.isChecked())
                {
                    tb3=1;
                }
                else
                {
                    tb3=0;
                }
                if(tbpackage.isChecked())
                {
                    tb4=1;
                }
                else
                {
                    tb4=0;
                }

                if(tb1==1&&tb2==1&&tb3==1&&tb4==1)
                {

                    activate=1;
                }
                else
                {
                    activate=0;

                }
                setValues(vid,tb3,tb4,activate);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(active==0)
                {
                    setValues(vid,1,1,1);
                    new AlertDialog.Builder(live.this)
                            .setTitle("Packaging Information")
                            .setMessage("Your tiffin center is now online.")
                            .setPositiveButton("Ok", null)
                            .setNeutralButton("Go to HomePage", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(live.this,HomeActivity.class);
                                    startActivity(it);
                                }
                            }).show();
                }
                    else
                {
                    setValues(vid,1,1,0);
                    new AlertDialog.Builder(live.this)
                            .setTitle("Packaging Information")
                            .setMessage("Your tiffin center is now offline.")
                            .setPositiveButton("Ok", null)
                            .setNeutralButton("Go to HomePage", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(live.this,HomeActivity.class);
                                    startActivity(it);
                                }
                            }).show();



                }
            }
        });
    }
    public void vendorLogin(final String vendorID){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/sellerApp_VendorInfo.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject=new JSONArray(response);
                    JSONObject successObj=jsonObject.getJSONObject(0);
                    String success=successObj.optString("success");
                    if(success.equals("1")){
                        JSONObject vendorInfoObj=jsonObject.getJSONObject(1);

                       String priceRating=vendorInfoObj.optString("priceRating");
                        int isMenu= Integer.parseInt(vendorInfoObj.optString("isMenu"));
                        int isPacked= Integer.parseInt(vendorInfoObj.optString("isPacked"));
                        active = Integer.parseInt(vendorInfoObj.optString("active"));;
                        if(priceRating!="NULL")
                            tbcharges.setChecked(true);
                        if(isMenu==1)
                            tbmenu.setChecked(true);
                        if(isPacked==1)
                            tbpackage.setChecked(true);
                        if(active==1)
                        {
                            submit.setText("Go Offline");
                        }
                        else
                        {
                            submit.setText("Go Online");
                        }
                        if(isMenu==0||isPacked==0)
                        {
                            submit.setBackgroundColor(Color.rgb(211,211,211));
                            submit.setEnabled(false);
                        }
                        else {
                            submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            submit.setEnabled(true);
                        }

                    }else{

                        Toast.makeText(live.this,"Cannot Find Information",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorID",vendorID);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void setValues(final String vendorID,final int tb3, final int tb4, final  int activate)
    {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/seller_updateVendor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.optJSONArray("data");
                    JSONObject successObj=jsonArray.getJSONObject(0);
                    String success=successObj.optString("success");
                    if(success.equals("1")){
                        Toast.makeText(live.this,"Values Updated",Toast.LENGTH_LONG).show();
                        vendorLogin(vendorID);
                    }else{

                        Toast.makeText(live.this,"Cannot Find Information",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",vendorID);
                map.put("isMenu",Integer.toString(tb3));
                map.put("isPacked",Integer.toString(tb4));
                map.put("active",Integer.toString(activate));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed()
    {
        Intent it = new Intent(live.this,HomeActivity.class);
        startActivity(it);
    }

}
