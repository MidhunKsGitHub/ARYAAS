package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawkssolutions.aryaas.GetMyOrderActivity;
import com.midhun.hawkssolutions.aryaas.MyOrderActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.GetMyOrder;

import java.util.List;

public class UpCommingAdapter extends RecyclerView.Adapter<UpCommingAdapter.MyOrderViewHolder> {
    List<GetMyOrder> myOrderList;
    Context ctx;
    String UID;

    public UpCommingAdapter(List<GetMyOrder> myOrderList, Context ctx) {
        this.myOrderList = myOrderList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public UpCommingAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.upcooming_custom_item, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpCommingAdapter.MyOrderViewHolder holder, int position) {
        GetMyOrder myOrder = myOrderList.get(position);
        UID = MidhunUtils.localDataCtx(ctx, "login", "UID");

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";
        String img_url = base.concat(myOrder.getImage());


        holder.name.setText(myOrder.getCartName());
        holder.status.setText(myOrder.getStatus_note());
        holder.price.setText(" ₹ " + myOrder.getTotal());
        //   MidhunUtils.gradientUi(holder.base, 30,  0xfffd1d1d,0xff833ab4);
        //  MidhunUtils.gradientUi(holder.base, 30,  0xff818181,0xff313131);

        if (position == 0) {
            holder.order.setVisibility(View.VISIBLE);
        } else {
            holder.order.setVisibility(View.GONE);

        }
        if (position == myOrderList.size() - 1) {
            holder.myorder.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.myorder.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);

        }
        holder.myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(ctx, GetMyOrderActivity.class);
                ctx.startActivity(in);
            }
        });
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)

                        .placeholder(R.drawable.arayyass)
                        .error(R.drawable.arayyass).centerCrop()
                )
                .into(holder.img1);

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.setClass(ctx, MyOrderActivity.class);
                in.putExtra("order_id", myOrder.getId());
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, status, address, qty, order, myorder;
        private ImageView img1;
        CardView base, more;
        View view;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            img1 = itemView.findViewById(R.id.img1);
            base = itemView.findViewById(R.id.base);
            more = itemView.findViewById(R.id.more);
            status = itemView.findViewById(R.id.status);
            order = itemView.findViewById(R.id.order);
            myorder = itemView.findViewById(R.id.myorder);
            view = itemView.findViewById(R.id.view);
        }
    }


}
