package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.CartAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.CartApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {

    RecyclerView recyclerView1;
    List<Cart> cartList;
    CartAdapter cartAdapter;
    String UID;
    TextView total, order;
    ImageView img_back;
    private LinearLayout loading, base, empty;
    int nowsize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        total = view.findViewById(R.id.total);
        order = view.findViewById(R.id.order);
        loading = view.findViewById(R.id.loading);
        base = view.findViewById(R.id.base);
        empty = view.findViewById(R.id.empty);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        img_back = view.findViewById(R.id.img_back);
        cartList = new ArrayList<>();


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getActivity(), ShowAddressActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);

            }
        });
        UID = MidhunUtils.localData(getActivity(), "login", "UID");

        recyclerView1.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(getActivity(), cartList);
        recyclerView1.setAdapter(cartAdapter);
        loadCart();
        return view;
    }

    public void loadCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CartApiModel> call = api.getCart(Api.API_KEY, Api.API_KEY, UID);
        call.enqueue(new Callback<CartApiModel>() {
            @Override
            public void onResponse(Call<CartApiModel> call, Response<CartApiModel> response) {
                try {
                    // cartList = response.body().getCart();
                    cartList.clear();
                    cartList.addAll(response.body().getCart());
                    cartAdapter = new CartAdapter(getActivity(), cartList);
                    recyclerView1.setAdapter(cartAdapter);
                    if (cartList.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                    }
                    loading.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                    int sum = 0;
                    for (int i = 0; i < cartList.size(); i++) {
                        //  sum = sum + Integer.parseInt(cartList.get(i).getPrice());
                        sum = i;

                    }
                    total.setText(String.valueOf(cartList.size()) + " Cart items");

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CartApiModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCart();
    }
}