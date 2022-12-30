package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import com.midhun.hawkssolutions.aryaas.FavFragment;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.FavModel;
import com.midhun.hawkssolutions.aryaas.View.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {
    Context ctx;
    ProgressDialog progress;
    String UID;

    public FavAdapter(Context ctx, List<FavModel> favModelList) {
        this.ctx = ctx;
        this.favModelList = favModelList;
    }

    List<FavModel> favModelList;

    @NonNull
    @Override
    public FavAdapter.FavAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.wishlist_custom_item, parent, false);
        return new FavAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FavAdapterViewHolder holder, int position) {
        FavModel favModel = favModelList.get(position);

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");

        String img_url = base.concat(favModel.getImage());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);

        holder.price.setText(favModel.getCategory());
        holder.qty.setText("₹ " + favModel.getPrice());
        holder.name.setText(favModel.getName());

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent();
//                in.putExtra("product_id", favModel.getId());
//                in.putExtra("product_name",favModel.getName());
//                in.setClass(ctx, ProductActivity.class);
//                ctx.startActivity(in);

                showBottomDg(favModel.getId(), img_url, favModel.getName(), favModel.getPrice(), favModel.getDescription());

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                removeHeart(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return favModelList.size();
    }

    class FavAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, qty;
        ImageView img;
        CardView base, remove;

        public FavAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
            qty = itemView.findViewById(R.id.date);
            base = itemView.findViewById(R.id.base);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    private void showBottomDg(String PID, String image, String name, String price, String des) {


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
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                bottomSheetDialog.dismiss();
                AddToCart(PID, name, price);
            }
        });

        bottomSheetDialog.show();
    }

    private void AddToCart(String PID, String PRODUCT, String PRICE) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Cart> call = api.addCart(Api.API_KEY, Api.API_KEY, UID, PID, PRODUCT, PRICE, "1", "", "", "1", "", "");
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


    private void removeHeart(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<WishList> call = api.removeWishList(Api.API_KEY, Api.API_KEY, UID, favModelList.get(pos).getId());
        call.enqueue(new Callback<WishList>() {
            @Override
            public void onResponse(Call<WishList> call, Response<WishList> response) {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                MidhunUtils.addLocalData(ctx, "isHeart", favModelList.get(pos).getId(), "no");
                favModelList.remove(pos);
                notifyDataSetChanged();
               progress.dismiss();
            }

            @Override
            public void onFailure(Call<WishList> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
