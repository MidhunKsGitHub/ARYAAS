package com.midhun.hawkssolutions.aryaas.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.ProductSizeApiModel;
import com.midhun.hawkssolutions.aryaas.View.ProductSize;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductSizeViewModel extends ViewModel {
    private MutableLiveData<List<ProductSize>> ProductSizeList;

    //we will call this method to get the data
    public LiveData<List<ProductSize>>getProductSize(String id) {
        //if the list is null
        if (ProductSizeList == null) {
            ProductSizeList = new MutableLiveData<List<ProductSize>>();

            //we will load it asynchronously from server in this method
            loadProducts(id);
        }

        //finally we will return the list
        return ProductSizeList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductSizeApiModel> call = api.getProductSize(Api.API_KEY,Api.API_KEY,
                "products",id);


       call.enqueue(new Callback<ProductSizeApiModel>() {
           @Override
           public void onResponse(Call<ProductSizeApiModel> call, Response<ProductSizeApiModel> response) {
               try {
                   ProductSizeList.setValue(response.body().getProductSize());
                   Log.d("size list", "onResponse: " + response.body().getProductSize().get(0).getPriceAttribute());
               }
               catch (Exception e){

               }
           }


           @Override
           public void onFailure(Call<ProductSizeApiModel> call, Throwable t) {

           }
       });
    }
}
