package mrhot.in.mrhotforbusiness.activities.adapters;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.AddItemActivity;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.ChangeMenuActivity;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Anuran on 3/18/2017.
 */

public class ItemListAdapter extends BaseAdapter {
    Context context;
    List<Item> data=new ArrayList<>();
    ItemListAdapter adapter;

    public ItemListAdapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
        this.adapter = this;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.item_list_row,null);
        TextView itemName,itemDesc,itemPrice;
        ImageView edit, delete;
        itemName=(TextView)view.findViewById(R.id.itemName);
        itemDesc=(TextView)view.findViewById(R.id.itemDesc);
        itemPrice=(TextView)view.findViewById(R.id.itemPrice);
        itemName.setText(data.get(position).getItemName());
        itemDesc.setText(data.get(position).getDescription());
        itemPrice.setText("\u20B9 "+data.get(position).getItemPrice());
        edit=(ImageView)view.findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, AddItemActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("position",position+"");
                bundle.putString("name",data.get(position).getItemName());
                bundle.putString("id",data.get(position).getItemId());
                bundle.putString("quantity",data.get(position).getQuantity());
                bundle.putString("desc",data.get(position).getDescription());
                bundle.putString("category",data.get(position).getCategory());
                bundle.putString("price",data.get(position).getItemPrice());
                bundle.putString("fastingitem",data.get(position).getFastingitem());
                bundle.putString("spicy",data.get(position).getSpicy());
                bundle.putString("jain",data.get(position).getJain());
                bundle.putString("day", ChangeMenuActivity.daySpinner.getSelectedItem().toString());
                if(ChangeMenuActivity.lunchRadio.isChecked())
                    bundle.putString("shift","Lunch");
                else
                    bundle.putString("shift","Dinner");
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        delete = (ImageView) view.findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String shift;
                if(ChangeMenuActivity.lunchRadio.isChecked())
                    shift = "Lunch";
                else
                    shift = "Dinner";

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure you want to delete this from menu?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteitem(new SharedPrefManager(context).getVendorID(),position,shift,ChangeMenuActivity.daySpinner.getSelectedItem().toString());
                                adapter.notifyDataSetChanged();
                                Intent intent=new Intent(context, ChangeMenuActivity.class);
                                ((Activity)context).finish();
                                context.startActivity(intent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });
        return view;
    }

    public void deleteitem(final String vendorID,final int position, final String shift, final String day){
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("Removing Item.....");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/deleteItem.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");



                    adapter.notifyDataSetChanged();
                    Toast.makeText(context,"Item deleted Successfully.",Toast.LENGTH_LONG).show();

                    if((progressDialog.isShowing()&&progressDialog!=null))
                        progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context,"Something went wrong while deleting this item.",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorid",vendorID);
                map.put("pos",position+"");
                map.put("shift",shift);
                map.put("day",day);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


}

