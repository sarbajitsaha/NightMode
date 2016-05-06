package com.saha.nightmode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    AppCompatButton btn;
    SeekBar s_red,s_green,s_blue,s_alpha;
    int r,g,b,a;
    SharedPreferences mPrefs;
    TextView r_val,g_val,b_val,a_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (AppCompatButton) findViewById(R.id.btn);
        s_red = (SeekBar) findViewById(R.id.sb_red);
        s_blue = (SeekBar) findViewById(R.id.sb_blue);
        s_green = (SeekBar) findViewById(R.id.sb_green);
        s_alpha = (SeekBar) findViewById(R.id.sb_alpha);

        r_val = (TextView) findViewById(R.id.val_red);
        g_val = (TextView) findViewById(R.id.val_green);
        b_val = (TextView) findViewById(R.id.val_blue);
        a_val = (TextView) findViewById(R.id.val_alpha);

        s_red.setMax(255);
        s_blue.setMax(255);
        s_green.setMax(255);
        s_alpha.setMax(255);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //get initial values of r,g,b
        r = mPrefs.getInt("red",0);
        g = mPrefs.getInt("green",0);
        b = mPrefs.getInt("blue",0);
        a = mPrefs.getInt("alpha",0);

        //set this progress of red,green,blue in seekbars
        s_red.setProgress(r);
        s_blue.setProgress(b);
        s_green.setProgress(g);
        s_alpha.setProgress(a);

        //set percentage for textview
        r_val.setText(((r*100)/255) + " %");
        b_val.setText(((b*100)/255) + " %");
        g_val.setText(((g*100)/255) + " %");
        a_val.setText(((a*100)/255) + " %");

        NightService.r = r;
        NightService.g = g;
        NightService.b = b;
        NightService.a = a;

        s_alpha.setOnSeekBarChangeListener(this);
        s_red.setOnSeekBarChangeListener(this);
        s_green.setOnSeekBarChangeListener(this);
        s_blue.setOnSeekBarChangeListener(this);

        stopServiceIfActive();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.getText()==getResources().getString(R.string.start))
                {
                    Intent intent = new Intent(MainActivity.this,NightService.class);
                    startService(intent);
                    btn.setText(getResources().getString(R.string.stop));
                }
                else
                {
                    Intent i=new Intent(MainActivity.this, NightService.class);
                    stopService(i);
                    btn.setText(getResources().getString(R.string.start));
                }

            }
        });

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    private void stopServiceIfActive(){
        if(NightService.STATE==NightService.ACTIVE){
            Intent i=new Intent(MainActivity.this, NightService.class);
            stopService(i);
            btn.setText(getResources().getString(R.string.start));
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar==s_alpha)
            a = progress;
        if(seekBar==s_red)
            r = progress;
        if(seekBar==s_blue)
            b = progress;
        if(seekBar==s_green)
            g = progress;

        r_val.setText(((r*100)/255) + " %");
        b_val.setText(((b*100)/255) + " %");
        g_val.setText(((g*100)/255) + " %");
        a_val.setText(((a*100)/255) + " %");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //save these values to preferences
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("red",r);
        editor.putInt("green",g);
        editor.putInt("blue",b);
        editor.putInt("alpha",a);

        NightService.r = r;
        NightService.g = g;
        NightService.b = b;
        NightService.a = a;

        Log.e("asd",r + " " + NightService.r);

        editor.apply();

        if(btn.getText()==getResources().getString(R.string.stop))
        {
            Intent intent = new Intent(MainActivity.this,NightService.class);
            startService(intent);
            btn.setText(getResources().getString(R.string.stop));
        }

    }
}
