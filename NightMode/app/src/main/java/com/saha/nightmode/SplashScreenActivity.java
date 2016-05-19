package com.saha.nightmode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    final int SYSTEM_ALERT_WINDOWS_REQUEST = 1;

    Runnable exitApp = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };


    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED)
        {
                ActivityCompat.requestPermissions(getParent(),
                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                        SYSTEM_ALERT_WINDOWS_REQUEST);

        }
        else
        {
            new Handler().postDelayed(r,1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SYSTEM_ALERT_WINDOWS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.permission_granted),Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(r,1500);

                } else {

                    //denied
                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.permission_denied),Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(exitApp,3000);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
