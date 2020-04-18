package com.example.seeker.EmployerMainPages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_MyProjectsFragment;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_In_Progress_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptBidConfirmation extends Fragment {
    Button accept_bid;
    Button cancel_bid;
    View view;
    Bid bid;
    private User user;
    ImageView backButton;
    TextView bidTitle;
    TextView bidDes;
    TextView bidBud;
    TextView bidDeadline;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getUser();
        view = inflater.inflate(R.layout.activity_accept_bid_confirmation, container, false);
        accept_bid = view.findViewById(R.id.bid_accept) ;
        cancel_bid = view.findViewById(R.id.bid_cancel);
        backButton = view.findViewById(R.id.project_view_back);
        bidTitle = view.findViewById(R.id.project_title);

        bidDes = view.findViewById(R.id.bid_des);
        bidBud = view.findViewById(R.id.bid_budget);
        bidDeadline = view.findViewById(R.id.bid_date);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bid = (Bid) bundle.getSerializable("bid");
        }
        if(bid!= null) {
            bidTitle.setText(bid.getTitle());


            if(bid.getDeliver_date()!= null ) {
                if (bid.getDeliver_date().length() > 10)
                    bidDeadline.setText(bid.getDeliver_date().substring(0, 10));

                bidDes.setText(bid.getDescription());
                bidBud.setText(bid.getPrice() + "");
            }

        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }
            }
        });

        accept_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.getIsEnabled().equals("1")) {
                    if (bid != null) {
                        ApiClients.getAPIs().acceptBid(bid.getId()).enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                Log.i("onResponse ", response.message());

                                incrementNumberOfWorkedOnPRojects(bid);

                                Fragment fragment = new Emp_MyProjectsFragment();

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_container_emp, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Log.i("onFailure ", t.getLocalizedMessage());

                            }
                        });
                    }
                } else {

                    wrongInfoDialog("Your Account has been deactivated  \n to further information contact the support");

                }
            }
        });

        cancel_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Emp_MyProjectsFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_emp, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        return view;
    }
    private void getUser() {

        long user_id = MySharedPreference.getLong(getContext(), Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().findUserById(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){

                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_accept_bid_confirmation);
//        accept_bid = findViewById(R.id.bid_accept) ;
//        cancel_bid = findViewById(R.id.bid_cancel);
//        Intent intent = getIntent();
//        Bid bid = (Bid) intent.getSerializableExtra("bid");
//
//        accept_bid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //        Log.i("bid information ", bid.toString());
//
//        ApiClients.getAPIs().acceptBid(bid.getId()).enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                Log.i("onResponse ",response.message());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                Log.i("onFailure ",t.getLocalizedMessage());
//
//            }
//        });
//            }
//        });
//
//        cancel_bid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent = new Intent(getApplicationContext(), EmployerMainActivity.class);
//               // intent.putExtra("AcceptBid" , intent);
//                startActivity(intent);
//            }
//        });
//    }

    private void incrementNumberOfWorkedOnPRojects(Bid bid){
        ApiClients.getAPIs().CalculateNumberOfWorkedOnProjects(bid.getFreelancer().getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    private void wrongInfoDialog(String msg) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()


}
