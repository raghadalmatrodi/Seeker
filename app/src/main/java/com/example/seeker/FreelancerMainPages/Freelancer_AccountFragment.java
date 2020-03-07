package com.example.seeker.FreelancerMainPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.EmployerMainPages.Emp_AccountFragment;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.R;
import com.example.seeker.SharedPref.MySharedPreference;

public class Freelancer_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;
    TextView logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_freelancer_account, container, false);

            init();
        switch_btn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }
    public void init(){

        switch_btn = (Button) view.findViewById(R.id.switch_to_e_btn);
        logoutBtn = view.findViewById(R.id.profile_logout_btn);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.switch_to_e_btn:
                Intent intent;
                intent = new Intent(Freelancer_AccountFragment.this.getActivity(), EmployerMainActivity.class);
                startActivity(intent);
                break;

            case R.id.profile_logout_btn:
                //todo: logout api
                logoutDialog();
                break;


        }
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
