package mrhot.in.mrhotforbusiness.activities;



import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.fragments.SoldFrag;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.Product;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class SoldDataPage extends AppCompatActivity{
    Gson gson=new Gson();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button lunch,dinner,render;
    public static String shift;
    public static List<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_data_page);
        lunch=(Button)findViewById(R.id.btnsold);
        dinner=(Button)findViewById(R.id.button7);
        render=(Button)findViewById(R.id.button9);
        String info=new SharedPrefManager(SoldDataPage.this).getVendorInfo();
        Vendor vendor=gson.fromJson(info,Vendor.class);
        new SharedPrefManager(this).setVendorID(vendor.getId());
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shift="Lunch";
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                SoldFrag soldFrag=new SoldFrag();
                Bundle bundle=new Bundle();
                soldFrag.setArguments(bundle);
                bundle.putString("shift","Lunch");
                fragmentTransaction.replace(R.id.sold_replace,soldFrag);
                fragmentTransaction.commit();
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shift="Dinner";
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                SoldFrag soldFrag=new SoldFrag();
                Bundle bundle=new Bundle();
                soldFrag.setArguments(bundle);
                bundle.putString("shift","Dinner");
                fragmentTransaction.replace(R.id.sold_replace,soldFrag);
                fragmentTransaction.commit();
            }
        });
        render.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SoldDataPage.this,Uploadsold.class);
                startActivity(in);
            }
        });

    }
}
