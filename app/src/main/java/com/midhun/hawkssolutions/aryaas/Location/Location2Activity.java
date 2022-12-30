package com.midhun.hawkssolutions.aryaas.Location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.midhun.hawkssolutions.aryaas.AddressActivity;
import com.midhun.hawkssolutions.aryaas.R;
import com.midhun.hawkssolutions.aryaas.SignUpActivity;
import com.midhun.hawkssolutions.aryaas.Util.MidhunUtils;


public class Location2Activity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ProgressBar progressBar;
    TextView textLatLong, address, postcode, locaity, state, district, country;
    ResultReceiver resultReceiver;
    double lati=0, longi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        resultReceiver = new AddressResultReceiver(new Handler());

        progressBar = findViewById(R.id.progress_circular);
        textLatLong = findViewById(R.id.textLatLong);

        address = findViewById(R.id.textaddress);
        locaity = findViewById(R.id.textlocality);
        postcode = findViewById(R.id.textcode);
        country = findViewById(R.id.textcountry);
        district = findViewById(R.id.textdistrict);
        state = findViewById(R.id.textstate);
        getSupportActionBar().hide();
        MidhunUtils.setStatusBarIcon(Location2Activity.this, true);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Location2Activity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        progressBar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Location2Activity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            textLatLong.setText(String.format("Latitude : %s\n Longitude: %s", lati, longi));
                            SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("lati", String.valueOf(lati));
                            myEdit.putString("longi", String.valueOf(longi));
                            myEdit.putString("city", locaity.getText().toString());
                            myEdit.commit();

                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);
                            fetchaddressfromlocation(location);
                            if(lati!=0&&longi!=0) {
                                Intent in = new Intent();
                                in.putExtra("lati", String.valueOf(lati));
                                in.putExtra("longi", String.valueOf(longi));
                                in.setClass(getApplicationContext(), AddressActivity.class);
                                startActivity(in);
                                finish();;
                            }

                        } else {
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }, Looper.getMainLooper());

    }

    public class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                address.setText(resultData.getString(Constants.ADDRESS));
                locaity.setText(resultData.getString(Constants.LOCAITY));
                state.setText(resultData.getString(Constants.STATE));
                district.setText(resultData.getString(Constants.DISTRICT));
                country.setText(resultData.getString(Constants.COUNTRY));
                postcode.setText(resultData.getString(Constants.POST_CODE));
                SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("city", locaity.getText().toString());
                myEdit.commit();
            } else {
                Toast.makeText(Location2Activity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }


    }

    private void fetchaddressfromlocation(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);


    }
}