package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Database.ApiMethods;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {

    private Switch enableProjectExpiryNoti;

    private Switch enableProjectSkillNoti;

    private Switch enableAcceptBidNoti;

    private Switch enableMilestoneDLNoti;
    private long user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setTitle("Notification");
        user_id = MySharedPreference.getLong(this,Constants.Keys.USER_ID,-1);
        init();
        getNotificationsStatus();
        onCheckChanged();


    }

    private void onCheckChanged() {
        enableAcceptBidNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ApiClients.getAPIs().SetEnableAcceptBidNoti(user_id,isChecked).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        MySharedPreference.putBoolean(getApplicationContext(), Constants.Keys.enableAcceptBidNoti, isChecked);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
        enableMilestoneDLNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ApiClients.getAPIs().SetEnableMilestoneDLNoti(user_id,isChecked).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        MySharedPreference.putBoolean(getApplicationContext(), Constants.Keys.enableMilestoneDLNoti, isChecked);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        enableProjectExpiryNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ApiClients.getAPIs().setEnableProjectExpiryNoti(user_id,isChecked).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        MySharedPreference.putBoolean(getApplicationContext(), Constants.Keys.enableProjectExpiryNoti, isChecked);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        enableProjectSkillNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ApiClients.getAPIs().SetEnableProjectSkillNoti(user_id,isChecked).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        MySharedPreference.putBoolean(getApplicationContext(), Constants.Keys.enableProjectSkillNoti, isChecked);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void init() {
        enableAcceptBidNoti = findViewById(R.id.allow_bids);
        enableMilestoneDLNoti = findViewById(R.id.allow_milestones);
        enableProjectExpiryNoti = findViewById(R.id.allow_expiry);
        enableProjectSkillNoti = findViewById(R.id.allow_skill);
    }

    private void getNotificationsStatus() {
        enableAcceptBidNoti.setChecked(MySharedPreference.getBoolean(this, Constants.Keys.enableAcceptBidNoti, true));
        enableMilestoneDLNoti.setChecked(MySharedPreference.getBoolean(this, Constants.Keys.enableMilestoneDLNoti, true));
        enableProjectExpiryNoti.setChecked(MySharedPreference.getBoolean(this, Constants.Keys.enableProjectExpiryNoti, true));
        enableProjectSkillNoti.setChecked(MySharedPreference.getBoolean(this, Constants.Keys.enableProjectSkillNoti, true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}
