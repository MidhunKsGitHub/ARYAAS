package com.midhun.hawkssolutions.aryaas.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.ProductImagesApiModel;
import com.midhun.hawkssolutions.aryaas.View.Images;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductImageViewModel extends ViewModel {
    private MutableLiveData<List<Images>> ImagesList;

    //we will call this method to get the data
    public LiveData<List<Images>>getProductImage(String id) {
        //if the list is null
        if (ImagesList == null) {
            ImagesList = new MutableLiveData<List<Images>>();

            //we will load it asynchronously from server in this method
            loadProducts(id);
        }

        //finally we will return the list
        return ImagesList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProducts(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductImagesApiModel> call = api.getProductImage(Api.API_KEY,Api.API_KEY,
                "products",id);
        Log.e("Product response",""+call.toString());

       call.enqueue(new Callback<ProductImagesApiModel>() {
           @Override
           public void onResponse(Call<ProductImagesApiModel> call, Response<ProductImagesApiModel> response) {
            if(response.code()==200) {
                ImagesList.setValue(response.body().getImages());
            }
           }

           @Override
           public void onFailure(Call<ProductImagesApiModel> call, Throwable t) {

           }
       });
    }
}
