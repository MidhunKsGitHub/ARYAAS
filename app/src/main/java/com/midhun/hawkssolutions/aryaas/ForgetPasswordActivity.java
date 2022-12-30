package com.midhun.hawkssolutions.aryaas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.midhun.hawkssolutions.aryaas.Config.Api;
import com.midhun.hawkssolutions.aryaas.Model.ChangePwdApiModel;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextView btn,catname;
    EditText edt_pwd, edt_cpwd;
    String UID;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edt_cpwd = findViewById(R.id.edt_cpwd);
        edt_pwd = findViewById(R.id.edt_pwd);
        catname=findViewById(R.id.catname);
        btn = findViewById(R.id.btn);
        img_back = findViewById(R.id.img_back);
        catname.setText("Forget password");

        getSupportActionBar().hide();
        UID = MidhunUtils.localData(ForgetPasswordActivity.this, "login", "UID");

        MidhunUtils.setStatusBarIcon(ForgetPasswordActivity.this, true);
        MidhunUtils.changeStatusBarColor(ForgetPasswordActivity.this, R.color.white);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_pwd.getText().toString().isEmpty()) {
                    edt_pwd.setError("Required");
                } else if (edt_cpwd.getText().toString().isEmpty()) {
                    edt_cpwd.setError("Required");
                }
               else {
                    ProgressDialog progress = ProgressDialog.show(ForgetPasswordActivity.this, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    changePassword();
                }
            }
        });
    }

    private void changePassword() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ChangePwdApiModel> call = api.forgetPassword(Api.API_KEY,Api.API_KEY, edt_pwd.getText().toString(), edt_cpwd.getText().toString());
        call.enqueue(new Callback<ChangePwdApiModel>() {
            @Override
            public void onResponse(Call<ChangePwdApiModel> call, Response<ChangePwdApiModel> response) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                Toast.makeText(ForgetPasswordActivity.this, "Password Update succesfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ChangePwdApiModel> call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this, "Cannot update password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}