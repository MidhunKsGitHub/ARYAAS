package com.midhun.hawkssolutions.aryaas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

public class SplashMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        getSupportActionBar().hide();
        MidhunUtils.setStatusBarIcon(SplashMainActivity.this,true);
        MidhunUtils.changeStatusBarColor(SplashMainActivity.this,R.color.white);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        },1000);

    }
}