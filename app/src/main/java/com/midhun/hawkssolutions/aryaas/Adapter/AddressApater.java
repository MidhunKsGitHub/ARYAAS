package com.midhun.hawkssolutions.aryaas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.View.ShowAddress;

import java.util.List;

public class AddressApater extends RecyclerView.Adapter<AddressApater.AddressAdapterViewHolder> {
    Context ctx;
    List<ShowAddress> addressList;


    public AddressApater(Context ctx, List<ShowAddress> addressList) {
        this.ctx = ctx;
        this.addressList = addressList;

    }

    @NonNull
    @Override
    public AddressApater.AddressAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.address_custom_item, parent, false);
        return new AddressAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressApater.AddressAdapterViewHolder holder, int position) {
        ShowAddress address = addressList.get(position);

        holder.address.setText(address.getAddress());
        holder.phone.setText(address.getPhone());
        holder.city.setText(address.getCity());
        holder.pincode.setText(address.getPincode());
        holder.state.setText(address.getState());
        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent in = new Intent();
//                in.putExtra("address_id", address.getId());
//                in.setClass(ctx, OrderActivity.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class AddressAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView address, name, phone, city, pincode, state;
        CardView base;

        public AddressAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            city = itemView.findViewById(R.id.city);
            pincode = itemView.findViewById(R.id.pincode);
            state = itemView.findViewById(R.id.state);
            base = itemView.findViewById(R.id.base);
        }
    }
}
