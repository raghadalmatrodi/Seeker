package com.example.seeker.FreelancerMainPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.SettingActivity;
import com.example.seeker.EmployerMainPages.Emp_AccountFragment;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;
    TextView logoutBtn, name;
    LinearLayout edit_profile;
    LinearLayout settings;
    ImageView profile_picture;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_freelancer_account, container, false);

            init();

            fillCurrentUserData();
        switch_btn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        settings.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }
    public void init(){

        switch_btn = (Button) view.findViewById(R.id.switch_to_e_btn);
        logoutBtn = view.findViewById(R.id.profile_logout_btn);

        edit_profile = view.findViewById(R.id.fr_edit_profile_ll);
        edit_profile.setOnClickListener(this);

        name = view.findViewById(R.id.freelancer_profile_name);
        settings = view.findViewById(R.id.settings_ll);
        profile_picture = view.findViewById(R.id.profile_picture);

        String userImgURL = MySharedPreference.getString(getContext(),Constants.Keys.USER_IMG,null);
        if( userImgURL != null ){
            Glide.with(this)
                    .load(userImgURL)
                    .placeholder(R.drawable.user)
                    .into(profile_picture);

        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.switch_to_e_btn:

            {  MySharedPreference.putString(getContext(),Constants.Keys.USER_CURRENT_TYPE,"EMPLOYER" );
                changeUserType();
                Intent intent;
                intent = new Intent(Freelancer_AccountFragment.this.getActivity(), EmployerMainActivity.class);
                startActivity(intent);}
                break;

            case R.id.profile_logout_btn:
                //todo: logout api
                logoutDialog();
                break;

            case R.id.fr_edit_profile_ll:
                startActivity(new Intent(Freelancer_AccountFragment.this.getActivity(), EditProfileActivity.class));
                break;

            case R.id.settings_ll:
                startActivity(new Intent(Freelancer_AccountFragment.this.getActivity(), SettingActivity.class));



        }
    }

    private void fillCurrentUserData() {
        String capitalizedName = "";
        String currentUSerName = MySharedPreference.getString(getContext(), Constants.Keys.USER_NAME, "");
        capitalizedName = currentUSerName.substring(0,1).toUpperCase() + currentUSerName.substring(1,currentUSerName.length());

        name.setText(capitalizedName);


    }

    public void changeUserType(){


        ApiClients.getAPIs().switchType(MySharedPreference.getLong(getContext(), Constants.Keys.USER_ID,0)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.i("onResponse successful  switchType ", response.message());

                }else{
                    Log.i("onResponse Notsuccessful  switchType ", response.message());

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("onResponse failure  switchType ", t.getLocalizedMessage());

            }
        });


    }
        private void logoutDialog() {
            Intent i;
//        String string = in.getStringExtra("message");
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure, that you want to logout?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            logout();
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder.create();
            alert11.show();

        }//End LogoutDialog()

    //TODO: REEMA LOGOUT
    public void logout() {

        clearData();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }//End of logout()

    public void clearData() {

        MySharedPreference.clearData(getContext());

    }//End of clearData()
}
