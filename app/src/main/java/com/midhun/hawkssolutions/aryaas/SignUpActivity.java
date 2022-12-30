package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.UserModelSignUP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    LinearLayout bg_name, bg_last_name, bg_email, bg_mobile, bg_password, bg_address, bg_town, bg_city, bg_state;
    TextView login, signup;
    EditText signup_name, signup_last_name, signup_email, signup_mobile, signup_password, signup_address, signup_town, signup_pincode, signup_state;
    List<UserModelSignUP> userModelSignUPList;
    String NAME, LAST_NAME, EMAIL, MOBILE, PASSWORD, PINCODE, TOWN, CITY, STATE;
    ProgressDialog progress;
String lati,longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bg_name = findViewById(R.id.bg_name);
        bg_last_name = findViewById(R.id.bg_last_name);
        bg_email = findViewById(R.id.bg_email);
        bg_mobile = findViewById(R.id.bg_mobile);
        bg_password = findViewById(R.id.bg_password);
        bg_address = findViewById(R.id.bg_address);
        bg_town = findViewById(R.id.bg_town);
        bg_city = findViewById(R.id.bg_city);
        bg_state = findViewById(R.id.bg_state);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        signup_name = findViewById(R.id.name);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_email = findViewById(R.id.address);
        signup_mobile = findViewById(R.id.mobile);
        signup_password = findViewById(R.id.signup_password);
        signup_address = findViewById(R.id.landmark);
        signup_town = findViewById(R.id.location);
        signup_pincode = findViewById(R.id.pincode);
        signup_state = findViewById(R.id.state);

        getSupportActionBar().hide();

        lati=getIntent().getExtras().getString("lati");
        longi=getIntent().getExtras().getString("longi");

        MidhunUtils.changeStatusBarColor(SignUpActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(SignUpActivity.this, true);
        MidhunUtils.round(bg_name, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_last_name, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_email, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_mobile, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_password, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_address, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_town, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_city, 0xffeceff1, 0xffeceff1, 25f);
        MidhunUtils.round(bg_state, 0xffeceff1, 0xffeceff1, 25f);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAME = signup_name.getText().toString();
                LAST_NAME = signup_last_name.getText().toString();
                EMAIL = signup_email.getText().toString();
                MOBILE = signup_mobile.getText().toString();
                PASSWORD = signup_password.getText().toString();
                CITY = signup_address.getText().toString();
                TOWN = signup_town.getText().toString();
                PINCODE = signup_pincode.getText().toString();
                STATE = signup_state.getText().toString();

                if (NAME.isEmpty()) {
                    signup_name.requestFocus();
                    signup_name.setError("Can't be empty");
                } else if (LAST_NAME.isEmpty()) {
                    signup_last_name.requestFocus();
                    signup_last_name.setError("Can't be empty");
                } else if (EMAIL.isEmpty()) {
                    signup_email.requestFocus();
                    signup_email.setError("Can't be empty");
                } else if (MOBILE.isEmpty()) {
                    signup_mobile.requestFocus();
                    signup_mobile.setError("Can't be empty");
                } else if (PASSWORD.isEmpty()) {
                    signup_password.requestFocus();
                    signup_password.setError("Can't be empty");
                } else if (CITY.isEmpty()) {
                    signup_address.requestFocus();
                    signup_address.setError("Can't be empty");
                } else if (TOWN.isEmpty()) {
                    signup_town.requestFocus();
                    signup_town.setError("Can't be empty");
                } else if (PINCODE.isEmpty()) {
                    signup_pincode.requestFocus();
                    signup_pincode.setError("Can't be empty");
                } else if (STATE.isEmpty()) {
                    signup_state.requestFocus();
                    signup_state.setError("Can't be empty");
                } else {
                    progress = ProgressDialog.show(SignUpActivity.this, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    SignUp();
                }
            }
        });
    }

    private void SignUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<UserModelSignUP> call = api.userSignUP(Api.API_KEY,Api.API_KEY, MOBILE, EMAIL, NAME, LAST_NAME, CITY, STATE, TOWN, PINCODE,lati,longi,PASSWORD);
        userModelSignUPList = new ArrayList<>();
        call.enqueue(new Callback<UserModelSignUP>() {
            @Override
            public void onResponse(Call<UserModelSignUP> call, Response<UserModelSignUP> response) {
                progress.dismiss();
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("2")) {
                        Toast.makeText(SignUpActivity.this, "User Already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("UID", response.body().getUserid());
                        myEdit.putString("USERNAMER", response.body().getUsername());
                        myEdit.commit();
//                        Toast.makeText(SignUpActivity.this,  response.body().getUsername(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignUpActivity.this, "Registration sucessful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(SignUpActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModelSignUP> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(SignUpActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}