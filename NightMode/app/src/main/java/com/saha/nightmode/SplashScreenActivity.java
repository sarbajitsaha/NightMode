package com.saha.nightmode;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity
{

    final int SYSTEM_ALERT_WINDOWS_REQUEST = 1;


    Runnable r = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firstRun();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == SYSTEM_ALERT_WINDOWS_REQUEST)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (!Settings.canDrawOverlays(this))
                {
                    Toast.makeText(this, "Permission denied.Please enable it in Settings. App will exit now", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    new Handler().postDelayed(r, 1000);
                }
            }
        }
    }

    public void firstRun()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(this))
            {
                Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName())
                );
                startActivityForResult(intent, SYSTEM_ALERT_WINDOWS_REQUEST);
            } else
            {
                new Handler().postDelayed(r, 1000);
            }
        } else
        {
            new Handler().postDelayed(r, 1000);
        }
    }
}
