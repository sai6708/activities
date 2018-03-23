package mrhot.in.mrhotforbusiness.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import mrhot.in.mrhotforbusiness.R;

public class Contactus extends AppCompatActivity {

    Button call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        call = (Button) findViewById(R.id.contactushere);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Seethis","calling");
                String number = "8696946385";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                if (ActivityCompat.checkSelfPermission(Contactus.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(surf);
            }
        });


    }
}
