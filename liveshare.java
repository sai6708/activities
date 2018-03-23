package mrhot.in.mrhotforbusiness.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class liveshare extends AppCompatActivity {

    String vendorid="";
    Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveshare);
        String info=new SharedPrefManager(liveshare.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        vendorid=vendor.getId();
        TextView link= (TextView)findViewById(R.id.textView11);
        link.setText("https://mrhot.in/tiffin"+vendorid);
    }

    public void exit(View view)
    {
        Intent intent = new Intent(liveshare.this,MainActivity.class);
        startActivity(intent);
    }

    public void share(View view)
    {

        Intent intent = new Intent(liveshare.this,Share.class);
        intent.putExtra("flag","0");
        startActivity(intent);
    }

    public void website(View view)
    {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mrhot.in/tiffin"+vendorid));
        startActivity(browserIntent);
    }

    public void offline(View view)
    {
        Intent it = new Intent(liveshare.this,live.class);
        startActivity(it);
    }
}
