package mrhot.in.mrhotforbusiness.activities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vidhant on 4/3/17.
 */

public class SessionManagement {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "Pay Pref";
    public static final String INTRO = "No";
    public static final String LOGIN="NO";





    public SessionManagement(Context context) {

        this.context = context;

        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();

    }

    public void createSplashSession() {

        editor.putBoolean(INTRO, true);

        editor.commit();

    }
    public void createloginSession() {

        //editor.putBoolean(LOGIN, true);

        //editor.commit();
        //editor.putString(Config.PHONE_SHARED_PREF,login.phone);


    }

    public boolean introDone() {
        return pref.getBoolean(INTRO, false);

    }



}
