package com.midhun.hawkssolutions.aryaas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.ReturnOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReturnActivity extends AppCompatActivity {
    String UID, PID;
TextView  txt1,txt2,txt3;
private EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(ReturnActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(ReturnActivity.this, true);
        UID = MidhunUtils.localData(ReturnActivity.this, "login", "UID");
        PID = getIntent().getExtras().getString("product_id");
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MidhunUtils.showProgress(ReturnActivity.this,true);
                ReturnOrder(txt1.getText().toString());

            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MidhunUtils.showProgress(ReturnActivity.this,true);
                ReturnOrder(txt2.getText().toString());

            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MidhunUtils.showProgress(ReturnActivity.this,true);

                ReturnOrder(txt3.getText().toString());

            }
        });
    }

    private void ReturnOrder(String str) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ReturnOrder> call = api.returnOrder(Api.API_KEY,Api.API_KEY, PID, str);
        call.enqueue(new Callback<ReturnOrder>() {
            @Override
            public void onResponse(Call<ReturnOrder> call, Response<ReturnOrder> response) {
                Toast.makeText(getApplicationContext(), "Order return request send", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ReturnOrder> call, Throwable t) {
                finish();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}