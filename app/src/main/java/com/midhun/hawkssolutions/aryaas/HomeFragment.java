package com.midhun.hawkssolutions.aryaas;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.midhun.hawkssolutions.aryaas.Adapter.HomeBannerAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.HomeCategoryAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.HomePopularAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.HomeProductAdapter;
import com.midhun.hawkssolutions.aryaas.Adapter.UpCommingAdapter;
import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.CartApiModel;
import com.midhun.hawkssolutions.aryaas.Model.GetMyOrderApiModel;
import com.midhun.hawkssolutions.aryaas.Model.ProductApiModel;
import com.midhun.hawkssolutions.aryaas.Util.CirclePagerIndicatorDecoration;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Cart;
import com.midhun.hawkssolutions.aryaas.View.Category;
import com.midhun.hawkssolutions.aryaas.View.GetMyOrder;
import com.midhun.hawkssolutions.aryaas.View.Products;
import com.midhun.hawkssolutions.aryaas.ViewModel.ProductsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    ImageView img_back, img_search, img_noty, img_search2;
    CardView search;
    List<GetMyOrder> myOrderList;
    List<Category> categoryList;
    List<Cart> cartList;
    List<Category> bannerList;
    List<Products> productsList;
    List<Products> popularList;
    HomePopularAdapter homePopularAdapter;
    List<Products> productsListPagination;
    HomeCategoryAdapter homeCategoryAdapter;
    UpCommingAdapter upCommingAdapter;
    HomeBannerAdapter homeBannerAdapter;
    HomeProductAdapter homeProductAdapter;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout loading;

    private NestedScrollView base;
    TextView load;
    TextView qty;

    String offset;
    int count = 0;
    ProgressBar pb;
    String UID;
    ShimmerFrameLayout shimmerFrameLayout1, shimmerFrameLayout2, shimmerFrameLayout, shimmerFrameLayout4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_back = view.findViewById(R.id.img_back);
        img_search = view.findViewById(R.id.img_search);
        img_search2 = view.findViewById(R.id.img_search2);
        img_noty = view.findViewById(R.id.img_noty);
        search = view.findViewById(R.id.search);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView3 = view.findViewById(R.id.recyclerview3);
        recyclerView4 = view.findViewById(R.id.recyclerview4);
        recyclerView5 = view.findViewById(R.id.recyclerview5);
        base = view.findViewById(R.id.base);
        load = view.findViewById(R.id.load);
        qty = view.findViewById(R.id.date);
        loading = view.findViewById(R.id.loading);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout1 = view.findViewById(R.id.shimmer_view_container1);
        shimmerFrameLayout2 = view.findViewById(R.id.shimmer_view_container2);
        shimmerFrameLayout4 = view.findViewById(R.id.shimmer_view_container4);

        pb = view.findViewById(R.id.pb);
        base.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        UID = MidhunUtils.localData(getActivity(), "login", "UID");

//        String first = "RYAAS";
//        String next = "<font color='#ff3344'>A</font>";
//        qty.setText(Html.fromHtml(next + first));


        //cart
        cartList = new ArrayList<>();

        //upcomming product

        recyclerView5.setHasFixedSize(true);
        myOrderList = new ArrayList<>();
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity());
        layoutManager5.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView5.setLayoutManager(layoutManager5);
        upCommingAdapter = new UpCommingAdapter(myOrderList, getActivity());
        recyclerView5.setAdapter(upCommingAdapter);
        myOrder();
        //category populate


        recyclerView2.setHasFixedSize(true);
        categoryList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager);
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), categoryList);
        recyclerView2.setAdapter(homeCategoryAdapter);
        //CategoriesPopulate();
        loadCategories();


        //banner populate
        recyclerView1.setHasFixedSize(true);
        bannerList = new ArrayList<>();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.addItemDecoration(new CirclePagerIndicatorDecoration());
        recyclerView1.setLayoutManager(layoutManager1);
        homeBannerAdapter = new HomeBannerAdapter(getActivity(), bannerList);
        recyclerView1.setAdapter(homeBannerAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (layoutManager1.findFirstCompletelyVisibleItemPosition() < (homeBannerAdapter.getItemCount() - 1)) {
                    layoutManager1.smoothScrollToPosition(recyclerView1, new RecyclerView.State(), layoutManager1.findFirstVisibleItemPosition() + 1);
                } else if (layoutManager1.findFirstCompletelyVisibleItemPosition() == (homeBannerAdapter.getItemCount() - 1)) {
                    layoutManager1.smoothScrollToPosition(recyclerView1, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);
        loadBanner();


        //popularload
        recyclerView4.setHasFixedSize(true);
        popularList = new ArrayList<>();
        GridLayoutManager layoutManager4 = new GridLayoutManager(getActivity(), 4);

        recyclerView4.setLayoutManager(layoutManager4);
        homePopularAdapter = new HomePopularAdapter(getActivity(), popularList);
        recyclerView4.setAdapter(homePopularAdapter);
        loadPopular();

        //products populate
        recyclerView3.setHasFixedSize(true);
        productsList = new ArrayList<>();
        productsListPagination = new ArrayList<>();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(layoutManager2);
        homeProductAdapter = new HomeProductAdapter(getActivity(), productsList,getActivity());
        recyclerView3.setAdapter(homeProductAdapter);
        ProductssPopulate();

        recyclerView3.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView3.canScrollVertically(1) && dy > 0) {
                    //scrolled to BOTTOM
                    Toast.makeText(getActivity(), "last", Toast.LENGTH_SHORT).show();
                } else if (!recyclerView3.canScrollVertically(-1) && dy < 0) {
                    //scrolled to TOP
                }
            }
        });
        //imagecolorfilter
        MidhunUtils.colorFilter(getActivity(), img_back, R.color.purple);
        MidhunUtils.colorFilter(getActivity(), img_search, R.color.light_blackk);
        MidhunUtils.colorFilter(getActivity(), img_search2, R.color.grey_black);
        MidhunUtils.colorFilter(getActivity(), img_noty, R.color.purple);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDg();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(getActivity(), SearchActivity.class);
                in.putExtra("name", "");
                startActivity(in);
            }
        });

        img_noty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), NotifyActivity.class));
            }
        });
        //swiperefresh

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        mSwipeRefreshLayout.setColorScheme(R.color.pink,
                R.color.purple_700, R.color.purple_200, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(getClass().getSimpleName(), "refresh");
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadCategories();
                        ProductssPopulate();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;
                offset = String.valueOf(count);
                ProductssPopulate2(offset);
                load.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void loadCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Category>> call = api.getCategoryList(Api.API_KEY, Api.API_KEY, "10", "0", "categories");
        Log.e("category response", "loadCategories: " + call.toString());
        categoryList = new ArrayList<>();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                try {
                    // categoryList = response.body();
                    categoryList.addAll(response.body());
                    homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), categoryList);
                    recyclerView2.setAdapter(homeCategoryAdapter);
                    loading.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadBanner() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Category>> call = api.getCategoryList(Api.API_KEY, Api.API_KEY, "10", "0", "categories");
        Log.e("category response", "loadCategories: " + call.toString());
        bannerList = new ArrayList<>();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                // bannerList = response.body();
                try {
                    bannerList.addAll(response.body());

                    homeBannerAdapter = new HomeBannerAdapter(getActivity(), bannerList);
                    recyclerView1.setAdapter(homeBannerAdapter);
                    loading.setVisibility(View.GONE);
                    shimmerFrameLayout1.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ProductssPopulate() {
        ProductsViewModel model1 = ViewModelProviders.of(getActivity()).get(ProductsViewModel.class);
        productsList = new ArrayList<>();
        model1.getProducts("0").observe(getActivity(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {


                try {
                    productsList.addAll(products);
                    productsListPagination.addAll(productsList);

                    int Length = productsList.size();
                    int index = Length - 1;


                    homeProductAdapter = new HomeProductAdapter(getActivity(), productsList,getActivity());
                    recyclerView3.setAdapter(homeProductAdapter);
                    loading.setVisibility(View.GONE);
                    shimmerFrameLayout2.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    startActivity(new Intent(getActivity(), MainActivity.class));

                }

            }
        });
    }

    private void ProductssPopulate2(String offset) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ProductApiModel> call = api.getProductList(Api.API_KEY, Api.API_KEY,
                "10", offset, "products");
        call.enqueue(new Callback<ProductApiModel>() {
            @Override
            public void onResponse(Call<ProductApiModel> call, Response<ProductApiModel> response) {

                if (response.body().PList().isEmpty()) {
                    load.setVisibility(View.GONE);
                } else {
                    load.setVisibility(View.VISIBLE);
                }

                productsListPagination.addAll(response.body().PList());

                homeProductAdapter = new HomeProductAdapter(getActivity(), productsListPagination,getActivity());
                recyclerView3.setAdapter(homeProductAdapter);
                loading.setVisibility(View.GONE);
                base.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProductApiModel> call, Throwable t) {

            }
        });
    }

    private void loadPopular() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<ProductApiModel> call = api.getProductList(Api.API_KEY, Api.API_KEY, "8", "0", "products");
        call.enqueue(new Callback<ProductApiModel>() {
            @Override
            public void onResponse(Call<ProductApiModel> call, Response<ProductApiModel> response) {
                if (response.code() == 200) {
                    popularList.addAll(response.body().PList());
                    homePopularAdapter = new HomePopularAdapter(getActivity(), popularList);
                    recyclerView4.setAdapter(homePopularAdapter);
                    shimmerFrameLayout4.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProductApiModel> call, Throwable t) {

            }
        });
    }

    private void myOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<GetMyOrderApiModel> call = api.getMyOrder(Api.API_KEY, Api.API_KEY, UID, "0", "1");
        myOrderList = new ArrayList<>();
        call.enqueue(new Callback<GetMyOrderApiModel>() {
            @Override
            public void onResponse(Call<GetMyOrderApiModel> call, Response<GetMyOrderApiModel> response) {
                myOrderList.addAll(response.body().Olist());
                int length = myOrderList.size();
                int index = length - 1;
                for (int i = 0; i < length; i++) {
                    if (myOrderList.get(index).getStatus().equalsIgnoreCase("1") || myOrderList.get(index).getStatus().equalsIgnoreCase("2") || myOrderList.get(index).getStatus().equalsIgnoreCase("3")) {

                    } else {
                        myOrderList.remove(index);
                    }
                    index--;
                }
                upCommingAdapter = new UpCommingAdapter(myOrderList, getActivity());
                recyclerView5.setAdapter(upCommingAdapter);
            }

            @Override
            public void onFailure(Call<GetMyOrderApiModel> call, Throwable t) {
            }
        });
    }







    private void showBottomDg() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        ImageView ic_order, ic_cart, ic_fav, ic_account, ic_settings, ic_rate, ic_notify, ic_policy, ic_refund;
        ic_refund = bottomSheetDialog.findViewById(R.id.ic_refund);
        ic_policy = bottomSheetDialog.findViewById(R.id.ic_policy);
        ic_notify = bottomSheetDialog.findViewById(R.id.ic_notify);
        ic_rate = bottomSheetDialog.findViewById(R.id.ic_rate);
        ic_settings = bottomSheetDialog.findViewById(R.id.ic_settings);
        ic_account = bottomSheetDialog.findViewById(R.id.ic_account);
        ic_fav = bottomSheetDialog.findViewById(R.id.ic_fav);
        ic_cart = bottomSheetDialog.findViewById(R.id.ic_cart);
        ic_order = bottomSheetDialog.findViewById(R.id.ic_order);
        CardView img_myorder = bottomSheetDialog.findViewById(R.id.img_myorder);
        CardView img_cart = bottomSheetDialog.findViewById(R.id.img_cart);
        CardView img_fav = bottomSheetDialog.findViewById(R.id.img_fav);
        CardView img_account = bottomSheetDialog.findViewById(R.id.img_account);
        CardView img_settings = bottomSheetDialog.findViewById(R.id.img_settings);
        CardView img_rate = bottomSheetDialog.findViewById(R.id.img_rate);
        CardView img_notify = bottomSheetDialog.findViewById(R.id.img_notify);
        CardView img_policy = bottomSheetDialog.findViewById(R.id.img_policy);
        CardView img_refund = bottomSheetDialog.findViewById(R.id.img_refund);
//        MidhunUtils.colorFilter(getActivity(), ic_order, R.color.yellow);
//        MidhunUtils.colorFilter(getActivity(), ic_cart, R.color.blue);
//        MidhunUtils.colorFilter(getActivity(), ic_fav, R.color.pink);
//        MidhunUtils.colorFilter(getActivity(), ic_account, R.color.purple);
//        MidhunUtils.colorFilter(getActivity(), ic_settings, R.color.green);
//        MidhunUtils.colorFilter(getActivity(), ic_rate, R.color.indigo);
//        MidhunUtils.colorFilter(getActivity(), ic_notify, R.color.purple_201);
//        MidhunUtils.colorFilter(getActivity(), ic_policy, R.color.teal_700);
//        MidhunUtils.colorFilter(getActivity(), ic_refund, R.color.purple_700);

        img_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });

        img_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotifyActivity.class));
            }
        });

        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FavActivity.class));

            }
        });

        img_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserActivity.class));

            }
        });
        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));

            }
        });

        bottomSheetDialog.show();
    }
}

