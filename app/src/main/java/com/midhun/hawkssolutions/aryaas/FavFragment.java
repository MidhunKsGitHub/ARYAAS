package com.midhun.hawkssolutions.aryaas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.midhun.hawkssolutions.aryaas.Adapter.FavAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.FavApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.FavModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FavFragment extends Fragment {
    RecyclerView recyclerView1;
    String UID;
    FavAdapter favAdapter;
    List<FavModel> favModelLsit;
    LinearLayout loading, base,empty;
    ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        loading = view.findViewById(R.id.loading);
        base = view.findViewById(R.id.base);
        empty=view.findViewById(R.id.empty);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        UID = MidhunUtils.localData(getActivity(), "login", "UID");
        recyclerView1.setHasFixedSize(true);
        favModelLsit = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        favAdapter = new FavAdapter(getActivity(), favModelLsit);
        recyclerView1.setAdapter(favAdapter);
        getWishList();
        return view;


    }

    private void getWishList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<FavApiModel> call = api.getWishList(Api.API_KEY,Api.API_KEY, UID);
        call.enqueue(new Callback<FavApiModel>() {
            @Override
            public void onResponse(Call<FavApiModel> call, Response<FavApiModel> response) {
                favModelLsit = response.body().FavList();
                favAdapter = new FavAdapter(getActivity(), favModelLsit);
                recyclerView1.setAdapter(favAdapter);
                loading.setVisibility(View.GONE);
                base.setVisibility(View.VISIBLE);
                if(favModelLsit.size()==0){
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<FavApiModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}