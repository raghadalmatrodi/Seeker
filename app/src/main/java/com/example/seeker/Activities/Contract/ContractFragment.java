package com.example.seeker.Activities.Contract;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Milestone;
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
    private RecyclerView recyclerView;
    private MilestoneAdapter adapter;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contract, container, false);

        init();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
           contract =(Contract)bundle.getSerializable("contract");
            setContractInformation();
        }

        createMilestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will move it to milestone fragment
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



    }


    private void setContractInformation() {

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


}
