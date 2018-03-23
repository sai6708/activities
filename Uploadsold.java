package mrhot.in.mrhotforbusiness.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.Product;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

import static mrhot.in.mrhotforbusiness.activities.SoldDataPage.products;

public class Uploadsold extends AppCompatActivity {

    String info,shift;
    Gson gson;
    Vendor vendor;
    public List<Product> solduploaddata=new ArrayList<>();
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadsold);
        info=new SharedPrefManager(this).getVendorInfo();
        gson=new Gson();
        vendor=gson.fromJson(info,Vendor.class);
        tx=(TextView)findViewById(R.id.textView20);
        tx.setText("..........RESPONSE...........");
        shift=SoldDataPage.shift;
        solduploaddata= products;
        for(int i=0;i<solduploaddata.size();i++)
        {
            uploadsoldoutitem((solduploaddata.get(i).getId()),shift,solduploaddata.get(i).getAvail(),solduploaddata.get(i).getItem_name());
        }

        products.clear();
    }



    public void uploadsoldoutitem(final String item,final String shift,final String avail,final String name)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/updatesolditem.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String s=response;
                    tx.append("\n"+name+"  "+s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Uploadsold.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorid",vendor.getId());
                map.put("shift",shift);
                map.put("item",item);
                map.put("city",vendor.getCity());
                map.put("avail",avail);
               // System.out.println(vendor.getId()+","+shift+","+item+","+vendor.getCity());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
