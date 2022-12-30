package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.GetMyOrderAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.MyOrderAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.GetMyOrderApiModel;
import com.midhun.hawkssolutions.aryaas.Model.MyOrderApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.GetMyOrder;
import com.midhun.hawkssolutions.aryaas.View.MyOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMyOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    String UID;
    List<GetMyOrder> myOrderList;
    GetMyOrderAdapter myOrderAdapter;
   private LinearLayout loading,base,empty;
   ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        recyclerView1 = findViewById(R.id.recyclerview1);
        loading=findViewById(R.id.loading);
        empty=findViewById(R.id.empty);
        base=findViewById(R.id.base);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(GetMyOrderActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(GetMyOrderActivity.this, true);
        UID = MidhunUtils.localData(GetMyOrderActivity.this, "login", "UID");
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = getIntent();
                if (in.hasExtra("fromorder")) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else {
                    finish();
                }
            }
        });

        recyclerView1.setHasFixedSize(true);
        myOrderList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetMyOrderActivity.this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        myOrderAdapter = new GetMyOrderAdapter(myOrderList, GetMyOrderActivity.this);
        recyclerView1.setAdapter(myOrderAdapter);
        myOrder();
    }

    private void myOrder() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<GetMyOrderApiModel> call = api.getMyOrder(Api.API_KEY,Api.API_KEY, UID, "0", "100");
        myOrderList = new ArrayList<>();
        call.enqueue(new Callback<GetMyOrderApiModel>() {
            @Override
            public void onResponse(Call<GetMyOrderApiModel> call, Response<GetMyOrderApiModel> response) {
                myOrderList = response.body().Olist();
                myOrderAdapter = new GetMyOrderAdapter(myOrderList, GetMyOrderActivity.this);
                recyclerView1.setAdapter(myOrderAdapter);
                if (myOrderList.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
                base.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<GetMyOrderApiModel> call, Throwable t) {
                if (myOrderList.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                }
          //    Toast.makeText(GetMyOrderActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = getIntent();
        if (in.hasExtra("fromorder")) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else {
            finish();
        }
    }
}