package mrhot.in.mrhotforbusiness.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.adapters.ItemListAdapter;
import mrhot.in.mrhotforbusiness.activities.adapters.OrdersListAdapter;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.Orders;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

//import static mrhot.in.mrhotforbusiness.R.id.header;

public class ManageOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<OrderListHead> list;
    private TextView lunchBtn;
    private TextView dinnerBtn;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list =new ArrayList<>();

        lunchBtn = (TextView) findViewById(R.id.lunchBtn);
        dinnerBtn = (TextView) findViewById(R.id.dinnerBtn);
        final ImageView lunchChk = (ImageView) findViewById(R.id.lunchIV);
        final ImageView dinnerChk = (ImageView) findViewById(R.id.dinnerIV);


        String info=new SharedPrefManager(ManageOrders.this).getVendorInfo();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        final String vendorid=vendor.getId();



        TimeZone timeZone;
        timeZone = TimeZone.getTimeZone("GMT+5:30");
        TimeZone.setDefault(timeZone);

        Calendar c = Calendar.getInstance();
        c.setTimeZone(timeZone);
        c.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        final String date = format1.format(c.getTime());



        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            if(timeOfDay >= 0 && timeOfDay < 15){
                setItems(vendorid, "Lunch",date);
                lunchChk.setVisibility(View.VISIBLE);
                dinnerChk.setVisibility(View.INVISIBLE);
            }else if(timeOfDay >= 15 && timeOfDay < 24){
                setItems(vendorid, "Dinner",date);
                dinnerChk.setVisibility(View.VISIBLE);
                lunchChk.setVisibility(View.INVISIBLE);
            }

        lunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItems(vendorid, "Lunch",date);
                lunchChk.setVisibility(View.VISIBLE);
                dinnerChk.setVisibility(View.INVISIBLE);
            }
        });
        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItems(vendorid, "Dinner",date);
                dinnerChk.setVisibility(View.VISIBLE);
                lunchChk.setVisibility(View.INVISIBLE);
            }
        });


    }

    void setItems(final String vendorid, final String shift, final String date) {


        final ProgressDialog progressDialog = new ProgressDialog(ManageOrders.this);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("fetching your orders.....");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(ManageOrders.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/dynamicOrdersjd.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    JSONObject successObj = jsonArray.getJSONObject(0);
                    String success = successObj.optString("success");
                    if (success.equals("1")) {

                        list.clear();
                        for (int i = 1; i < jsonArray.length(); i++) {

                            JSONObject orderInfoObj = jsonArray.getJSONObject(i);
                            OrderListHead olh = new OrderListHead(
                                                                    orderInfoObj.optString("id"),
                                                                    orderInfoObj.optString("hotcode"),
                                                                    orderInfoObj.optString("name"),
                                                                    orderInfoObj.optString("itemlist"),
                                                                    orderInfoObj.optString("quantity"),
                                                                    orderInfoObj.optString("COD"),
                                                                    orderInfoObj.optString("cost"),
                                                                    orderInfoObj.optString("contact"),
                                                                    orderInfoObj.optString("roomNo"),
                                                                    orderInfoObj.optString("address"),
                                                                    orderInfoObj.optString("slot"),
                                                                    orderInfoObj.optString("orderTime"),
                                                                    orderInfoObj.optString("accepted")
                                                                );

                            list.add(olh);

                        }

                        adapter = new OrdersListAdapter(list,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "List cannot be loaded. ", Toast.LENGTH_LONG).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("vendorid", vendorid);
                map.put("shift", shift);
                map.put("date", date);
                return map;
            }
        };


        requestQueue.add(stringRequest);






    }
}