package mrhot.in.mrhotforbusiness.activities;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
public class Registorvendor2 extends AppCompatActivity {
    Button button,add,btnClosePopup;
    EditText name;
    ProgressDialog loading;
    String name2;
    public static  String[] data;
    private JSONArray result;
    ListView listView;
    ArrayList<String> item = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registorvendor2);
        button=(Button) findViewById(R.id.buttonGet);
        add = (Button) findViewById(R.id.addArea);
        listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter;
        sendRequest();
        setTitle("Select the areas you service in");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInit();
            }
        });
    }
    private void dialogInit() {
        final Dialog di = new Dialog(this);
        di.setContentView(R.layout.popupaddcity);
        di.setCancelable(true);
        di.show();

        name = (EditText) di.findViewById(R.id.textView2);
        btnClosePopup = (Button) di.findViewById(R.id.add);
        btnClosePopup.setText("Add Area");
        name.setHint("Name of Area");

        btnClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name2= name.getText().toString();
                addarea();
                recreate();
                di.dismiss();

            }
        });
    }
    public void addarea() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.ADD_AREA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registorvendor2.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registorvendor2.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name2);
                params.put("city",Registervendor.cityname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void sendRequest(){
        loading = ProgressDialog.show(this, "Please wait...", "Fetching areas in your city...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://www.mrhot.in/androidApp/getareas.php" ,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject j = new JSONObject(response);
                    JSONArray result = j.getJSONArray("result");
                    item.clear();
                    for(int i=0;i<result.length();i++)
                    {
                        JSONObject json = result.getJSONObject(i);
                        item.add(json.getString("area"));
                    }
                }
                catch (JSONException e){
                }
                findViewsById();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.multiplecityarea, item);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setAdapter(adapter);
                loading.dismiss();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SparseBooleanArray checked = listView.getCheckedItemPositions();
                        ArrayList<String> selectedItems = new ArrayList<String>();
                        for (int i = 0; i < checked.size(); i++) {
                            int position = checked.keyAt(i);
                            if (checked.valueAt(i))
                                selectedItems.add(adapter.getItem(position));
                            String[] outputStrArr = new String[selectedItems.size()];
                            for (int j = 0; j < selectedItems.size(); j++) {
                                outputStrArr[j] = selectedItems.get(j);

                            }
                           data=outputStrArr;
                            Intent intent = new Intent(getApplicationContext(),
                                    Registervendor3.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registorvendor2.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("city",Registervendor.cityname);
            return params;
        }
    };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.buttonGet);
        add = (Button) findViewById(R.id.addArea);
    }
    @Override
    public void onBackPressed()
    {
        Intent it = new Intent(Registorvendor2.this,Registervendor.class);
        startActivity(it);
    }
}