package com.example.seeker.Activities.Contract;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.PostProject.CategoryAdapter;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractFragment extends Fragment {

    private View view;
    private TextView status;
    private TextView employerName;
    private TextView freelancerName;
    private TextView deliveryDate;
    private TextView totalPrice;
    private TextView createMilestone;
    private TextView projectName;
    private ImageView backBtn;
    private Contract contract;
    private Project project;
    private RecyclerView recyclerView;
    private MilestoneAdapter adapter;
    private ProgressBar mProgressBar;
    private LinearLayout linearLayout;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contract, container, false);

        mProgressBar = view.findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        init();

        //will change to project
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           project =(Project)bundle.getSerializable("project");
           performAPIRequest(project.getId());

        }

        createMilestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new MilestoneFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("contract",contract);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_emp, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                performAPIRequest(project.getId());

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        return view;

    }

    private void init() {

        status = view.findViewById(R.id.contract_projectStatus);
        employerName = view.findViewById(R.id.contract_employer);
        freelancerName = view.findViewById(R.id.contract_freelancer);
        deliveryDate = view.findViewById(R.id.contract_deliveryDate);
        totalPrice = view.findViewById(R.id.contract_totalPrice);
        createMilestone = view.findViewById(R.id.contract_createMilestone);
        projectName = view.findViewById(R.id.contract_project_name);
        recyclerView = view.findViewById(R.id.contract_recycler_view);
        backBtn = view.findViewById(R.id.contract_back);
        linearLayout = view.findViewById(R.id.info_contract);



    }


    private void setContractInformation() {

        linearLayout.setVisibility(View.VISIBLE);
        projectName.setText(contract.getProject().getTitle());

        if(contract.getType().equals("0")){
            status.setText("In Progress");
        }else{
            status.setText("Completed");

        }
        employerName.setText(contract.getProject().getEmployer().getUser().getUsername());
        freelancerName.setText(contract.getFreelancer().getUser().getUsername());
        deliveryDate.setText(contract.getDeadline().toString().substring(0,10));
        String price = String.valueOf(contract.getPrice());
        totalPrice.setText(price+" SAR");


        setRecyclerView(contract.getProject().getMilestones());

        checkVisibility();
    }

    private void setRecyclerView(List<Milestone> milestoneList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MilestoneAdapter(milestoneList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    public void performAPIRequest(long project_id){


        ApiClients.getAPIs().getContractByProjectIdRequest(project_id).enqueue(new Callback<Contract>() {

            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()){

                    contract = response.body();
                    project = contract.getProject();

                    setContractInformation();

                    Log.i("on Response: contract suc", response.message());

                }
                Log.i("on Response: contract Notsuc", response.message());
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Log.i("on Response: contract fail", t.getMessage());
            }
        });

    }
    public void checkVisibility(){


        if(project.getMilestones().isEmpty()){
            createMilestone.setVisibility(View.VISIBLE);
        }else{
            createMilestone.setVisibility(View.INVISIBLE);


        }
    }


}
