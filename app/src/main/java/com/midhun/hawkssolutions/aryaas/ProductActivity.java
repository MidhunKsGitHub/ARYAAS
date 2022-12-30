package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.ProductImageAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.ProductSizeAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Util.CirclePagerIndicatorDecoration;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.Util.RecyclerItemClickListener;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.Images;
import com.midhun.hawkssolutions.aryaas.View.ProductSize;
import com.midhun.hawkssolutions.aryaas.View.Result;
import com.midhun.hawkssolutions.aryaas.View.WishList;
import com.midhun.hawkssolutions.aryaas.ViewModel.ProductDataViewModel;
import com.midhun.hawkssolutions.aryaas.ViewModel.ProductImageViewModel;
import com.midhun.hawkssolutions.aryaas.ViewModel.ProductSizeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {
    TextView price, desc, product_name, addtocart, buynow;
    List<Result> resultDataList;
    List<Images> imagesList;
    List<Cart> cartList;
    List<ProductSize> productSizeList;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    ProductImageAdapter productImageAdapter;
    ProductSizeAdapter productSizeAdapter;
    LinearLayout base, loading;
    ImageView heart, img_back;
    String UID;
    String PID;
    String PRODUCT;
    String PRICE;
    String SIZE;
    int POSITION = 0;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        price = findViewById(R.id.price);
        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView2 = findViewById(R.id.recyclerview2);
        desc = findViewById(R.id.desc);
        base = findViewById(R.id.base);
        product_name = findViewById(R.id.product_name);
        addtocart = findViewById(R.id.addtocart);
        loading = findViewById(R.id.loading);
        heart = findViewById(R.id.img_noty);
        img_back = findViewById(R.id.img_back);
        buynow = findViewById(R.id.buynow);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loading.setVisibility(View.VISIBLE);
        base.setVisibility(View.GONE);
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(ProductActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(ProductActivity.this, true);
        UID = MidhunUtils.localData(ProductActivity.this, "login", "UID");
        PID = getIntent().getExtras().getString("product_id");
        PRODUCT = getIntent().getExtras().getString("product_name");
        product_name.setText(getIntent().getExtras().getString("product_name"));
        LoadProductData(getIntent().getExtras().getString("product_id"));
        LoadProductImages(getIntent().getExtras().getString("product_id"));
        LoadProductSize(getIntent().getExtras().getString("product_id"));
        //check extra
        Intent intent = getIntent();

        if (intent.hasExtra("position")) {

            POSITION = 1;
        }

        resultDataList = new ArrayList<>();

        //recyclerview1

        recyclerView1.setHasFixedSize(true);
        imagesList = new ArrayList<>();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ProductActivity.this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.addItemDecoration(new CirclePagerIndicatorDecoration());
        recyclerView1.setLayoutManager(layoutManager1);
        productImageAdapter = new ProductImageAdapter(ProductActivity.this, imagesList);
        recyclerView1.setAdapter(productImageAdapter);

        //recyclerview2

        recyclerView2.setHasFixedSize(true);
        productSizeList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(ProductActivity.this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);
        productSizeAdapter = new ProductSizeAdapter(ProductActivity.this, productSizeList);
        recyclerView2.setAdapter(productSizeAdapter);

        ////is cart

        isFav();


        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                onRecyclerViewClick(position);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHeartClick();
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UID.isEmpty()) {
                    MidhunUtils.showSnackBarMsg(getApplicationContext(), addtocart, "Login first to continue", "close");
                } else {
                    if (MidhunUtils.localData(ProductActivity.this, "iscart", PID + SIZE).equalsIgnoreCase("yes")) {
                        Intent in = new Intent();
                        in.setClass(ProductActivity.this, CartActivity.class);
                        startActivity(in);
                    } else {
                        progress = ProgressDialog.show(ProductActivity.this, null, null, true);
                        progress.setContentView(R.layout.progress_layout);
                        progress.setCancelable(false);
                        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        progress.show();
                        AddToCart();
                    }
                }
            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getApplicationContext(), BuyNowCartActivity.class);
                in.putExtra("product_id", PID);
                in.putExtra("product_name", PRODUCT);
                in.putExtra("product_price", PRICE);
                startActivity(in);

            }
        });
    }

    private void LoadProductData(String id) {

        ProductDataViewModel model = ViewModelProviders.of(ProductActivity.this).get(ProductDataViewModel.class);
        resultDataList = new ArrayList<>();
        model.getProductData(id).observe(ProductActivity.this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultDataList.addAll(results);
                price.setText("₹ " + resultDataList.get(POSITION).getPrice());
                PRICE = resultDataList.get(0).getPrice();
                SIZE = resultDataList.get(0).getId();
                desc.setText(HtmlCompat.fromHtml(resultDataList.get(0).getDescription(), 0));
                loading.setVisibility(View.GONE);
                base.setVisibility(View.VISIBLE);

            }
        });
    }

    private void LoadProductImages(String id) {
        ProductImageViewModel model2 = ViewModelProviders.of(ProductActivity.this).get(ProductImageViewModel.class);
        imagesList = new ArrayList<>();
        model2.getProductImage(id).observe(ProductActivity.this, new Observer<List<Images>>() {
            @Override
            public void onChanged(List<Images> images) {
                if (images != null) {
                    imagesList.addAll(images);
                    productImageAdapter = new ProductImageAdapter(ProductActivity.this, imagesList);
                    recyclerView1.setAdapter(productImageAdapter);
                }
            }
        });
    }

    private void LoadProductSize(String id) {
        ProductSizeViewModel model3 = ViewModelProviders.of(ProductActivity.this).get(ProductSizeViewModel.class);
        imagesList = new ArrayList<>();
        model3.getProductSize(id).observe(ProductActivity.this, new Observer<List<ProductSize>>() {
            @Override
            public void onChanged(List<ProductSize> productSizes) {

                if (productSizes != null) {
                    try {
                        productSizeList.addAll(productSizes);
                        SIZE = productSizeList.get(0).getId();
                        //iscart
                        isCart();
                        productSizeAdapter = new ProductSizeAdapter(ProductActivity.this, productSizeList);
                        recyclerView2.setAdapter(productSizeAdapter);
                    } catch (Exception e) {

                    }
                }


            }
        });
    }

    private void AddToCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Cart> call = api.addCart(Api.API_KEY, Api.API_KEY, UID, PID, PRODUCT, PRICE, "1", "", "", SIZE, "","");
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                SharedPreferences sharedPreferences = getSharedPreferences("iscart", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString(PID, "yes");
                myEdit.commit();
                Toast.makeText(ProductActivity.this, "success", Toast.LENGTH_SHORT).show();
                isCart();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isCart() {
        if (MidhunUtils.localData(ProductActivity.this, "iscart", PID + SIZE).equalsIgnoreCase("yes")) {
            addtocart.setText("Go to cart");
        } else {
            addtocart.setText("Add to cart");
        }
    }

    private void onRecyclerViewClick(int position) {
        ProgressDialog progress1 = ProgressDialog.show(ProductActivity.this, null, null, true);
        progress1.setContentView(R.layout.progress_layout);
        progress1.setCancelable(false);
        progress1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progress1.show();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                SIZE = productSizeList.get(position).getId();
                PRICE = productSizeList.get(position).getPrice();
                price.setText("₹ " + productSizeList.get(position).getPrice());
                progress1.dismiss();
                isCart();
            }
        }, 1000);
    }

    private void addHeart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.addWishList(Api.API_KEY, Api.API_KEY, UID, PID);
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ProductActivity.this, "Success added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeHeart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.removeWishList(Api.API_KEY, Api.API_KEY, UID, PID);
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isFav() {
        if (MidhunUtils.localDataCtx(ProductActivity.this, "isHeart", PID).equalsIgnoreCase("yes")) {
            heart.setImageResource(R.drawable.heart_full);

        } else {
            heart.setImageResource(R.drawable.heart_outline);
        }

    }

    private void onHeartClick() {
        if (UID.isEmpty()) {
            MidhunUtils.showSnackBarMsg(getApplicationContext(), addtocart, "Login first to continue", "close");
        } else {
            if (!MidhunUtils.localDataCtx(ProductActivity.this, "isHeart", PID).equalsIgnoreCase("yes")) {
                heart.setImageResource(R.drawable.heart_full);
                MidhunUtils.addLocalData(ProductActivity.this, "isHeart", PID, "yes");
                addHeart();
            } else {
                MidhunUtils.addLocalData(ProductActivity.this, "isHeart", PID, "no");
                heart.setImageResource(R.drawable.heart_outline);
                removeHeart();
            }
        }
    }
}