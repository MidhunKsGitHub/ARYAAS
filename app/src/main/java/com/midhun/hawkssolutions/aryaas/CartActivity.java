package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

public class CartActivity extends AppCompatActivity {
    FrameLayout fragment_container;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        fragment_container = findViewById(R.id.fragment_container);

        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(CartActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(CartActivity.this, true);


        UID = MidhunUtils.localData(CartActivity.this, "login", "UID");

        if (UID.isEmpty()) {
            MidhunUtils.customSnackBarLogin(fragment_container, CartActivity.this);
            finish();
        }

        //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, CartFragment.class, null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}