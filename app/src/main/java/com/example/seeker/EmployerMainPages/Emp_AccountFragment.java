package com.example.seeker.EmployerMainPages;

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

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.ContactSupportActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.NotificationsActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment.PaymentActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment.PaymentAdapter;
import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.LogOut;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;
    User user;

    //profile
    private LinearLayout edit_profileLL;

    private ImageView profile_picture;
    private TextView name;


    private LinearLayout notificationsLL, paymentsLL, settingsLL, privacy_policyLL, terms_conditionsLL, contact_supportLL;
    private TextView logout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_emp_account, container, false);

        init();


        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        switch_btn = view.findViewById(R.id.switch_to_f_btn);
        switch_btn.setOnClickListener(this);

        name = view.findViewById(R.id.profile_name);
        fillCurrentUserData();

        edit_profileLL = view.findViewById(R.id.edit_profile_ll);
        edit_profileLL.setOnClickListener(this);

        notificationsLL = view.findViewById(R.id.notifications_ll);
        notificationsLL.setOnClickListener(this);

        paymentsLL = view.findViewById(R.id.payments_ll);
        paymentsLL.setOnClickListener(this);

        settingsLL = view.findViewById(R.id.settings_ll);
        settingsLL.setOnClickListener(this);

        privacy_policyLL = view.findViewById(R.id.privacy_policy_ll);
        privacy_policyLL.setOnClickListener(this);

        terms_conditionsLL = view.findViewById(R.id.terms_conditions_ll);
        terms_conditionsLL.setOnClickListener(this);

        contact_supportLL = view.findViewById(R.id.contact_support_ll);
        contact_supportLL.setOnClickListener(this);

        logout = view.findViewById(R.id.profile_logout_btn);
        logout.setOnClickListener(this);

    }

    private void fillCurrentUserData() {
        String capitalizedName = "";
        String currentUSerName = MySharedPreference.getString(getContext(), Constants.Keys.USER_NAME, "");
        capitalizedName = currentUSerName.substring(0,1).toUpperCase() + currentUSerName.substring(1,currentUSerName.length());
        name.setText(capitalizedName);


    }
    //not yet
public void changeUserType(){


    ApiClients.getAPIs().switchType(MySharedPreference.getLong(getContext(),Constants.Keys.USER_ID,0)).enqueue(new Callback<Void>() {
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.switch_to_f_btn:
                {

                    MySharedPreference.putString(getContext(),Constants.Keys.USER_CURRENT_TYPE,"FREELANCER" );
changeUserType();
                    Intent intent;
                intent = new Intent(Emp_AccountFragment.this.getActivity(), FreelancerMainActivity.class);
                startActivity(intent);}
                break;


            case R.id.edit_profile_ll:
                Intent intent1;
                intent1 = new Intent(Emp_AccountFragment.this.getActivity(), EditProfileActivity.class);
                startActivity(intent1);
                break;

            case R.id.notifications_ll:

                Intent intent2;
                intent2 = new Intent(Emp_AccountFragment.this.getActivity(), NotificationsActivity.class);
                startActivity(intent2);
                break;

            case R.id.payments_ll:

                Intent intent3;
                intent3 = new Intent(Emp_AccountFragment.this.getActivity(), PaymentActivity.class);
                startActivity(intent3);
                break;

            case R.id.settings_ll:
//                startActivity(new Intent(Emp_AccountFragment.this.getActivity(), ...));
                break;

            case R.id.privacy_policy_ll:
//                startActivity(new Intent(Emp_AccountFragment.this.getActivity(), ...));
                break;

            case R.id.terms_conditions_ll:
                openDialog();
                break;


            case R.id.contact_support_ll:
                Intent intent7;
                intent7=new Intent(Emp_AccountFragment.this.getActivity(), ContactSupportActivity.class);
                startActivity(intent7);
                break;


            case R.id.profile_logout_btn:
                //todo: logout api
//                startActivity(new Intent(Emp_AccountFragment.this.getActivity(), ...));
                logoutDialog();
                break;

        }//End switch



//        Intent intent;
//        intent = new Intent(Emp_AccountFragment.this.getActivity(), FreelancerMainActivity.class);
//        startActivity(intent);
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

    private void openDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        String termsConditionsStr = "Terms and conditions string test";
        // Setting Dialog Message
        alertDialog.setMessage(termsConditionsStr);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();
    }

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
