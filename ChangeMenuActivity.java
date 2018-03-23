package mrhot.in.mrhotforbusiness.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.fragments.MenuFragment;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

import static android.R.attr.button;

/**
 * Created by jaideepsingh on 24/05/17.
 */

public class ChangeMenuActivity extends AppCompatActivity{

    Gson gson=new Gson();
    public static Spinner daySpinner;
    public static RadioButton lunchRadio,dinnerRadio;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String shift;
    String day;
    String vendorid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_your_menu);


        lunchRadio=(RadioButton)findViewById(R.id.lunchRadio);
        dinnerRadio=(RadioButton)findViewById(R.id.dinnerRadio);

        daySpinner=(Spinner)findViewById(R.id.daySpinner);
        String info=new SharedPrefManager(ChangeMenuActivity.this).getVendorInfo();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        new SharedPrefManager(this).setVendorID(vendor.getId());

        vendorid = vendor.getId();

        List<String> days=new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ChangeMenuActivity.this,android.R.layout.simple_list_item_1,days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);


        boolean truth= getIntent().hasExtra("shift");
//        Toast.makeText(getApplicationContext(),String.valueOf(truth),Toast.LENGTH_LONG).show();
        if(truth){
            String shift= String.valueOf(getIntent().getStringExtra("shift"));
            if(shift.equals("Lunch")){
                this.shift = "Lunch";
                this.day = String.valueOf(getIntent().getStringExtra("day"));
                dinnerRadio.setChecked(false);
                lunchRadio.setChecked(true);
                daySpinner.setSelection(adapter.getPosition(String.valueOf(getIntent().getStringExtra("day"))));
            }
            else{
                this.shift = "Dinner";
                this.day = String.valueOf(getIntent().getStringExtra("day"));
                lunchRadio.setChecked(false);
                dinnerRadio.setChecked(true);
                daySpinner.setSelection(adapter.getPosition(String.valueOf(getIntent().getStringExtra("day"))));
            }

        }

        lunchRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shift = "Lunch";
                    day = getDay(daySpinner.getSelectedItemPosition());
                    dinnerRadio.setChecked(false);
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    MenuFragment menuFragment=new MenuFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shift","Lunch");
                    bundle.putString("day",getDay(daySpinner.getSelectedItemPosition()));
                    menuFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container,menuFragment);
                    fragmentTransaction.commit();
                }
            }
        });
        dinnerRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shift = "Dinner";
                    day = getDay(daySpinner.getSelectedItemPosition());
                    lunchRadio.setChecked(false);
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    MenuFragment menuFragment2=new MenuFragment();
                    Bundle bundle2=new Bundle();
                    bundle2.putString("shift","Dinner");
                    bundle2.putString("day",getDay(daySpinner.getSelectedItemPosition()));
                    menuFragment2.setArguments(bundle2);
                    fragmentTransaction.replace(R.id.container,menuFragment2);
                    fragmentTransaction.commit();
                }
            }
        });


        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Monday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Monday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Monday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Monday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 1:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Tuesday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Tuesday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Tuesday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Tuesday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 2:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Wednesday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Wednesday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Wednesday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Wednesday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 3:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Thursday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Thursday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Thursday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Thursday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 4:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Friday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Friday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Friday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Friday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 5:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Saturday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Saturday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Saturday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Saturday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 6:
                        if(lunchRadio.isChecked()){
                            shift = "Lunch";
                            day = "Sunday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Lunch");
                            bundle.putString("day","Sunday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }else{
                            shift = "Dinner";
                            day = "Sunday";
                            fragmentManager=getSupportFragmentManager();
                            fragmentTransaction=fragmentManager.beginTransaction();
                            MenuFragment menuFragment=new MenuFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("shift","Dinner");
                            bundle.putString("day","Sunday");
                            menuFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,menuFragment);
                            fragmentTransaction.commit();
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(new SharedPrefManager(ChangeMenuActivity.this).getHasDownloaded())
            getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.reset){
//            Intent intent=new Intent(ChangeMenuActivity.this,AddItemActivity.class);
//            startActivity(intent);
//        }
        return true;
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

    public void addNewItem(View view){
        Intent i = new Intent(ChangeMenuActivity.this,AddNewItem.class);
        startActivity(i);
    }
    public void resetAll(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to reset the menu?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteAllItems(vendorid,shift,day);
                                finish();
                                startActivity(getIntent());
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void deleteAllItems(final String vendorID, final String shift, final String day){
        final ProgressDialog progressDialog=new ProgressDialog(ChangeMenuActivity.this);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("Removing Item.....");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(ChangeMenuActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/deleteAllItems.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray dataArray=jsonObject.optJSONArray("data");

                    Toast.makeText(ChangeMenuActivity.this,"Items deleted Successfully.",Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChangeMenuActivity.this,"Something went wrong while deleting this item.",Toast.LENGTH_LONG).show();
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
