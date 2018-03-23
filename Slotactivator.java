package mrhot.in.mrhotforbusiness.activities;

/**
 * Created by sai on 23/11/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;

public class Slotactivator extends AppCompatActivity {
    Switch switch1;
    int flag=0,slot;
    String shift,id,sendendtime;
    EditText name;
    TimePicker pictimeend;
    TextView  endtime;
    Button done,save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slotactivator);

        name=(EditText)findViewById(R.id.editText);
        done=(Button) findViewById(R.id.done);
        save=(Button) findViewById(R.id.button8);
        endtime=(TextView) findViewById(R.id.textView19);
        pictimeend=(TimePicker) findViewById(R.id.timePicker);
        name.setText(getIntent().getStringExtra("name"));
        String showtime=getIntent().getStringExtra("time");
        endtime.setText(timeformatconverter(showtime));
        sendendtime=showtime;
        flag= getIntent().getIntExtra("active",0);
        System.out.println(flag);
        slot=getIntent().getIntExtra("slot",0);
        id=getIntent().getStringExtra("id");
        shift=getIntent().getStringExtra("shift");
        done.setVisibility(View.GONE);
        pictimeend.setVisibility(View.GONE);
       // final TextView deactivate=(TextView) findViewById(R.id.textView10);


        final TextView activate=(TextView)findViewById(R.id.textView12);
        switch1= (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener (null);
        if(flag==1)
        {
            activate.setTextColor(Color.parseColor("#FA2F04"));
            switch1.setChecked(true);
        }
        else
        {
            switch1.setChecked(false);
        }
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    activate.setTextColor(Color.parseColor("#FA2F04"));
                    flag=1;

                }
                else
                {
                    activate.setTextColor(Color.parseColor("#FF424242"));
                    flag=0;

                }
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void done(View view)
    {
        save.setVisibility(View.VISIBLE);
        done.setVisibility(View.GONE);
        pictimeend.setVisibility(View.GONE);

        String hourString;
        if (pictimeend.getHour() < 10)
        { hourString = "0" + pictimeend.getHour();}
        else
        { hourString = "" +pictimeend.getHour();}

        String minuteSting;
        if (pictimeend.getMinute() < 10)
        { minuteSting = "0" + pictimeend.getMinute();}
        else
        { minuteSting = "" +pictimeend.getMinute();}
        sendendtime=hourString+""+minuteSting;
        endtime.setText(timeformatconverter(hourString+""+minuteSting));

    }

    public void time (View view)
    {
        done.setVisibility(View.VISIBLE);
        pictimeend.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);
    }

    public void back(View view)
    {
        Intent intent=new Intent(Slotactivator.this,EditDeliveryTimings.class);
        startActivity(intent);
    }
    public void save(View view) {



        if (name.getText().toString().equals("") || sendendtime.toString().equals("")) {
            Toast.makeText(Slotactivator.this, "Enter all the details", Toast.LENGTH_LONG).show();
        } else {

            final ProgressDialog progress;
            progress = new ProgressDialog(Slotactivator.this);
            progress.setMessage("Please Wait ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

            RequestQueue requestQueue = Volley.newRequestQueue(Slotactivator.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/slotactivate.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String s=jsonObject.getString("server");
                        progress.dismiss();
                        Toast.makeText(Slotactivator.this,s+"",Toast.LENGTH_LONG).show();
                        flag=0;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", name.getText().toString());
                    map.put("time", sendendtime);
                    map.put("shift",shift);
                    map.put("slot", slot+"");
                    map.put("activate",flag+"");
                    map.put("id",id);
                    return map;
                }

            };
            requestQueue.add(stringRequest);

        }
    }

    public String timeformatconverter(String time)

    {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
            final Date dateObj;
            dateObj = sdf.parse(time);
           time=new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
