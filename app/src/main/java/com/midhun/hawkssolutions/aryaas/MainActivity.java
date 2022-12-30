package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.CartApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FrameLayout fragment_container;
    ImageView img_home, img_cart, img_heart, img_user, img;
    String UID;
    List<Cart> cartList;
    TextView items;
    CardView iscart;
    int isItemcart = 0;
    LinearLayout home, cart, fav, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_container = findViewById(R.id.fragment_container);
        home = findViewById(R.id.home);
        cart = findViewById(R.id.cart);
        fav = findViewById(R.id.fav);
        account = findViewById(R.id.account);
        img_home = findViewById(R.id.img_home);
        img_heart = findViewById(R.id.img_heart);
        img_cart = findViewById(R.id.img_cart);
        img_user = findViewById(R.id.img_user);
        items = findViewById(R.id.items);
        cartList = new ArrayList<>();
        img = findViewById(R.id.img);
        iscart = findViewById(R.id.iscart);
        items.setVisibility(View.INVISIBLE);
        iscart.setVisibility(View.GONE);

        //Hide action bar
        getSupportActionBar().hide();
        MidhunUtils.round(items, 0xff000000, 0xff000000, 360);
        //strict mode vm policy
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());
        //statusbar
        MidhunUtils.setStatusBarIcon(MainActivity.this, true);
        MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.white);
        UID = MidhunUtils.localData(MainActivity.this, "login", "UID");

        //image filter
        MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.purple);
        MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
        MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
        MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);
        MidhunUtils.colorFilter(MainActivity.this, img, R.color.white);

        img_home.setImageResource(R.drawable.home);
        img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
        img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
        img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
        //add fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, HomeFragment.class, null)
                    .commit();
        }

        iscart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, HomeFragment.class, null)
                        .addToBackStack(null)
                        .commit();

                MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.purple);
                MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
                MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
                MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);


                img_home.setImageResource(R.drawable.home);
                img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
                if (isItemcart != 0) {
                    iscart.setVisibility(View.VISIBLE);
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.customSnackBarLogin(view, MainActivity.this);

                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, CartFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);


                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                    img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
                    iscart.setVisibility(View.GONE);
                }
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.customSnackBarLogin(view, MainActivity.this);
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, FavFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.grey_black);

                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart));
                    img_user.setImageDrawable(getDrawable(R.drawable.user_outline));
                    iscart.setVisibility(View.GONE);
                }
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MidhunUtils.localData(MainActivity.this, "login", "UID").isEmpty()) {
                    MidhunUtils.customSnackBarLogin(view, MainActivity.this);
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, UserFragment.class, null)
                            .addToBackStack(null)
                            .commit();

                    MidhunUtils.colorFilter(MainActivity.this, img_user, R.color.purple);
                    MidhunUtils.colorFilter(MainActivity.this, img_cart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_heart, R.color.grey_black);
                    MidhunUtils.colorFilter(MainActivity.this, img_home, R.color.grey_black);

                    img_home.setImageResource(R.drawable.home_outline);
                    img_cart.setImageDrawable(getDrawable(R.drawable.cart_outline));
                    img_heart.setImageDrawable(getDrawable(R.drawable.heart_outline));
                    img_user.setImageDrawable(getDrawable(R.drawable.user));
                    iscart.setVisibility(View.GONE);
                }
            }
        });

        //cart load
        loadCart("home");

    }

    public void loadCart(String where) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.getCart(Api.API_KEY, Api.API_KEY, UID);
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                try {
                    cartList.clear();
                    cartList.addAll(response.body().getCart());
                } catch (Exception e) {

                }
                int length = cartList.size();
                int index = length - 1;
                int count = 0;
                for (int i = 0; i < length; i++) {
                    count++;
                    if (cartList.get(index).getId().equalsIgnoreCase("")) {

                    }
                    index--;
                }
                if (count > 0) {
                    isItemcart = cartList.size();
                    items.setText(String.valueOf(count));
                    items.setVisibility(View.VISIBLE);
                    if (where.equalsIgnoreCase("home")) {

                        iscart.setVisibility(View.VISIBLE);
                    } else {
                        iscart.setVisibility(View.GONE);
                    }
                } else {
                    isItemcart=0;
                    iscart.setVisibility(View.GONE);
                    items.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {

    }

}