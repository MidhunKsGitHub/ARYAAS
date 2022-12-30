package com.midhun.hawkssolutions.aryaas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Address;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressActivity extends AppCompatActivity {
    EditText name, address, mobile, landmark, location, pincode, state;
    CardView btn;
    String UID;
    String lati, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);
        landmark = findViewById(R.id.landmark);
        location = findViewById(R.id.landmark);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        btn = findViewById(R.id.btn);
        UID = MidhunUtils.localData(AddressActivity.this, "login", "UID");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddress();
            }
        });
        getSupportActionBar().hide();
        lati=getIntent().getExtras().getString("lati");
        longi=getIntent().getExtras().getString("longi");
        MidhunUtils.changeStatusBarColor(AddressActivity.this, R.color.grey);
        MidhunUtils.setStatusBarIcon(AddressActivity.this, true);
    }

    private void addAddress() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Address> call = api.addAddress(Api.API_KEY, Api.API_KEY, name.getText().toString(), mobile.getText().toString(), location.getText().toString(), pincode.getText().toString(), address.getText().toString(), location.getText().toString(), state.getText().toString(), landmark.getText().toString(), UID, lati, longi);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Toast.makeText(AddressActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Toast.makeText(AddressActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}