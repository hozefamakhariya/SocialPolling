package com.techomega.socialpolling.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.techomega.socialpolling.Helper.IntentUtils;
import com.techomega.socialpolling.Helper.Prefs;
import com.techomega.socialpolling.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences mPref;
    String strid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPref = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        strid = mPref.getString(Prefs.UID,"");

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.techomega.socialpolling", PackageManager.GET_SIGNATURES);
//            for(Signature signature : info.signatures){
//                MessageDigest mmd = MessageDigest.getInstance("SHA");
//                mmd.update(signature.toByteArray());
//                Log.e("KeyHash: ", Base64.encodeToString(mmd.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e){
//
//        } catch (NoSuchAlgorithmException er){
//
//        }

        Thread timer=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);

                    if(TextUtils.isEmpty(strid)) {
                        IntentUtils.fireIntent(SplashActivity.this,SelectionActivity.class,true);
                        finish();
                    } else {
                        IntentUtils.fireIntent(SplashActivity.this,DashboardActivity.class,true);
                        finish();
                    }
                    super.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}