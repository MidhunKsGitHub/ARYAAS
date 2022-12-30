package com.midhun.hawkssolutions.aryaas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

public class NotifyActivity extends AppCompatActivity {
ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        img_back=findViewById(R.id.img_back);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(NotifyActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(NotifyActivity.this, true);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}