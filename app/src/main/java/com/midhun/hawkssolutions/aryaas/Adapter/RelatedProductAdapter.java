package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
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
import com.midhun.hawkssolutions.aryaas.CartActivity;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.MainActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.Related;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RelatedProductAdapter extends RecyclerView.Adapter<RelatedProductAdapter.RelatedViewHolder> {
    Context ctx;
    List<Related> relatedList;
    String UID;
    ProgressDialog progress;
    Activity activity;

    public RelatedProductAdapter(Context ctx, List<Related> relatedList,Activity activity) {
        this.ctx = ctx;
        this.relatedList = relatedList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.related_custom_item, parent, false);
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, int position) {
        Related related = relatedList.get(position);
        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");
        String img_url = base.concat(related.getImage());
        holder.name.setText(related.getName());
        holder.price.setText("₹ " + related.getPrice());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UID.isEmpty()) {
                    MidhunUtils.customSnackBarLogin(view, activity);
                    Toast.makeText(ctx, "User not logged in", Toast.LENGTH_SHORT).show();
                } else {
                    progress = ProgressDialog.show(ctx, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    holder.add1.setVisibility(View.GONE);
                    holder.add.setCardBackgroundColor(ctx.getResources().getColor(R.color.background));
                    AddToCart(related.getId(), related.getName(), related.getPrice(), related.getSize(), related.getCombinationid(),view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return relatedList.size();
    }

    class RelatedViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img, add1;
        CardView add;

        public RelatedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
            add = itemView.findViewById(R.id.add);
            add1 = itemView.findViewById(R.id.add1);
        }
    }

    private void AddToCart(String PID, String PRODUCT, String PRICE, String PSIZE, String COMBINATION,View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Cart> call = api.addCart(Api.API_KEY, Api.API_KEY, UID, PID, PRODUCT, PRICE, "1", "", "", PSIZE, "", COMBINATION);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.code() == 200) {
                    Toast.makeText(ctx, "Item added to cart", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("iscart", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString(PID, "yes");
                myEdit.commit();

                progress.dismiss();
                if (ctx instanceof MainActivity) {
                    ((MainActivity) ctx).loadCart("home");
                }

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
