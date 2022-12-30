package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

public class SettingsActivity extends AppCompatActivity {
    ImageView img_back;
    LinearLayout logout, edit, changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        img_back = findViewById(R.id.img_back);
        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        changepassword = findViewById(R.id.changepswd);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(SettingsActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(SettingsActivity.this, true);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progress = ProgressDialog.show(SettingsActivity.this, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                MidhunUtils.addLocalData(getApplicationContext(), "login", "UID", "");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                        finishAffinity();
                    }
                }, 1500);
            }
        });
    }
}