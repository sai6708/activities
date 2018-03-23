package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import mrhot.in.mrhotforbusiness.activities.utils.ChangeProfile;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by prabhat on 29-09-2017.
 */

public class EditTiming extends AppCompatActivity
{
    Gson gson=new Gson();
    EditText ed1,ed2;
    String deliveryslot,takeorder,slotdet,pos;
    String[] slot={"Lunch","Dinner"};
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String id;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingtime);
        pos=getIntent().getExtras().getString("Pos");
        Log.v("POS",pos);
        String info=new SharedPrefManager(EditTiming.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();


        ed1=(EditText)findViewById(R.id.editdeliveryslot);
        ed2=(EditText)findViewById(R.id.edittakeorder);

        spinner=(Spinner)findViewById(R.id.spinneredit);
        adapter=ArrayAdapter.createFromResource(this,R.array.Slotdetails,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                slotdet=slot[position];
                Log.v("Spinner",slotdet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn=(Button)findViewById(R.id.Savebtnabc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LunchDetails();
            }
        });


    }

    public void LunchDetails() {

        deliveryslot=ed1.getText().toString();
        takeorder=ed2.getText().toString();

        if(deliveryslot.equals("")||takeorder.equals(""))
        {
            Toast.makeText(EditTiming.this,"Enter all the details",Toast.LENGTH_LONG).show();
        }
        else{
            final ProgressDialog progress;
            progress = new ProgressDialog(EditTiming.this);
            progress.setMessage("Please Wait ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

        Log.v("Seee",id+"/"+pos+"/"+slotdet+"/"+deliveryslot+"/"+takeorder);
        RequestQueue requestQueue = Volley.newRequestQueue(EditTiming.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/changetime.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String s=jsonObject.getString("server");
                    progress.dismiss();
                    Toast.makeText(EditTiming.this,s+"",Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id",id);
                map.put("slot",pos);
                map.put("shift",slotdet);
                map.put("name",deliveryslot);
                map.put("endtime",takeorder);
                return map;

            }

        };
        requestQueue.add(stringRequest);

    }
    }

    public void onBackPressed() {
        Intent i=new Intent(EditTiming.this,EditDeliveryTimings.class);
        startActivity(i);
        super.onBackPressed();
    }



}
