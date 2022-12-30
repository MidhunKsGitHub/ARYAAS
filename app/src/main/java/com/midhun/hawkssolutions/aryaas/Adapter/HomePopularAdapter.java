package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.midhun.hawkssolutions.aryaas.CartActivity;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.ProductActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePopularAdapter extends RecyclerView.Adapter<HomePopularAdapter.HomeCategoryViewHolder> {
    Context ctx;
    ProgressDialog progress;
    private List<Products> bannerList;
    String UID;

    public HomePopularAdapter(Context ctx, List<Products> bannerList) {
        this.ctx = ctx;
        this.bannerList = bannerList;
    }


    @NonNull
    @Override
    public HomePopularAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.popular_custom_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePopularAdapter.HomeCategoryViewHolder holder, int position) {
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");

        Products category = bannerList.get(position);


        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        String img_url = base.concat(category.getImage());


        Log.d("is image", img_url);

        holder.name.setText(category.getCombinationName());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent();
//                in.putExtra("product_id", category.getId());
//                in.putExtra("product_name", category.getName());
//                in.setClass(ctx, ProductActivity.class);
//                ctx.startActivity(in);
                showBottomDg(category.getId(),img_url,category.getCombinationName(),category.getCombinationPrice(),category.getDescription(),category.getCombinationSize(),category.getCombinationId());

            }
        });
    }

    @Override
    public int getItemCount() {

        return bannerList.size();
    }

    class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }

    private void showBottomDg(String PID, String image, String name, String price, String des,String psize,String combination) {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ctx);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_product);
        bottomSheetDialog.getEdgeToEdgeEnabled();
        ImageView img = bottomSheetDialog.findViewById(R.id.image);
        CardView add = bottomSheetDialog.findViewById(R.id.add);
        TextView name1 = bottomSheetDialog.findViewById(R.id.name);
        TextView price1 = bottomSheetDialog.findViewById(R.id.price);
        TextView desc = bottomSheetDialog.findViewById(R.id.desc);
        Glide.with(ctx)
                .load(image)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(img);

        name1.setText(name);
        price1.setText("₹ " + price);
        desc.setText(des);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    Toast.makeText(ctx, "Login to continue", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                } else {
                    progress = ProgressDialog.show(ctx, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    bottomSheetDialog.dismiss();
                    AddToCart(PID, name, price, psize, combination);
                }
            }
        });

        bottomSheetDialog.show();
    }

    private void AddToCart(String PID, String PRODUCT, String PRICE,String psize,String combination) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Cart> call = api.addCart(Api.API_KEY, Api.API_KEY, UID, PID, PRODUCT, PRICE, "1", "", "", psize, "",combination);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("iscart", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString(PID, "yes");
                myEdit.commit();

                progress.dismiss();
                Intent in = new Intent();
                in.setClass(ctx, CartActivity.class);
                ctx.startActivity(in);
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
