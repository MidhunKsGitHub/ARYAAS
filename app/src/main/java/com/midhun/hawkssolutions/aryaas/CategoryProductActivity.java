package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.CategoryProductAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.CategoryProducts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryProductActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    List<CategoryProducts> categoryProductsList;
    CategoryProductAdapter categoryProductAdapter;
    TextView catname;
    ImageView img_back;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        recyclerView1 = findViewById(R.id.recyclerview1);
        catname = findViewById(R.id.catname);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().hide();
        MidhunUtils.changeStatusBarColor(CategoryProductActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(CategoryProductActivity.this, true);

        progress = ProgressDialog.show(CategoryProductActivity.this, null, null, true);
        progress.setContentView(R.layout.progress_layout);
        progress.setCancelable(false);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progress.show();

        recyclerView1.setHasFixedSize(true);
        categoryProductsList = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(CategoryProductActivity.this);
        layoutManager2.setOrientation(layoutManager2.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager2);
        categoryProductAdapter = new CategoryProductAdapter(CategoryProductActivity.this, categoryProductsList);
        recyclerView1.setAdapter(categoryProductAdapter);
        catname.setText(getIntent().getExtras().getString("category_name"));
        CategoryProductssPopulate(getIntent().getExtras().getString("category_id"));

    }

    private void CategoryProductssPopulate(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<CategoryProducts>> call = api.getCategoryProductsList(Api.API_KEY, Api.API_KEY, id);
        call.enqueue(new Callback<List<CategoryProducts>>() {
            @Override
            public void onResponse(Call<List<CategoryProducts>> call, Response<List<CategoryProducts>> response) {
                categoryProductsList.addAll(response.body());
                categoryProductAdapter = new CategoryProductAdapter(CategoryProductActivity.this, categoryProductsList);
                recyclerView1.setAdapter(categoryProductAdapter);
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<CategoryProducts>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(CategoryProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}