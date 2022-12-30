package com.midhun.hawkssolutions.aryaas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.View.ProductSize;
import com.midhun.hawkssolutions.aryaas.View.Result;

import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ProductImageAdapterViewHolder> {
    Context ctx;
    List<ProductSize> ProductSizeList;
    List<Result> resultList;
    int row_index = 0;

    public ProductSizeAdapter(Context ctx, List<ProductSize> ProductSizeList) {
        this.ctx = ctx;
        this.ProductSizeList = ProductSizeList;
    }

    @NonNull
    @Override
    public ProductSizeAdapter.ProductImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_combination_custom_item, parent, false);
        return new ProductImageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSizeAdapter.ProductImageAdapterViewHolder holder, int position) {
        ProductSize ProductSize = ProductSizeList.get(position);

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        //    String img_url = base.concat(ProductSize.getImage());
//        Glide.with(ctx)
//                .load(img_url)
//                .transition(withCrossFade())
//                .apply(new RequestOptions()
//                        //.override(60, 60)
//                        .placeholder(R.drawable.background_color_black)
//                        .error(R.drawable.background_color_black).centerCrop()
//                )
//                .into(holder.img);
        holder.price.setText("₹ " + ProductSize.getPrice());
        holder.size.setText(ProductSize.getName());


        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();

//                Intent in = new Intent();
//                in.putExtra("position",holder.getAdapterPosition());
//                in.putExtra("product_id",holder.getAdapterPosition());
//                in.putExtra("product_name",holder.getAdapterPosition());
//                in.setClass(ctx, ProductActivity.class);
//                ctx.startActivity(in);

            }

        });
        if (row_index == position) {
              holder.base.setCardBackgroundColor(ctx.getResources().getColor(R.color.purple));
            //   holder.base2.setCardBackgroundColor(ctx.getResources().getColor(R.color.white));
        } else {

            holder.base.setCardBackgroundColor(ctx.getResources().getColor(R.color.white));

        }

    }

    @Override
    public int getItemCount() {
        return ProductSizeList.size();
    }

    class ProductImageAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView price, size;
        CardView base, base2;
        LinearLayout container;

        public ProductImageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            size = itemView.findViewById(R.id.size);
            base = itemView.findViewById(R.id.base);
            base2 = itemView.findViewById(R.id.base2);
            container = itemView.findViewById(R.id.container);

        }
    }
}
