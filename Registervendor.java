package mrhot.in.mrhotforbusiness.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;


public class Registervendor extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    private Spinner spinner;
    Button btnClosePopup;
    Button btnCreatePopup;

    public static String cityname;
    String name1;

    EditText name;
    EditText tiffinname;
    EditText username;
    EditText phone;
    EditText password;
    public static String tiffinname1 = null;
    public static String username1,username2;
    public static String phone1;
    public static String password1;
    int value =2;


    //An ArrayList for Spinner Items
    private ArrayList<String> students;

    //JSON Array
    private JSONArray result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registervendor);
        students = new ArrayList<String>();
        students.add("Select a City");
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        tiffinname = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        setTitle("Register Yourself");

        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.spinner);


        //Adding an Item Selected Listener to our Spinner
        //As we have implemenmented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //Initializing TextViews
        //textViewCourse = (TextView) findViewById(R.id.textViewcity);
        //textViewSession = (TextView) findViewById(R.id.textViewSession);

        //This method will fetch the data from the URL
       getData();
    }
     private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,config.DATA_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray(config.JSON_ARRAY);
                            getid(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getid(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the city to array list
                students.add(json.getString("city"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(Registervendor.this, android.R.layout.simple_spinner_dropdown_item, students));
    }

    //Method to get city name of a particular position
    private String getcity(int position) {
        String city = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            city = json.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return city;
    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to variables for a selected item
        if(position==0)
        {
            cityname=null;
        }
        cityname = getcity(position-1);
        //Toast.makeText(mrhot.in.mrhotforbusiness.activities.Registervendor.this, cityname, Toast.LENGTH_SHORT).show();

        //textViewCourse.setText(getcity(position));
    }


    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        cityname = null;
    }

    public void popup(View view) {
        dialogInit();


    }
    private void dialogInit() {
        final Dialog di = new Dialog(this);
        di.setContentView(R.layout.popupaddcity);
        di.setCancelable(true);
        di.show();

        name = (EditText) di.findViewById(R.id.textView2);
        btnClosePopup = (Button) di.findViewById(R.id.add);

        btnClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1= name.getText().toString();
                addcity();
                recreate();
                di.dismiss();


            }
        });
    }
    public void addcity() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registervendor.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registervendor.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void next(View view) {
int flag =0;
        username2 = username.getText().toString();
        password1 = password.getText().toString();
        tiffinname1 = tiffinname.getText().toString();
        phone1 = phone.getText().toString();
        if(TextUtils.isEmpty(cityname))
        {
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please select a city");
            flag=1;
        }
        if(TextUtils.isEmpty(password1))
        {
            password.setError("Field can't be empty.");
            flag=1;
        }
        if(TextUtils.isEmpty(tiffinname1))
        {
            tiffinname.setError("Name field can't be empty.");
            flag=1;

        }
        if(TextUtils.isEmpty(phone1))
        {
            phone.setError("Phone field can't be empty.");
            flag=1;

        }
        if (TextUtils.isEmpty(username2)) {
            Toast.makeText(this, "Please enter your details ", Toast.LENGTH_SHORT).show();
            username.setError("Username field can't be empty.");
            flag=1;

        }
        if(flag==0) {
            checkUsername(username2);
        }


    }
    void checkUsername(final String user)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,config.User_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("data");
                            JSONObject jt = result.getJSONObject(0);
                            value = Integer.parseInt(jt.getString("success"));
                            if(value==0)
                            {
                                Intent intent = new Intent(Registervendor.this, Registorvendor2.class);
                                startActivity(intent);
                            }
                            else if(value==1)
                            {
                                username.setError("Username already exsits.");
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}








