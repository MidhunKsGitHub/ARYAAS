package com.midhun.hawkssolutions.aryaas.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.midhun.hawkssolutions.aryaas.View.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    Context context;
    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Category>> categoryList;

    //we will call this method to get the data
    public LiveData<List<Category>> getCategoryList() {
        //if the list is null
        if (categoryList == null) {
            categoryList = new MutableLiveData<List<Category>>();
            //we will load it asynchronously from server in this method
            loadCategories();
        }

        //finally we will return the list
        return categoryList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadCategories() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api = retrofit.create(Api.class);
//        Call<CategoryProductsApiModel> call = api.getCategoryList("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM",
//                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikhhd2tzIFNvbHV0aW9ucyIsImFkbWluIjp0cnVlfQ.B5RoeVIIAVuLBbkq_yCFoZMmy4gXmiaIF-tSM3yPzQM", "10", "0", "categories");
//        Log.e("category response", "loadCategories: " + call.toString());
//
//call.enqueue(new Callback<CategoryProductsApiModel>() {
//    @Override
//    public void onResponse(Call<CategoryProductsApiModel> call, Response<CategoryProductsApiModel> response) {
//        Log.e("hcart test", "onResponse: ");
//        try {
//            //finally we are setting the list to our MutableLiveData\
//            Log.e("Category response plus", "" + response.body());
//
//            if (response.code() == 200) {
//                Toast.makeText(context, "passed", Toast.LENGTH_SHORT).show();
//                categoryList.setValue(response.body().CList());
//            }
//        } catch (Exception e) {
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    @Override
//    public void onFailure(Call<CategoryProductsApiModel> call, Throwable t) {
//
//    }
//});
    }


}

