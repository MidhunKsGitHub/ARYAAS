package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.midhun.hawkssolutions.aryaas.CategoryProductActivity;

import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.View.Category;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {
    Context ctx;
    List<Category> categoryList;


    public HomeCategoryAdapter(Context ctx, List<Category> categoryList) {
        this.ctx = ctx;
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public HomeCategoryAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.category_custom_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.HomeCategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);


        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        String img_url = base.concat(category.getImage());
        holder.name.setText(category.getName());
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
        holder.desc.setText(category.getDescription());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.putExtra("category_id", category.getId());
                in.putExtra("category_name", category.getName());
                in.setClass(ctx, CategoryProductActivity.class);
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name,desc;
        ImageView img;


        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc= itemView.findViewById(R.id.desc);
            img = itemView.findViewById(R.id.img);

        }
    }
}
