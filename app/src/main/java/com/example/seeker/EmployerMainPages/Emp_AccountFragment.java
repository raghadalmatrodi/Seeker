package com.example.seeker.EmployerMainPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.seeker.EmployerMainPages.AccountRelatedActivities.ContactSupportActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.NotificationsActivity;
import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.R;

public class Emp_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;

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


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.switch_to_f_btn:
                Intent intent;
                intent = new Intent(Emp_AccountFragment.this.getActivity(), FreelancerMainActivity.class);
                startActivity(intent);
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

//                Intent intent3;
//                intent3 = new Intent(Emp_AccountFragment.this.getActivity(), ...);
//                startActivity(intent3);
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
                break;

        }//End switch



//        Intent intent;
//        intent = new Intent(Emp_AccountFragment.this.getActivity(), FreelancerMainActivity.class);
//        startActivity(intent);
    }

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
}
