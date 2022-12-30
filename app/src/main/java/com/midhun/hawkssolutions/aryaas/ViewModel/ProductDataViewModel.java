package com.midhun.hawkssolutions.aryaas.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.ProductDataApiModel;
import com.midhun.hawkssolutions.aryaas.View.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDataViewModel extends ViewModel {
    private MutableLiveData<List<Result>> resultList;

    //we will call this method to get the data
    public LiveData<List<Result>>getProductData(String id) {
        //if the list is null
        if (resultList == null) {
            resultList = new MutableLiveData<List<Result>>();

            //we will load it asynchronously from server in this method
            loadProducts(id);
        }

        //finally we will return the list
        return resultList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductDataApiModel> call = api.getProductData(Api.API_KEY,Api.API_KEY,
                "products",id);
        Log.e("Product response",""+call.toString());

       call.enqueue(new Callback<ProductDataApiModel>() {
           @Override
           public void onResponse(Call<ProductDataApiModel> call, Response<ProductDataApiModel> response) {
               resultList.setValue(response.body().getResult());
           }

           @Override
           public void onFailure(Call<ProductDataApiModel> call, Throwable t) {

           }
       });
    }
}
