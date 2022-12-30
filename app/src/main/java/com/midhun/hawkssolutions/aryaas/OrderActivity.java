package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.CartApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.BuyNow;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {
    TextView next;
    String UID;
    String ADDRESS;
    String AMOUT;
    String PRICE;
    String QTY;
    String NAME;
    String PID;
    ProgressDialog progress;
    List<Cart> cartList;
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        next = findViewById(R.id.next);
        note = findViewById(R.id.note);
        ADDRESS = getIntent().getExtras().getString("address_id");
        Intent in = getIntent();
        if (in.hasExtra("cart")) {
            PRICE = getIntent().getExtras().getString("product_price");
            QTY = getIntent().getExtras().getString("qty");
            NAME = getIntent().getExtras().getString("product_name");
            PID = getIntent().getExtras().getString("product_id");
        }
        UID = MidhunUtils.localData(OrderActivity.this, "login", "UID");
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(OrderActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(OrderActivity.this, true);


        loadCart();
        progress = ProgressDialog.show(OrderActivity.this, null, null, true);
        progress.setContentView(R.layout.progress_layout);
        progress.setCancelable(false);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progress.show();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(OrderActivity.this, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                if (in.hasExtra("cart")) {
                    Toast.makeText(OrderActivity.this, "buynow", Toast.LENGTH_SHORT).show();
                    buyNow();
                } else {
                    Toast.makeText(OrderActivity.this, "cart", Toast.LENGTH_SHORT).show();
                    placeOrder();
                }
            }
        });

    }

    private void placeOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PlaceOrder> call = api.placeOrder(Api.API_KEY, Api.API_KEY, UID, ADDRESS, AMOUT, note.getText().toString(),"1");
        call.enqueue(new Callback<PlaceOrder>() {
            @Override
            public void onResponse(Call<PlaceOrder> call, Response<PlaceOrder> response) {
                progress.dismiss();
                Intent in = new Intent();
                in.setClass(getApplicationContext(), GetMyOrderActivity.class);
                in.putExtra("fromorder", "yes");
                startActivity(in);
                finish();
                Toast.makeText(OrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PlaceOrder> call, Throwable t) {
                progress.dismiss();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                Toast.makeText(OrderActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buyNow() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<BuyNow> call = api.buyNow(Api.API_KEY, Api.API_KEY, UID, PID, NAME, PRICE, QTY, "", "20", "", "", "", ADDRESS, PRICE, "1");
        call.enqueue(new Callback<BuyNow>() {
            @Override
            public void onResponse(Call<BuyNow> call, Response<BuyNow> response) {
                startActivity(new Intent(getApplicationContext(), MyOrderActivity.class));

                Toast.makeText(OrderActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<BuyNow> call, Throwable t) {
                progress.dismiss();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                Toast.makeText(OrderActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCart() {
        cartList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.getCart(Api.API_KEY, Api.API_KEY, UID);
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                progress.dismiss();
                try {
                    // cartList = response.body().getCart();
                    cartList.addAll(response.body().getCart());

                    int length = cartList.size();
                    int index = length - 1;
                    for (int i = 0; i < length; i++) {
                        SharedPreferences mobilePreference = getSharedPreferences("iscart", Context.MODE_PRIVATE);
                        mobilePreference.edit().remove(cartList.get(index).getId() + cartList.get(index).getSize()).commit();
                        index--;
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(OrderActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}