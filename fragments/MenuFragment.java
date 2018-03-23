package mrhot.in.mrhotforbusiness.activities.fragments;

import android.app.ProgressDialog;
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
import mrhot.in.mrhotforbusiness.activities.HomeActivity;
import mrhot.in.mrhotforbusiness.activities.adapters.ItemListAdapter;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Anuran on 3/19/2017.
 */

public class MenuFragment extends Fragment {
    ListView itemList;
    ItemListAdapter itemListAdapter;
    List<Item> mydata=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_container_fragment,container,false);
        itemList=(ListView)view.findViewById(R.id.itemList);
        itemListAdapter=new ItemListAdapter(getActivity(),mydata);
        itemList.setAdapter(itemListAdapter);
        String info=new SharedPrefManager(getActivity()).getVendorInfo();
        Gson gson=new Gson();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        getVendorMenu(vendor.getId(),getArguments().getString("shift"),getArguments().getString("day"));
        return view;
    }

    public void getVendorMenu(final String vendorID, final String shift, final String day){
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Please");
        progressDialog.setMessage("fetching your item list.....");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/getVendorMenu.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject temp=dataArray.getJSONObject(i);
                        String itemId,itemName,itemNick,itemPrice,description,category,fastingitem,spicy,jain,quantity;
                        itemId=temp.optString("itemId");
                        itemName=temp.optString("itemName");
                        itemNick=temp.optString("itemNick");
                        itemPrice=temp.optString("itemPrice");
                        description=temp.optString("description");
                        category=temp.optString("category");
                        fastingitem=temp.optString("fastingitem");
                        spicy=temp.optString("spicy");
                        jain=temp.optString("jain");
                        quantity=temp.optString("quantity");
                        if(!itemId.equals("0")){
                            Item item=new Item(itemId,itemName,itemNick,itemPrice,description,category,fastingitem,spicy,jain,quantity);
                            mydata.add(item);
                        }else{
                            Item item=new Item("0","Item not added","Nil","0","Nil","Nil","0","0","0","0");
                            mydata.add(item);
                        }


                    }
                    itemListAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Something went wrong while fetching your item list",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorid",vendorID);
                map.put("shift",shift);
                map.put("day",day);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
