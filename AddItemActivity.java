package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.adapters.AutoCompleteItemAdapter;
import mrhot.in.mrhotforbusiness.activities.fragments.MenuFragment;
import mrhot.in.mrhotforbusiness.activities.models.AllItem;
import mrhot.in.mrhotforbusiness.activities.models.Item;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

/**
 * Created by Anuran on 3/23/2017.
 */

public class AddItemActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    TextView itemName,itemPrice,itemDesc,itemCategory,itemQuantity,fastingItem,spicy,jain;
    Button btnAdd,btnCancel;
    List<String> allItemNames=new ArrayList<>();
    List<Item> list=new ArrayList<>();
    String itemID,position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_layout);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.addItemET);
       // autoCompleteTextView.setText(getIntent().getExtras().getString("name"));
        itemName=(TextView)findViewById(R.id.itemName);
        itemPrice=(TextView)findViewById(R.id.itemPrice);
        itemDesc=(TextView)findViewById(R.id.itemDesc);
        itemCategory=(TextView)findViewById(R.id.itemCategory);
        itemQuantity=(TextView)findViewById(R.id.itemQuantity);
        fastingItem=(TextView)findViewById(R.id.itemFastingItem);
        spicy=(TextView)findViewById(R.id.itemSpicy);
        jain=(TextView)findViewById(R.id.itemJain);

        itemName.setText("  "+getIntent().getExtras().getString("name"));
        itemDesc.setText("  "+getIntent().getExtras().getString("desc"));
        itemPrice.setText("  \u20B9"+getIntent().getExtras().getString("price"));
        itemCategory.setText("  "+getIntent().getExtras().getString("category"));
        itemQuantity.setText("  "+getIntent().getExtras().getString("quantity"));
        if(getIntent().getExtras().getString("fastingitem").equals("1")){
            fastingItem.setText("  Yes");
        }
        if(getIntent().getExtras().getString("spicy").equals("1")){
            spicy.setText("  Yes");
        }
        if(getIntent().getExtras().getString("jain").equals("1")){
            jain.setText("  Yes");
        }


        Gson gson=new Gson();
        String info=new SharedPrefManager(this).getAllItem();
        final Item[] allItem=gson.fromJson(info,Item[].class);
        list= Arrays.asList(allItem);
        for(int i=0;i<allItem.length;i++){
            allItemNames.add(allItem[i].getItemName()+" (\u20B9"+allItem[i].getItemPrice()+")");
        }

        position=getIntent().getExtras().getString("position");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allItemNames);
        //AutoCompleteItemAdapter adapter=new AutoCompleteItemAdapter(allItem,this);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=allItemNames.indexOf(parent.getItemAtPosition(position));
                itemID=allItem[pos].getItemId();
                itemQuantity.setText("  "+allItem[pos].getQuantity());
                itemName.setText("  "+allItem[pos].getItemName());
                if(allItem[pos].getFastingitem().equals("1")){
                    fastingItem.setText("  Yes");
                }
                if(allItem[pos].getSpicy().equals("1")){
                    spicy.setText("  Yes");
                }
                if(allItem[pos].getJain().equals("1")){
                    jain.setText("  Yes");
                }
                itemPrice.setText("  \u20B9"+allItem[pos].getItemPrice());
                itemDesc.setText("  "+allItem[pos].getDescription());
                itemCategory.setText("  "+allItem[pos].getCategory());
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMenuItem(new SharedPrefManager(AddItemActivity.this).getVendorID(),itemID,getIntent().getExtras().getString("day"),getIntent().getExtras().getString("shift"),getIntent().getExtras().getString("position"));
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("shift"),Toast.LENGTH_LONG).show();

                Intent intent=new Intent(AddItemActivity.this,ChangeMenuActivity.class);
                intent.putExtra("shift", getIntent().getExtras().getString("shift"));
                intent.putExtra("day", getIntent().getExtras().getString("day"));
                startActivity(intent);
                finish();
            }
        });
    }


    public void setMenuItem(final String vendorID, final String itemID, final String day, final String shift, final String position){
        final ProgressDialog progressDialog=new ProgressDialog(AddItemActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Have patience while we are updating your menu table");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(AddItemActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/updateVendorMenu.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.optString("success");
                    Log.d("shift day hh",shift);
                    progressDialog.dismiss();
                    if(success.equals("1")){
                        Toast.makeText(AddItemActivity.this,"You successfully updated your menu item",Toast.LENGTH_LONG).show();


//                        Toast.makeText(getApplicationContext(),"add " +
//                                " = " + getIntent().getExtras().getString("shift"),Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AddItemActivity.this,ChangeMenuActivity.class);
                        intent.putExtra("shift", shift);
                        intent.putExtra("day", day);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(AddItemActivity.this,"Item already added. Select new from search.",Toast.LENGTH_LONG).show();


//                        Toast.makeText(getApplicationContext(),"add already " +
//                                " = " + getIntent().getExtras().getString("shift"),Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AddItemActivity.this,ChangeMenuActivity.class);
                        intent.putExtra("shift", shift);
                        intent.putExtra("day", day);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddItemActivity.this,"Item already added. Select new from search.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AddItemActivity.this,ChangeMenuActivity.class);
                intent.putExtra("shift", shift);
                intent.putExtra("day", day);
                startActivity(intent);
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("pos",position);
                map.put("vendorid",vendorID);
                map.put("day",day);
                map.put("shift",shift);
                map.put("itemId",itemID);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public String getDay(int pos){
        switch (pos){
            case 0:
                return "Monday";

            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return null;
    }
}
