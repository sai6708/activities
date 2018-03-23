package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.services.DownloadItemService;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class AddNewItem extends AppCompatActivity {
    Spinner categorySpn;
    EditText dishNameET,descET,qtyET,priceET;
    CheckBox fastCB, jainCB, spicyCB;
    Button back, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        categorySpn = (Spinner) findViewById(R.id.categorySpn);

        List<String> categories=new ArrayList<>();
        categories.add("Thali");
        categories.add("Breads & Rice");
        categories.add("Vegetables");
        categories.add("Sides");
        categories.add("Sweets&Beverages");
        categories.add("Snacks");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddNewItem.this,android.R.layout.simple_list_item_1,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpn.setAdapter(adapter);

        dishNameET = (EditText) findViewById(R.id.dishNameET);
        descET = (EditText) findViewById(R.id.descET);
        qtyET = (EditText) findViewById(R.id.qtyET);
        priceET = (EditText) findViewById(R.id.priceET);

        fastCB = (CheckBox) findViewById(R.id.fastCB);
        jainCB = (CheckBox) findViewById(R.id.jainCB);
        spicyCB = (CheckBox) findViewById(R.id.spicyCB);

        back = (Button) findViewById(R.id.backBtn);
        add = (Button) findViewById(R.id.addBtn);
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(AddNewItem.this, ChangeMenuActivity.class);
                 startActivity(i);
             }
         });


        final String name = String.valueOf(dishNameET.getText());
        final String quantity = String.valueOf(qtyET.getText());
        final String description = String.valueOf(descET.getText());
        final String price = String.valueOf(priceET.getText());
        final String fasting, jain, spicy;
        if(fastCB.isChecked()){
            fasting = "1";
        }else{
            fasting = "0";
        }
        if(jainCB.isChecked()){
            jain = "1";
        }else{
            jain = "0";
        }
        if(spicyCB.isChecked()){
            spicy = "1";
        }else{
            spicy = "0";
        }
        final String category = String.valueOf(categorySpn.getSelectedItem());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = String.valueOf(dishNameET.getText());
                final String quantity = String.valueOf(qtyET.getText());
                final String description = String.valueOf(descET.getText());
                final String price = String.valueOf(priceET.getText());
                final String fasting, jain, spicy;
                if(fastCB.isChecked()){
                    fasting = "1";
                }else{
                    fasting = "0";
                }
                if(jainCB.isChecked()){
                    jain = "1";
                }else{
                    jain = "0";
                }
                if(spicyCB.isChecked()){
                    spicy = "1";
                }else{
                    spicy = "0";
                }
                final String category = String.valueOf(categorySpn.getSelectedItem());
                setMenuItem(name, quantity, category, price, fasting, jain, spicy, description);
            }
        });


    }

    public void setMenuItem(final String name, final String quantity, final String category, final String price, final String fasting, final String jain, final String spicy , final String description ){
        final ProgressDialog progressDialog=new ProgressDialog(AddNewItem.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Have patience while we are updating your menu table");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(AddNewItem.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/addItem.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.optString("success");
                    progressDialog.dismiss();
                    if(success.equals("1")){

                        Toast.makeText(AddNewItem.this,"You successfully updated your menu item",Toast.LENGTH_LONG).show();

                        new SharedPrefManager(AddNewItem.this).clearAllItem();
                        startService(new Intent(getApplicationContext(),DownloadItemService.class));

                        Intent i2 = new Intent(AddNewItem.this, ChangeMenuActivity.class);
                        startActivity(i2);

                    }else{
//                        Toast.makeText(AddNewItem.this,name+" "+quantity+" "+category+" "+price+" "+fasting+" "+jain+" "+spicy,Toast.LENGTH_LONG).show();
//                        Toast.makeText(AddNewItem.this,"Item cannot be added right now.",Toast.LENGTH_LONG).show();
                        new SharedPrefManager(AddNewItem.this).clearAllItem();
                        startService(new Intent(getApplicationContext(),DownloadItemService.class));

                        Intent i2 = new Intent(AddNewItem.this, ChangeMenuActivity.class);
                        startActivity(i2);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddNewItem.this,"Item cannot be added.",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("name",name);
                map.put("quantity",quantity);
                map.put("category",category);
                map.put("price",price);
                map.put("fasting",fasting);
                map.put("jain",jain);
                map.put("spicy",spicy);
                map.put("description",description);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

}
