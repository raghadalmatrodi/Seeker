package com.example.seeker.EmployerMainPages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
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
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptBidConfirmation extends Fragment {
    Button accept_bid;
    Button cancel_bid;
    View view;
    Bid bid;
    ImageView backButton;
    TextView bidTitle;
    TextView bidDes;
    TextView bidBud;
    TextView bidDeadline;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
            bidDeadline.setText(bid.getDeliver_date());
            bidDes.setText(bid.getDescription());
            bidBud.setText(bid.getPrice()+"");

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
         if(bid != null) {
             ApiClients.getAPIs().acceptBid(bid.getId()).enqueue(new Callback<ApiResponse>() {
                 @Override
                 public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                     Log.i("onResponse ", response.message());

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
}
