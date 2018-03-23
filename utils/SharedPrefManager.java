package mrhot.in.mrhotforbusiness.activities.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Anuran on 3/17/2017.
 */

public class SharedPrefManager {
    public static Context context;
    final static String FILE_NAME="main_shared_pref";
    final static String VENDOR_INFO="vendor_info";
    final static String ALL_ITEM="vendor_items";
    final static String HAS_DOWNLOADED_OR_NOT="download_status";
    final static String VENDOR_ID="vendor_id";
    final static String ORDER_INFO="order_info";
    final static String SHIFT_BOOLEAN="shift_boolean";
    final static String FIREBASE_TOKEN="firebase_token";


    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static String[] orderInfos;

    public SharedPrefManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }



    public void setVendorInfo(String info){
        editor.putString(VENDOR_INFO,info);
        editor.commit();
    }

    public String getVendorInfo(){
        String info=sharedPreferences.getString(VENDOR_INFO,null);
        return info;
    }


    public void setVendorID(String info){
        editor.putString(VENDOR_ID,info);
        editor.commit();
    }

    public String getVendorID(){
        String info=sharedPreferences.getString(VENDOR_ID,null);
        return info;
    }


    public void setAllItem(String info){
        editor.putString(ALL_ITEM,info);
        editor.commit();
    }

    public String getAllItem(){
        String info=sharedPreferences.getString(ALL_ITEM,null);
        return info;
    }

    public void clearAllItem(){
        editor.remove(ALL_ITEM);

    }


    public void setHasDownloaded(boolean hasDownloaded){
        editor.putBoolean(HAS_DOWNLOADED_OR_NOT,hasDownloaded);
        editor.commit();
    }

    public boolean getHasDownloaded(){
        boolean downloadedOrNot=sharedPreferences.getBoolean(HAS_DOWNLOADED_OR_NOT,false);
        return downloadedOrNot;
    }


    public void setFirebaseToken(String token){
        editor.putString(FIREBASE_TOKEN, String.valueOf(token));
        editor.commit();
    }
    public String getFirebaseToken() {
        String token=sharedPreferences.getString(FIREBASE_TOKEN,null);
        return token;
    }

    public void setLogin(String username, String password) {
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public String getLoginUser() {
        return sharedPreferences.getString("username", "");
    }


}
