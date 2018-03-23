package mrhot.in.mrhotforbusiness.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import org.json.JSONStringer;

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
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class EditDeliveryTimings extends AppCompatActivity {

    String JsonString;
    DetailsAdapter detailsAdapter;
    ListView listView1,listView2;
    CardView lunchcard,dinnercard;
    TextView lunchbanner,dinnerbanner;
    RadioButton lunch,dinner;
    ViewGroup parent;
    ImageButton btn;
    View view;
    String id;
    Gson gson=new Gson();
    List lunchdata=new ArrayList<>();
    List dinnerdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_delivery_timings);

        String info=new SharedPrefManager(EditDeliveryTimings.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();
        lunchcard=(CardView) findViewById(R.id.lunchcard);
        dinnercard=(CardView)findViewById(R.id.dinnercard);
        lunchbanner=(TextView) findViewById(R.id.lunchbanner);
        dinnerbanner=(TextView) findViewById(R.id.dinnerbanner);
        listView1=(ListView)findViewById(R.id.lunchlist);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=listView1.getItemAtPosition(position);
                DetailsAdapter edt=(DetailsAdapter) o;
               Log.v("Listval","");
                //display value here
                TextView tx=(TextView)view.findViewById(R.id.deliveryslots);
                System.out.println("slot of "+tx.getText());
            }
        });
        LunchDetails();
        DinnerDetails();
        lunch=(RadioButton) findViewById(R.id.lunch);
        dinner=(RadioButton) findViewById(R.id.dinner);
        lunch.setOnCheckedChangeListener(null);
        lunch.setChecked(true);
        lunchbanner.setVisibility(View.VISIBLE);
        lunchcard.setVisibility(View.VISIBLE);
        dinnerbanner.setVisibility(View.GONE);
        dinnercard.setVisibility(View.GONE);

        lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    lunchbanner.setVisibility(View.VISIBLE);
                    lunchcard.setVisibility(View.VISIBLE);
                    dinner.setChecked(false);
                    dinnerbanner.setVisibility(View.GONE);
                    dinnercard.setVisibility(View.GONE);
                }

            }
        });
        dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    dinnerbanner.setVisibility(View.VISIBLE);
                    dinnercard.setVisibility(View.VISIBLE);
                    lunch.setChecked(false);
                    lunchbanner.setVisibility(View.GONE);
                    lunchcard.setVisibility(View.GONE);
                }

            }
        });
    }



    public void onEdit(View view){
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        EditDeliveryTimings.Editdetails c;

        String shift="";
        Log.v("pos=",position+"");
        if(listView.getId()==R.id.dinnerlist){
            c=(EditDeliveryTimings.Editdetails) this.dinnerdata.get(position);
            shift="dinner";
        }
        else
        {
            c=(EditDeliveryTimings.Editdetails) this.lunchdata.get(position);
            shift="lunch";
        }

        System.out.println("name =" +c.getName()+" active= "+c.getActive() +" time= "+c.getTime2());
        Intent i=new Intent(EditDeliveryTimings.this,Slotactivator.class);
        i.putExtra("slot",position);
        i.putExtra("shift",shift);
        i.putExtra("id",id);
        i.putExtra("name",c.getName());
        i.putExtra("time",c.getTime2());
        i.putExtra("active",c.getActive());
        startActivity(i);

    }






    public void LunchDetails() {

        RequestQueue requestQueue = Volley.newRequestQueue(EditDeliveryTimings.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/edittimelistvendor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                listView1 = (ListView) findViewById(R.id.lunchlist);
                detailsAdapter = new DetailsAdapter(EditDeliveryTimings.this, R.layout.editorders); // this line should come before the line given below ,otherwise eerror
                listView1.setAdapter(detailsAdapter);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server");
                    int count = 0,active =0;
                    String name, time2;
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(count);
                        name = jsonObject1.getString("name");
                        time2 = jsonObject1.getString("endTime");
                        if(time2.length()<4){ time2="0"+time2;}
                        active = jsonObject1.getInt("active");
                        Editdetails ed = new Editdetails(name, time2,active,"Lunch");
                        detailsAdapter.add(ed);
                        lunchdata.add(ed);
                        count++;
                    }

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
                 map.put("shift","Lunch");
                return map;
            }

        };
        requestQueue.add(stringRequest);

    }

    public void DinnerDetails() {

        RequestQueue requestQueue = Volley.newRequestQueue(EditDeliveryTimings.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/edittimelistvendor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                listView2 = (ListView) findViewById(R.id.dinnerlist);
                detailsAdapter = new DetailsAdapter(EditDeliveryTimings.this, R.layout.editorders); // this line should come before the line given below ,otherwise eerror
                listView2.setAdapter(detailsAdapter);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server");
                    int count = 0,active=0;
                    String name, time2;
                    while (count < jsonArray.length()) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(count);
                        name = jsonObject1.getString("name");
                        time2 = jsonObject1.getString("endTime");
                        if(time2.length()<4){ time2="0"+time2;}
                        active = jsonObject1.getInt("active");
                        Editdetails ed = new Editdetails(name, time2,active,"Dinner");
                        detailsAdapter.add(ed);
                        dinnerdata.add(ed);
                        count++;
                    }

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
                map.put("shift","Dinner");
                return map;
            }

        };
        requestQueue.add(stringRequest);

    }




        public static class Editdetails {

        String name,time2,period;
            int active=0;

        public Editdetails(String name, String time2,int active,String period) {
            this.name = name;
            this.time2 = time2;
            this.period=period;
            this.active=active;
        }

            public String getPeriod() {
                return period;
            }

            public void setPeriod(String period) {
                this.period = period;
            }

            public String getName() {
                return name;
        }

        public void setName(String time1) {
            this.name = time1;
        }

        public String getTime2() {
            return time2;
        }

       // public void setTime2(String time2) {           this.time2 = time2; }        }

        public int getActive(){ return active; }

           // public void setActive(int active){ this.active = active;}
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(EditDeliveryTimings.this,HomeActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
