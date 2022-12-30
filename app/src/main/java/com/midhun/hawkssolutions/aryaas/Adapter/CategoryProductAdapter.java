package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
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
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.CategoryProducts;
import com.midhun.hawkssolutions.aryaas.View.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.HomeCategoryViewHolder> {
    Context ctx;
    List<CategoryProducts> productsList;
    String UID;
    Activity activity;
    ProgressDialog progress;

    public CategoryProductAdapter(Context ctx, List<CategoryProducts> productsList) {
        this.ctx = ctx;
        this.productsList = productsList;
    }


    @NonNull
    @Override
    public CategoryProductAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.productcategory_custom_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductAdapter.HomeCategoryViewHolder holder, int position) {

        CategoryProducts products = productsList.get(position);
        if (MidhunUtils.localDataCtx(ctx, "login", "UID") != null) {
            UID = MidhunUtils.localDataCtx(ctx, "login", "UID");
        }
        if (MidhunUtils.localDataCtx(ctx, "isHeart", products.getId()).equalsIgnoreCase("yes")) {
            holder.heart.setImageResource(R.drawable.heart_full);

        } else {
            holder.heart.setImageResource(R.drawable.heart_outline);

        }
        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        String img_url = base.concat(products.getImage());
        MidhunUtils.colorFilterContex(ctx, holder.heart, R.color.grey_white);
        holder.name.setText(products.getCombinationName());
        holder.category.setText(products.getCategory());
        if (products.getCombinationPrice() == null) {
            holder.price.setText("₹ 50");
        } else {
            holder.price.setText("₹ " + products.getCombinationPrice());
        }
        Log.d("is image", img_url);

        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);
        holder.desc.setText(products.getDescription());

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Login first to continue", "Close");

                } else {
                    if (!MidhunUtils.localDataCtx(ctx, "isHeart", products.getId()).equalsIgnoreCase("yes")) {
                        holder.heart.setImageResource(R.drawable.heart_full);
                        MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Product added to wishlist", "Close");
                        MidhunUtils.addLocalData(ctx, "isHeart", products.getId(), "yes");
                        addHeart(holder.getAdapterPosition());
                    } else {
                        MidhunUtils.addLocalData(ctx, "isHeart", products.getId(), "no");
                        holder.heart.setImageResource(R.drawable.heart_outline);
                        MidhunUtils.showSnackBarMsg(ctx, holder.heart, "Product removed from wishlist", "Close");
                        removeHeart(holder.getAdapterPosition());
                    }
                }

            }
        });

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent();
//                in.putExtra("product_id", products.getId());
//                in.putExtra("product_name", products.getName());
//                in.setClass(ctx, ProductActivity.class);
//                ctx.startActivity(in);
                showBottomDg(products.getId(), img_url, products.getName(), products.getCombinationPrice(), products.getDescription(),products.getCombinationSize(),products.getCombinationId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    private void addHeart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.addWishList(Api.API_KEY, Api.API_KEY, UID, productsList.get(pos).getId());
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ctx, "Success added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeHeart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.removeWishList(Api.API_KEY, Api.API_KEY, UID, productsList.get(pos).getId());
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void AddToCart(String PID, String PRODUCT, String PRICE,String PSIZE, String COMBINATION) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Cart> call = api.addCart(Api.API_KEY, Api.API_KEY, UID, PID, PRODUCT, PRICE, "1", "", "", PSIZE, "",COMBINATION);
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

    class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, category, desc;
        ImageView img, heart;
        CardView base;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            desc = itemView.findViewById(R.id.desc);
            heart = itemView.findViewById(R.id.heart_img);
            img = itemView.findViewById(R.id.img);
            base = itemView.findViewById(R.id.base);
            category = itemView.findViewById(R.id.category);
        }
    }

}

