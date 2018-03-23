package mrhot.in.mrhotforbusiness.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.adapters.SoldDataAdapter;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.models.sold_data;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Jasbir_Singh on 18-06-2017.
 */

public class SoldFrag extends Fragment {
    ListView soldlist;
    SoldDataAdapter soldDataAdapter;
    List<sold_data> sold_mydata=new ArrayList<>();
    String info;
    Gson gson;
    Vendor vendor;
    Context cntx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sold_out_replace,container,false);
        soldlist=(ListView)view.findViewById(R.id.soldList);
        cntx=this.getActivity();
        info=new SharedPrefManager(getActivity()).getVendorInfo();
        gson=new Gson();
        vendor=gson.fromJson(info,Vendor.class);
        soldDataAdapter = new SoldDataAdapter(getActivity(),sold_mydata);
        soldlist.setAdapter(soldDataAdapter);
        sold_mydata.clear();
        getSoldMenu(vendor.getId(),getArguments().getString("shift"));
        return view;
    }
    public void getSoldMenu(final String VendorId, final String shift){
        RequestQueue requestQueue= Volley.newRequestQueue(this.getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/getsolddata.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ///////////////Start from here
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject temp=dataArray.getJSONObject(i);
                        String itemname;
                        int success,item;
                        success=temp.getInt("success");
                        itemname=temp.getString("itemname");
                        item=temp.getInt("item");
                            sold_data data=new sold_data(success,item,itemname);
                            sold_mydata.add(data);
                        System.out.println(item +"  "+success);
                    }
                    soldDataAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Toast.makeText(getActivity(),"Something went wrong while fetching your item list",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorid",VendorId);
                map.put("shift",shift);
                //map.put("day",day);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


}

