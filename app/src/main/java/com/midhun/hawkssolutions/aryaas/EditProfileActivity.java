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
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;
import com.midhun.hawkssolutions.aryaas.View.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileActivity extends AppCompatActivity {

    EditText edt_name, edt_last, edt_email;
    String UID;
    TextView btn;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edt_email = findViewById(R.id.edt_email);
        edt_name = findViewById(R.id.edt_name);
        edt_last = findViewById(R.id.edit_last);
        img_back=findViewById(R.id.img_back);
        btn = findViewById(R.id.btn);
        getSupportActionBar().hide();
        UID = MidhunUtils.localData(EditProfileActivity.this, "login", "UID");

        MidhunUtils.changeStatusBarColor(EditProfileActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(EditProfileActivity.this, true);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_name.getText().toString().isEmpty()) {
                    edt_name.setError("Required");
                } else if (edt_last.getText().toString().isEmpty()) {
                    edt_last.setError("Required");
                } else if (edt_email.getText().toString().isEmpty()) {
                    edt_email.setError("Required");
                } else {
                    ProgressDialog progress = ProgressDialog.show(EditProfileActivity.this, null, null, true);
                    progress.setContentView(R.layout.progress_layout);
                    progress.setCancelable(false);
                    progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    progress.show();
                    editProfile();
                }
            }
        });

    }

    private void editProfile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<Profile> call = api.updateProfile(Api.API_KEY,Api.API_KEY,
                UID, edt_last.getText().toString(), edt_name.getText().toString(), edt_email.getText().toString());
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}