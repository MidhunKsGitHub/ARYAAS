package com.midhun.hawkssolutions.aryaas.Config;

import com.midhun.hawkssolutions.aryaas.Model.CartApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ChangePwdApiModel;
import com.midhun.hawkssolutions.aryaas.Model.FavApiModel;
import com.midhun.hawkssolutions.aryaas.Model.GetMyOrderApiModel;
import com.midhun.hawkssolutions.aryaas.Model.MyOrderApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProductApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProductDataApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProductImagesApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProductSizeApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProfileApiModel;
import com.midhun.hawkssolutions.aryaas.Model.SearchApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ShowAddressApiModel;
import com.midhun.hawkssolutions.aryaas.View.Address;
import com.midhun.hawkssolutions.aryaas.View.BuyNow;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.Category;
import com.midhun.hawkssolutions.aryaas.View.CategoryProducts;
import com.midhun.hawkssolutions.aryaas.View.PlaceOrder;
import com.midhun.hawkssolutions.aryaas.View.Profile;
import com.midhun.hawkssolutions.aryaas.View.ReturnOrder;
import com.midhun.hawkssolutions.aryaas.View.UserModelSignIn;
import com.midhun.hawkssolutions.aryaas.View.UserModelSignUP;
import com.midhun.hawkssolutions.aryaas.View.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "https://aryaas.hawkssolutions.com/basicapi/public/v1/";
    String  API_KEY= "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhcnlhYXMiLCJuYW1lIjoiSGF3a3MgU29sdXRpb25zIiwiYWRtaW4iOnRydWV9.BoaclKRc8ZpUbWFoQ0tv80RRncyXtRypI6jDVIJQOas";

    @FormUrlEncoded
    @POST("categories")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<List<Category>> getCategoryList(@Field("Authorization") String Authorization,
                                         @Header("Authorization") String authorization,
                                         @Field("pageLimit") String pageLimit,
                                         @Field("offst") String offst,
                                         @Field("table") String table
    );

    @FormUrlEncoded
    @POST("products/ByCombination")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ProductApiModel> getProductList(@Field("Authorization") String Authorization,
                                         @Header("Authorization") String authorization,
                                         @Field("pageLimit") String pageLimit,
                                         @Field("offset") String offset,
                                         @Field("table") String table
    );

    @FormUrlEncoded
    @POST("getProduct")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ProductDataApiModel> getProductData(@Field("Authorization") String Authorization,
                                             @Header("Authorization") String authorization,
                                             @Field("table") String table,
                                             @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getProduct")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ProductImagesApiModel> getProductImage(@Field("Authorization") String Authorization,
                                                @Header("Authorization") String authorization,
                                                @Field("table") String table,
                                                @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getProduct")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ProductSizeApiModel> getProductSize(@Field("Authorization") String Authorization,
                                             @Header("Authorization") String authorization,
                                             @Field("table") String table,
                                             @Field("id") String id
    );

    @FormUrlEncoded
    @POST("categories/getProducts")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<List<CategoryProducts>> getCategoryProductsList(@Field("Authorization") String Authorization,
                                                         @Header("Authorization") String authorization,

                                                         @Field("id") String id
    );

    @FormUrlEncoded
    @POST("common/signUP")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<UserModelSignUP> userSignUP(@Field("Authorization") String Authorization,
                                     @Header("Authorization") String authorization,
                                     @Field("contact") String contact,
                                     @Field("email") String email,
                                     @Field("first_name") String first_name,
                                     @Field("last_name") String last_name,
                                     @Field("address") String address,
                                     @Field("state") String state,
                                     @Field("location") String location,
                                     @Field("postal") String postal,
                                     @Field("latitude") String latitude,
                                     @Field("longitude") String longitude,
                                     @Field("password") String password
    );

    @FormUrlEncoded
    @POST("common/authenticate")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<List<UserModelSignIn>> userSignIn(@Field("Authorization") String Authorization,
                                           @Header("Authorization") String authorization,
                                           @Field("username") String username,
                                           @Field("password") String password
    );

    @FormUrlEncoded
    @POST("cart/add")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Cart> addCart(@Field("Authorization") String Authorization,
                       @Header("Authorization") String authorization,
                       @Field("userid") String userid,
                       @Field("productid") String productid,
                       @Field("product") String product,
                       @Field("price") String price,
                       @Field("quantity") String quantity,
                       @Field("tax") String tax,
                       @Field("category") String category,
                       @Field("psize") String psize,
                       @Field("pcolor") String pcolor,
                       @Field("combination") String combination
    );


    @FormUrlEncoded
    @POST("cart/get")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<CartApiModel> getCart(@Field("Authorization") String Authorization,
                               @Header("Authorization") String authorization,


                               @Field("userid") String id
    );

    @FormUrlEncoded
    @POST("cart/increment")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<CartApiModel> plusCart(@Field("Authorization") String Authorization,
                                @Header("Authorization") String authorization,


                                @Field("userid") String id,
                                @Field("productid") String productid,
                                 @Field("size") String size
    );

    @FormUrlEncoded
    @POST("cart/decrement")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<CartApiModel> minusCart(@Field("Authorization") String Authorization,
                                 @Header("Authorization") String authorization,


                                 @Field("userid") String id,
                                 @Field("productid") String product,
                                 @Field("size") String size
    );

    @FormUrlEncoded
    @POST("cart/remove")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<CartApiModel> removeCart(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,


                                  @Field("userid") String id,
                                  @Field("productid") String product,
                                  @Field("size") String size
    );

    @FormUrlEncoded
    @POST("user/saveAddress")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Address> addAddress(@Field("Authorization") String Authorization,
                             @Header("Authorization") String authorization,
                             @Field("name") String name,
                             @Field("contact") String contact,
                             @Field("locality") String locality,
                             @Field("postal") String postal,
                             @Field("address") String address,
                             @Field("location") String location,
                             @Field("state") String state,
                             @Field("landmark") String landmark,
                             @Field("userid") String userid,
                             @Field("latitude") String latitude,
                             @Field("longitude") String longitude

    );

    @FormUrlEncoded
    @POST("user/getAddress")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ShowAddressApiModel> getAddress(@Field("Authorization") String Authorization,
                                         @Header("Authorization") String authorization,
                                         @Field("userid") String id
    );

    @FormUrlEncoded
    @POST("cart/placeOrder")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<PlaceOrder> placeOrder(@Field("Authorization") String Authorization,
                                @Header("Authorization") String authorization,


                                @Field("id") String id,
                                @Field("address") String address,
                                @Field("amount") String amount,
                                @Field("delivery_note") String delivery_note,
                                @Field("paid") String paid
    );

    @FormUrlEncoded
    @POST("common/getOrderDetails")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<MyOrderApiModel> myOrder(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,
                                  @Field("orderid") String userid,
                                  @Field("offset") String offset,
                                  @Field("pageLimit") String pageLimit

    );
    @FormUrlEncoded
    @POST("common/getMyOrders")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<GetMyOrderApiModel> getMyOrder(@Field("Authorization") String Authorization,
                                        @Header("Authorization") String authorization,
                                        @Field("userid") String userid,
                                        @Field("offset") String offset,
                                        @Field("pageLimit") String pageLimit

    );

    @FormUrlEncoded
    @POST("wishList/add")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<WishList> addWishList(@Field("Authorization") String Authorization,
                               @Header("Authorization") String authorization,


                               @Field("userid") String id,
                               @Field("productid") String product
    );

    @FormUrlEncoded
    @POST("wishList/removeByProduct")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<WishList> removeWishList(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,


                                  @Field("userid") String id,
                                  @Field("productid") String product
    );

    @FormUrlEncoded
    @POST("wishList/get")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<FavApiModel> getWishList(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,


                                  @Field("userid") String id

    );


    @FormUrlEncoded
    @POST("common/orderReturn")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ReturnOrder> returnOrder(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,
                                  @Field("orderid") String orderid,
                                  @Field("reason") String reason

    );

    @FormUrlEncoded
    @POST("common/orderReturnSingleItem")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ReturnOrder> returnOrderOne(@Field("Authorization") String Authorization,
                                  @Header("Authorization") String authorization,
                                  @Field("cartid") String rid,
                                  @Field("status") String reason

    );

    @FormUrlEncoded
    @POST("common/profile")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ProfileApiModel> getProfile(@Field("Authorization") String Authorization,
                                     @Header("Authorization") String authorization,


                                     @Field("id") String id
    );

    @FormUrlEncoded
    @POST("common/updateProfile")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<Profile> updateProfile(@Field("Authorization") String Authorization,
                                @Header("Authorization") String authorization,


                                @Field("id") String id,
                                @Field("last_name") String last_name,
                                @Field("first_name") String first_name,
                                @Field("email") String email
    );

    @FormUrlEncoded
    @POST("common/allSearch")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<SearchApiModel> searchProduct(@Field("Authorization") String Authorization,
                                       @Header("Authorization") String authorization,

                                       @Field("offset") String offset,
                                       @Field("pageLimit") String pageLimit,
                                       @Field("key") String search,
                                       @Field("categoryvalue") String category,
                                       @Field("locationvalue") String location
    );

    @FormUrlEncoded
    @POST("common/resetPassword")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ChangePwdApiModel> changePassword(@Field("Authorization") String Authorization,
                                           @Header("Authorization") String authorization,
                                           @Field("id") String id,
                                           @Field("password") String password
    );

    @FormUrlEncoded
    @POST("common/forgetPassword")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ChangePwdApiModel> forgetPassword(@Field("Authorization") String Authorization,
                                           @Header("Authorization") String authorization,
                                           @Field("username") String username,
                                           @Field("password") String password
    );
    @FormUrlEncoded
    @POST("cart/buyNow")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<BuyNow> buyNow(@Field("Authorization") String Authorization,
                        @Header("Authorization") String authorization,


                        @Field("userid") String id,
                        @Field("productid") String productid,
                        @Field("product") String product,
                        @Field("price") String price,
                        @Field("quantity") String quantity,
                        @Field("tax") String tax,
                        @Field("category") String category,
                        @Field("size") String size,
                        @Field("psize") String psize,
                        @Field("pcolor") String pcolor,
                        @Field("address") String address,
                        @Field("amount") String amount,
                        @Field("paid") String paid
    );
}
