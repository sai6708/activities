package mrhot.in.mrhotforbusiness.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;


public class Share extends AppCompatActivity {
     String vendorid="",vendorname="",link="",flag="";
    Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        //Bundle bundle=getIntent().getExtras();
        String info=new SharedPrefManager(Share.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        vendorid=vendor.getId();
        vendorname=vendor.getName();
        //vendorid=getIntent().getExtras().getString("id");
        //vendorname=getIntent().getExtras().getString("name");
        flag=getIntent().getExtras().getString("flag");
        link="https://mrhot.in/Vendor/test/vendor.php?vendor="+vendorid;
        TextView name=(TextView)findViewById(R.id.textView4);
        name.setText(vendorname);




    }

    public void share(View view)
    {

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Hey now you can place single from my "+vendorname+" in restaurant style \n\n "+link;
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, vendorname);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "SHARE"));

    }
    public void done(View view)
    {
        if(flag.equals("1"))
        {
            Intent in=new Intent(Share.this,live.class);
            startActivity(in);
        }
        else
        {
            Intent it = new Intent(Share.this,HomeActivity.class);
            startActivity(it);
        }

    }
}
