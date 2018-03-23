package mrhot.in.mrhotforbusiness.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.ChangeProfile;
import mrhot.in.mrhotforbusiness.activities.utils.NotificationUtils;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

import static android.R.attr.vendor;

/**
 * Created by Anuran on 3/16/2017.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView infoText;
    Button live;
    int flag=2,action=2;
    String vendorid="";
    Gson gson=new Gson();
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    View v ;
    DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        //callfunc();

        live = (Button)findViewById(R.id.live) ;
        infoText=(TextView)findViewById(R.id.infoText);
        String info=new SharedPrefManager(HomeActivity.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        vendorid=vendor.getId();
        vendorLogin(vendor.getId());
        infoText.setText(vendor.getName()+"("+vendor.getId()+"), "+vendor.getCity());
        new SharedPrefManager(this).setVendorID(vendor.getId());

        String token = new SharedPrefManager(this).getFirebaseToken();
        Log.e("TOKEN__----------",token);
//        Toast.makeText(getApplicationContext(),"Token is : "+ token,Toast.LENGTH_LONG).show();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(NotificationConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notification
                    FirebaseMessaging.getInstance().subscribeToTopic(NotificationConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(NotificationConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };

        displayFirebaseRegId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void Gooofline(View v){
        String info=new SharedPrefManager(HomeActivity.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        vendorLogin(vendor.getId());
        infoText.setText(vendor.getName()+"("+vendor.getId()+"), "+vendor.getCity());
        new SharedPrefManager(this).setVendorID(vendor.getId());

                vendorLogin(vendor.getId());
                Log.d("Pragya","Pragya"+flag);
                if(flag==1)
                {
                    Toast.makeText(getApplicationContext(),"Please check if all conditons to activate your tiffin service has been achieved." , Toast.LENGTH_LONG).show();
                    Intent it = new Intent(HomeActivity.this,live.class);
                    startActivity(it);
                }
                else if(flag==2)
                {
                    Toast.makeText(getApplicationContext(),"Please check your Internet Connection." , Toast.LENGTH_LONG).show();

                }
                else if(action ==1 &&flag==0)
                {
                    new AlertDialog.Builder(HomeActivity.this).setMessage("Do you want to go Offline ? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    setValues(vendor.getId(),1,1,0);
                                }
                            })
                            .setNegativeButton("No",null).create().show();

                }
                else if (action==0 && flag==0)
                {

                    setValues(vendor.getId(),1,1,1);
                }

    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.main2, menu);
       // return true;
   // }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(HomeActivity.this,Profiles.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        HomeActivity ha=new HomeActivity();
        item.setChecked(true);
        drawer.closeDrawers();

        if (id == R.id.nav_seeorders) {
            // Handle the camera action
            manageOrders(v);
        } else if (id == R.id.nav_menu) {
           changeMenu(v);
        } else if (id == R.id.nav_gooffline) {
            Gooofline(v);
        } else if (id == R.id.nav_profile) {
            Intent i=new Intent(HomeActivity.this,ChangeProfile.class);
            startActivity(i);
        }else if (id == R.id.nav_share) {
            Intent i=new Intent(HomeActivity.this,Share.class);

            i.putExtra("flag","0");

            startActivity(i);
        } else if (id == R.id.nav_logout) {
            logout(v);

        }else if(id==R.id.nav_deliverytime){
            Intent i=new Intent(HomeActivity.this,EditDeliveryTimings.class);
            startActivity(i);
        }
        else if (id == R.id.nav_howstart) {
            Calling();
        }else if (id == R.id.nav_support) {
            Calling();
        }
        else if (id == R.id.soldout){
            Intent i=new Intent(HomeActivity.this,SoldDataPage.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Calling(){
        String number = "8696946385";
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_CALL, call);
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(surf);
    }

    public void callfunc(){
        String id;
        String info=new SharedPrefManager(HomeActivity.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();
        Log.v("Seeid",id);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        //TextView nav_user = (TextView)hView.findViewById(R.id.textViewnav);
        //nav_user.setText(id);
        //ImageView imageView5=(ImageView)hView.findViewById(R.id.imagenavbar);
        //String str="http://mrhot.in/androidApp/vendorLogos/"+id+".jpg";
        //Picasso.with(this).load(str).into(imageView5);

    }


    public void logout(View view){


           Log.v("See here","Working");
            new AlertDialog.Builder(this).setMessage("Do you want to Logout ")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            new SharedPrefManager(HomeActivity.this).setLogin("", "");

                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel",null).create().show();
            // Creating the AlertDialog object


    }
    public void changeMenu(View view){

        Intent intent=new Intent(HomeActivity.this,ChangeMenuActivity.class);
        startActivity(intent);
    }
    public void manageOrders(View view){

        Intent intent=new Intent(HomeActivity.this,ManageOrders.class);
        startActivity(intent);
    }

    public void solddatapage(View view){
        Intent intent = new Intent(HomeActivity.this,SoldDataPage.class);
        startActivity(intent);
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(NotificationConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

    }
    public void edittimeslot(View view)
    {
        Intent intent=new Intent(HomeActivity.this,EditDeliveryTimings.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    public void vendorLogin(final String vendorID){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/sellerApp_VendorInfo.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int active;
                    JSONArray jsonObject=new JSONArray(response);
                    JSONObject successObj=jsonObject.getJSONObject(0);
                    String success=successObj.optString("success");
                    if(success.equals("1")){
                        JSONObject vendorInfoObj=jsonObject.getJSONObject(1);

                        String priceRating=vendorInfoObj.optString("priceRating");
                        int isMenu= Integer.parseInt(vendorInfoObj.optString("isMenu"));
                        int isPacked= Integer.parseInt(vendorInfoObj.optString("isPacked"));
                        active = Integer.parseInt(vendorInfoObj.optString("active"));
                        if(active==1)
                        {
                            live.setText("Go Offline");
                            action =1;
                        }
                        else
                        {
                                            live.setText("Go Online");
                                            action=0;
                        }
                        if(isMenu==0||isPacked==0)
                        {
                            flag=1;
                        }
                        else{
                            flag=0;
                        }

                    }else{

                        Toast.makeText(HomeActivity.this,"Cannot Find Information",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("vendorID",vendorID);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public  void webpage(View view)
    {
        Intent browserIntent = new Intent(HomeActivity.this,liveshare.class);
        startActivity(browserIntent);
    }
    public void setValues(final String vendorID,final int tb3, final int tb4, final  int activate)
    {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://mrhot.in/androidApp/seller_updateVendor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.optJSONArray("data");
                    JSONObject successObj=jsonArray.getJSONObject(0);
                    String success=successObj.optString("success");
                    if(success.equals("1")){
                        if(activate==1) {
                            //Toast.makeText(HomeActivity.this,"Congratulations!! Your tiffin centre is now online",Toast.LENGTH_LONG).show();
                        Intent intent= new Intent(HomeActivity.this,liveshare.class);

                            startActivity(intent);
                        }else if(activate==0) {

                            Toast.makeText(HomeActivity.this, "The tiffin centre is now Offline.", Toast.LENGTH_LONG).show();

                        }
                        vendorLogin(vendorID);
                    }
                    else{

                        Toast.makeText(HomeActivity.this,"Cannot Find Information",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",vendorID);
                map.put("isMenu",Integer.toString(tb3));
                map.put("isPacked",Integer.toString(tb4));
                map.put("active",Integer.toString(activate));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}