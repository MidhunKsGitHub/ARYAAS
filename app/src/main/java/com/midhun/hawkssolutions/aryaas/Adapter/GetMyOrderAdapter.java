package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.MyOrderActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.GetMyOrder;
import com.midhun.hawkssolutions.aryaas.View.ReturnOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMyOrderAdapter extends RecyclerView.Adapter<GetMyOrderAdapter.MyOrderViewHolder> {
    List<GetMyOrder> myOrderList;
    Context ctx;
    String UID;
    ProgressDialog progress;

    public GetMyOrderAdapter(List<GetMyOrder> myOrderList, Context ctx) {
        this.myOrderList = myOrderList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public GetMyOrderAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.getmyorder_custom_item, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetMyOrderAdapter.MyOrderViewHolder holder, int position) {
        GetMyOrder myOrder = myOrderList.get(position);
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";
        String img_url = base.concat(myOrder.getImage());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);
        holder.name.setText(myOrder.getCartName());
        holder.address.setText(myOrder.getAddress());
        holder.price.setText("₹ " + myOrder.getTotal());
        holder.qty.setText(myOrder.getDate());
        holder.status.setText(myOrder.getStatus_note());
        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(ctx, MyOrderActivity.class);
                in.putExtra("order_id", myOrder.getId());
                ctx.startActivity(in);
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
                ReturnOrder(holder.getAdapterPosition());

            }
        });

        if(myOrder.getStatus().equalsIgnoreCase("-1")){
            holder.cancel.setVisibility(View.GONE);
        }
        else{
            holder.cancel.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, address, qty, status;
        CardView base, cancel;
        ImageView img;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            address = itemView.findViewById(R.id.address);
            qty = itemView.findViewById(R.id.date);
            base = itemView.findViewById(R.id.base);
            img = itemView.findViewById(R.id.img);
            status = itemView.findViewById(R.id.status);
            cancel = itemView.findViewById(R.id.cancel);
        }
    }

    private void ReturnOrder(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ReturnOrder> call = api.returnOrder(Api.API_KEY, Api.API_KEY, myOrderList.get(pos).getId(), "message");
        call.enqueue(new Callback<ReturnOrder>() {
            @Override
            public void onResponse(Call<ReturnOrder> call, Response<ReturnOrder> response) {
                Toast.makeText(ctx, "Order cancel request send", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ReturnOrder> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
