package mrhot.in.mrhotforbusiness.activities.fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.adapters.ItemListAdapter;
import mrhot.in.mrhotforbusiness.activities.adapters.Product_List_Adapter;
import mrhot.in.mrhotforbusiness.activities.adapters.SoldListAdapter;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.models.sold_item;
import mrhot.in.mrhotforbusiness.activities.utils.Product;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Jasbir_Singh on 09-06-2017.
 */

public class RenderOut extends Fragment{
    //TextView soldid,soldshift,solditem,soldvendorid;
    ListView itemList;
    Button renderbtn;
    //ItemListAdapter itemListAdapter;
    //Product_List_Adapter product_list_adapter;
    SoldListAdapter soldListAdapter;
    List<sold_item> sold_mydata=new ArrayList<>();
    //List<Product> product_data=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.render_out,container,false);
        itemList=(ListView)view.findViewById(R.id.render_list);
        renderbtn = (Button)view.findViewById(R.id.render_btn);

/*
        soldid=(TextView)view.findViewById(R.id.soldid);
        soldshift =(TextView)view.findViewById(R.id.soldshift);
        solditem=(TextView)view.findViewById(R.id.solditem);
        soldvendorid=(TextView)view.findViewById(R.id.soldvendorid);


        RequestQueue reqQue = Volley.newRequestQueue(getActivity());
        StringRequest strreq = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

*/

        //need to change here
        soldListAdapter=new SoldListAdapter(getActivity(),sold_mydata);
        //product_list_adapter.add(new Product(1,"Paneer Masala","sold","","Available"));
        itemList.setAdapter(soldListAdapter);
        String info=new SharedPrefManager(getActivity()).getVendorInfo();
        Gson gson=new Gson();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        getVendorMenu(vendor.getId(),getArguments().getString("shift"),getArguments().getString("day"));
        renderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Rendered", Toast.LENGTH_SHORT).show();
                ///Change here for button
            }
        });
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
                    ///////////////Start from here
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject temp=dataArray.getJSONObject(i);
                        String itemId,itemName,itemNick,itemPrice,description,category,fastingitem,spicy,jain,quantity;
                        itemId=temp.optString("itemId");
                        itemName=temp.optString("itemName");

                        if(!itemId.equals("0")){
                            sold_item item=new sold_item(itemId,itemName,0,null,"sold",null,"Available");
                            //mydata.add(item);
                            sold_mydata.add(item);
                        }else{
                            sold_item item=new sold_item("0","Item not added",0,null,"sold",null,"Available");
                            sold_mydata.add(item);
                        }


                    }
                    soldListAdapter.notifyDataSetChanged();
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



/*
    private ListView lvProduct;
    private Product_List_Adapter adapter;
    //private List<Product> mProductList;
    Button renderbtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.render_out,container,false);

        lvProduct = (ListView)root.findViewById(R.id.render_list);
        renderbtn = (Button)root.findViewById(R.id.renderbtn);
        //mProductList = new ArrayList<>();

        //Add Sample Data For List
        //We Can get data from database webserver here
        //mProductList.add(new Product(1,"Jasbir ","Singh ","Birdi"));

        //Init adapter

        adapter =new Product_List_Adapter(getActivity().getBaseContext(), R.layout.render_out);
        adapter.add(new Product(1,"Paneer Masala","sold","","Available"));
        adapter.add(new Product(2,"Dal Fry","sold","","Available"));
        adapter.add(new Product(3,"Jeera Dal","sold","","Available"));
        adapter.add(new Product(4,"Masala Aloo","sold","","Available"));
        adapter.add(new Product(5,"Handi Paneer","sold","","Available"));
        adapter.add(new Product(6,"Chapati (2pc)","sold","","Available"));
        adapter.add(new Product(7,"Plain Parantha","sold","","Available"));
        adapter.add(new Product(8,"Onion Parantha(1 pc)","sold","","Available"));
        adapter.add(new Product(9,"Steam Rice","sold","","Available"));
        adapter.add(new Product(10,"Fried Rice","sold","","Available"));
        adapter.add(new Product(11,"Aloo Parantha(1 pc)","sold","","Available"));
        adapter.add(new Product(12,"Masala Chaas","sold","","Available"));
        adapter.add(new Product(13,"Jaljeera","sold","","Available"));
        adapter.add(new Product(14,"Gulab Jamun","sold","","Available"));
        adapter.add(new Product(15,"Boondi Raita","sold","","Available"));
        adapter.add(new Product(16,"Salad Cup","sold","","Available"));
        adapter.add(new Product(17,"Onion Lemon Salad","sold","","Available"));
        adapter.add(new Product(18,"Aloo Paties","sold","","Available"));
        adapter.add(new Product(18,"Maggie 2 Minutes","sold","","Available"));
        adapter.add(new Product(18,"Fried Maggie","sold","","Available"));

        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Displaying whick item clicked
                Toast.makeText(getActivity().getBaseContext(), "Clicked Product Id ="+ view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        renderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Rendered", Toast.LENGTH_SHORT).show();
            }
        });
        return  root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Render Out Of Stock");
    }
    */
}
