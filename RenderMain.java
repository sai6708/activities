package mrhot.in.mrhotforbusiness.activities;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.fragments.MenuFragment;
import mrhot.in.mrhotforbusiness.activities.fragments.RenderOut;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

//import mrhot.in.mrhotforsell.R;

public class RenderMain extends AppCompatActivity {
    Gson gson = new Gson();
    public static Spinner sold_daySpinner;
    public static RadioButton sold_lunch,sold_dinner;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_main);
        sold_lunch = (RadioButton)findViewById(R.id.sold_lunch);
        sold_dinner = (RadioButton)findViewById(R.id.sold_dinner);
        sold_daySpinner = (Spinner)findViewById(R.id.sold_daySpinner);

        String info=new SharedPrefManager(RenderMain.this).getVendorInfo();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        new SharedPrefManager(this).setVendorID(vendor.getId());
        sold_lunch.setChecked(false);
        sold_dinner.setChecked(false);
        sold_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sold_lunch.setChecked(false);
                    //Toast.makeText(RenderMain.this, "Lunch ", Toast.LENGTH_SHORT).show();
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    RenderOut renderout = new RenderOut();
                    Bundle bundle=new Bundle();
                    bundle.putString("shift","Lunch");
                    bundle.putString("day",getDay(sold_daySpinner.getSelectedItemPosition()));
                    renderout.setArguments(bundle);
                    fragmentTransaction.replace(R.id.lunch_container,renderout);
                    fragmentTransaction.commit();
                    // Toast.makeText(RenderMain.this, "Lunch ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sold_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sold_dinner.setChecked(false);
                    fragmentManager=getSupportFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    RenderOut renderout = new RenderOut();
                    Bundle bundle1=new Bundle();
                    bundle1.putString("shift","Dinner");
                    bundle1.putString("day",getDay(sold_daySpinner.getSelectedItemPosition()));
                    renderout.setArguments(bundle1);
                    fragmentTransaction.replace(R.id.lunch_container,renderout);
                    fragmentTransaction.commit();
                }
            }
        });
        List<String> days=new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RenderMain.this,android.R.layout.simple_list_item_1,days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sold_daySpinner.setAdapter(adapter);

        sold_daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            //MenuFragment menuFragment=new MenuFragment();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Monday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Monday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;

                    case 1:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Tuesday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Tuesday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 2:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Wednesday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Wednesday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 3:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Thursday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Thursday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 4:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Friday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Friday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 5:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Saturday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Saturday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                    case 6:
                        if (sold_lunch.isChecked()) {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Lunch");
                            bundle.putString("day", "Sunday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        } else {
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            RenderOut renderOut = new RenderOut();
                            Bundle bundle = new Bundle();
                            bundle.putString("shift", "Dinner");
                            bundle.putString("day", "Sunday");
                            renderOut.setArguments(bundle);
                            fragmentTransaction.replace(R.id.lunch_container, renderOut);
                            fragmentTransaction.commit();
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return ;
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
