package com.midhun.hawkssolutions.aryaas.Adapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.View.Images;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductImageAdapterViewHolder> {
    Context ctx;
    List<Images> imagesList;

    public ProductImageAdapter(Context ctx, List<Images> imagesList) {
        this.ctx = ctx;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ProductImageAdapter.ProductImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_image_custom_item, parent, false);
        return new ProductImageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageAdapter.ProductImageAdapterViewHolder holder, int position) {
     Images images = imagesList.get(position);

        String base = "https://aryaas.hawkssolutions.com/basicapi/public/";

        String img_url = base.concat(images.getImage());
        Glide.with(ctx)
                .load(img_url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.override(60, 60)
                        .placeholder(R.drawable.background_color_black)
                        .error(R.drawable.background_color_black).centerCrop()
                )
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class ProductImageAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ProductImageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }
}
