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
import com.midhun.hawkssolutions.aryaas.GetMyOrderActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.MyOrder;
import com.midhun.hawkssolutions.aryaas.View.ReturnOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {
    List<MyOrder> myOrderList;
    Context ctx;
    String UID;
    ProgressDialog progress;

    public MyOrderAdapter(List<MyOrder> myOrderList, Context ctx) {
        this.myOrderList = myOrderList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyOrderAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.myorder_custom_item, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyOrderViewHolder holder, int position) {
        MyOrder myOrder = myOrderList.get(position);
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        String img_url = base.concat(myOrder.getImage());
        holder.name.setText(myOrder.getProduct());
        holder.price.setText("₹ " + myOrder.getPrice());
        holder.address.setText(myOrder.getAddress());
        holder.qty.setText(myOrder.getQuantity());


        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent in = new Intent();
//                in.putExtra("product_id",myOrderList.get(holder.getAdapterPosition()).getId());
//                in.setClass(ctx, ReturnActivity.class);
//                ctx.startActivity(in);
                ReturnOrder(holder.getAdapterPosition());
                progress = ProgressDialog.show(ctx, null, null, true);
                progress.setContentView(R.layout.progress_layout);
                progress.setCancelable(false);
                progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                progress.show();
            }
        });

        if (myOrder.getStatus().equalsIgnoreCase("1")) {
            holder.remove.setVisibility(View.VISIBLE);
            holder.person.setText("Delivery agent : " +myOrder.getDelivery_person());
            holder.out.setText("Expecting delivery time : " +myOrder.getDelivery_out_at());
        } else {
            holder.remove.setVisibility(View.GONE);
            holder.person.setVisibility(View.GONE);
            holder.out.setVisibility(View.GONE);
            Toast.makeText(ctx, "Order canceeled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, address, qty,out,person;
        ImageView img;
        CardView remove;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            address = itemView.findViewById(R.id.address);
            qty = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img);
            remove = itemView.findViewById(R.id.remove);
            person=itemView.findViewById(R.id.person);
            out=itemView.findViewById(R.id.out);
        }
    }

    private void ReturnOrder(int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ReturnOrder> call = api.returnOrderOne(Api.API_KEY, Api.API_KEY, myOrderList.get(pos).getId(), "message");
        call.enqueue(new Callback<ReturnOrder>() {
            @Override
            public void onResponse(Call<ReturnOrder> call, Response<ReturnOrder> response) {
                if(response.code()==200) {
                    Toast.makeText(ctx, "Order return request send", Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
                ctx.startActivity(new Intent(ctx, GetMyOrderActivity.class));

            }

            @Override
            public void onFailure(Call<ReturnOrder> call, Throwable t) {
                Toast.makeText(ctx, t.toString(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}
