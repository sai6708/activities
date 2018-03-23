package mrhot.in.mrhotforbusiness.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import mrhot.in.mrhotforbusiness.R;
import mrhot.in.mrhotforbusiness.activities.models.Vendor;
import mrhot.in.mrhotforbusiness.activities.utils.SharedPrefManager;

public class AddImage extends AppCompatActivity implements View.OnClickListener{

    Gson gson=new Gson();
    ImageView imageView;
    Button btn1,btn2;
    private final int Image_req=1;
    String id;
    Bitmap bitmap;
    final String PhpUrl="http://mrhot.in/androidApp/uploadimages.php";
    Context context=AddImage.this;

    public AddImage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        String info=new SharedPrefManager(AddImage.this).getVendorInfo();
        final Vendor vendor=gson.fromJson(info,Vendor.class);
        id=vendor.getId();
        Log.v("Checkingid",id);

        imageView = (ImageView)findViewById(R.id.AddImgImage);
        Downloadimage(imageView,id,AddImage.this);
        btn1=(Button)findViewById(R.id.AddImgSave);
        btn2=(Button)findViewById(R.id.AddImgChange);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
       }

    @Override
    public void onClick(View v) {

     switch (v.getId()){

         case R.id.AddImgChange :   Selecting();
                                    break;
         case R.id.AddImgSave:      Uploading();
                                    break;

     }

    }

    public static void Downloadimage(ImageView imageView,String id,Context context){

        String str="http://mrhot.in/androidApp/vendorLogos/"+id+".jpg";
        Picasso.with(context).load(str).into(imageView);

      }

    public Bitmap StringToBitMap(String encodedString) {
        Log.v("STRingasd",encodedString);
        try {
            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inJustDecodeBounds = true;
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public void Uploading(){

       final ProgressDialog progress;
        progress=new ProgressDialog(AddImage.this);
        progress.setMessage("Uploading image");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, PhpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String Res=jsonObject.getString("response");
                    String rese;
                    rese = Res.toString();
                    Log.v("See this one ",rese);
                    progress.dismiss();
                    Toast.makeText(AddImage.this,rese+"",Toast.LENGTH_LONG).show();
                    imageView.setImageResource(0);
                    Downloadimage(imageView,id,AddImage.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddImage.this,"Failed to upload the image.",Toast.LENGTH_LONG).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                map.put("id",id);
                map.put("image",ImageToString(bitmap));
                return map;
            }

        };

        MySingleton.getInstances(AddImage.this).addToRequestQueue(stringRequest);
    }

    private String ImageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);
    }



    public void Selecting(){

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Image_req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();
            try {

                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setMinimumHeight(200);
                imageView.setMinimumWidth(200);
                imageView.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
