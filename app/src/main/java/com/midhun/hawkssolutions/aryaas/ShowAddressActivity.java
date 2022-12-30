package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.AddressApater;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Location.Location2Activity;
import com.midhun.hawkssolutions.aryaas.Model.ShowAddressApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.Util.RecyclerItemClickListener;
import com.midhun.hawkssolutions.aryaas.View.ShowAddress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowAddressActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    String UID;
    List<ShowAddress> showAddressList;
    AddressApater addressApater;
    CardView add;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);
        recyclerView1 = findViewById(R.id.recyclerview1);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add = findViewById(R.id.add);


        Intent intent = getIntent();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Location2Activity.class));
            }
        });
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(ShowAddressActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(ShowAddressActivity.this, true);
        UID = MidhunUtils.localData(ShowAddressActivity.this, "login", "UID");
        recyclerView1.setHasFixedSize(true);
        showAddressList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowAddressActivity.this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        addressApater = new AddressApater(getApplicationContext(), showAddressList);
        recyclerView1.setAdapter(addressApater);
        getAddress();

        recyclerView1.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView1, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent in = new Intent();

                in.putExtra("address_id", showAddressList.get(position).getId());
                if (intent.hasExtra("cart")) {
                    in.putExtra("product_id", getIntent().getExtras().getString("product_id"));
                    in.putExtra("product_name", getIntent().getExtras().getString("name"));
                    in.putExtra("product_price", getIntent().getExtras().getString("price"));
                    in.putExtra("qty", getIntent().getExtras().getString("qty"));
                    in.putExtra("cart", getIntent().getExtras().getString("cart"));

                }
                in.setClass(getApplicationContext(), OrderActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void getAddress() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ShowAddressApiModel> call = api.getAddress(
                Api.API_KEY,Api.API_KEY, UID);
        call.enqueue(new Callback<ShowAddressApiModel>() {
            @Override
            public void onResponse(Call<ShowAddressApiModel> call, Response<ShowAddressApiModel> response) {
                showAddressList = response.body().getStatus();
                addressApater = new AddressApater(getApplicationContext(), showAddressList);
                recyclerView1.setAdapter(addressApater);
            }

            @Override
            public void onFailure(Call<ShowAddressApiModel> call, Throwable t) {
                Toast.makeText(ShowAddressActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}