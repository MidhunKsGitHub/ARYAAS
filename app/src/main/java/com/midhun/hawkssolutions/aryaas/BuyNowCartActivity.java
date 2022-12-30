package com.midhun.hawkssolutions.aryaas;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.ProductImagesApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Images;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuyNowCartActivity extends AppCompatActivity {
    String UID;
    String PID;
    String PRICE;
    String NAME;
    List<Images> productsList;
    TextView name, price, btn, qty;
    ImageView img, plus, minus, img_back;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now_cart);
        name = findViewById(R.id.name);
        img = findViewById(R.id.img);
        qty = findViewById(R.id.date);
        price = findViewById(R.id.price);
        btn = findViewById(R.id.btn);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                qty.setText(String.valueOf(count));
                price.setText("₹  " + String.valueOf(Integer.parseInt(PRICE) * count));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(count <= 1)) {
                    count--;
                    qty.setText(String.valueOf(count));
                    price.setText("₹  " + String.valueOf(Integer.parseInt(PRICE) * count));
                }
            }
        });
        getSupportActionBar().hide();
        PID = getIntent().getExtras().getString("product_id");
        PRICE = getIntent().getExtras().getString("product_price");
        NAME = getIntent().getExtras().getString("product_name");
        UID = MidhunUtils.localData(BuyNowCartActivity.this, "login", "UID");
        MidhunUtils.setStatusBarIcon(BuyNowCartActivity.this, true);
        MidhunUtils.changeStatusBarColor(BuyNowCartActivity.this, R.color.white);
        productsList = new ArrayList<>();
        price.setText("₹ " + PRICE);
        name.setText(NAME);
        loadProducts(PID);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getApplicationContext(), ShowAddressActivity.class);
                in.putExtra("cart", "no");
                in.putExtra("product_id", PID);
                in.putExtra("name", NAME);
                in.putExtra("price", PRICE);
                in.putExtra("qty", String.valueOf(count));
                startActivity(in);
            }
        });
    }


    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductImagesApiModel> call = api.getProductImage(Api.API_KEY,Api.API_KEY,
                "products", id);
        Log.e("Product response", "" + call.toString());

        call.enqueue(new Callback<ProductImagesApiModel>() {
            @Override
            public void onResponse(Call<ProductImagesApiModel> call, Response<ProductImagesApiModel> response) {
                if (response.code() == 200) {
                    productsList.addAll(response.body().getImages());

                    String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

                    String img_url = base.concat(productsList.get(0).getImage());
                    Glide.with(getApplicationContext())
                            .load(img_url)
                            .transition(withCrossFade())
                            .apply(new RequestOptions()
                                    //.override(60, 60)
                                    .placeholder(R.drawable.background_color_black)
                                    .error(R.drawable.background_color_black).centerCrop()
                            )
                            .into(img);
                }
            }

            @Override
            public void onFailure(Call<ProductImagesApiModel> call, Throwable t) {

            }
        });
    }
}