package com.midhun.hawkssolutions.aryaas;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

public class FavActivity extends AppCompatActivity {
FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fragment_container=findViewById(R.id.fragment_container);

        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(FavActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(FavActivity.this, true);
    //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, FavFragment.class, null)
                    .commit();
        }
    }
}