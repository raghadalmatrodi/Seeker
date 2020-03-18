package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Activities.Contract.ContractFragment;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AcceptBidConfirmation;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_viewProjectFragment extends Fragment implements  Emp_MyProjects_Pending_Fragment.ProjectListener ,BidsAdapter.BidsAdapterListener {


    Project project ;
    TextView title;
    TextView descreption;
    TextView budget;
    TextView type;
    TextView skills;
    TextView deadline;
    ImageView backButton;
    TextView employerName;
    Contract contract;

    ImageView contractImg;

    Emp_MyProjects_Pending_Fragment emp_myProjects_pending_fragment;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    View view;
    List<Bid> bids;
    int isPending=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_view_project, container, false);

         init();

        emp_myProjects_pending_fragment = new Emp_MyProjects_Pending_Fragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            project = (Project) bundle.getSerializable("project");
             isPending = bundle.getInt("pending" ,0);
        }

        setProjectInformation();

        contractImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ContractFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("contract",contract);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_emp, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bids = project.getBids();
        TextView number_of_bids = view.findViewById(R.id.numberOfBids);
        number_of_bids.setText("("+ (bids.size() )+ ")");
//        Bid bid = new Bid(0, "hll", "kkkk"xd, 999, "25/2/4 5945495945454", "pending", 7979);
       // bids.add(bid);

        emp_myProjects_pending_fragment.setListener(this);
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

        setTheAdapter();


        return view;

    }

    private void init() {
        backButton = view.findViewById(R.id.project_view_back);
        title = view.findViewById(R.id.project_title);
        descreption = view.findViewById(R.id.project_des);
        deadline = view.findViewById(R.id.project_date);
        budget = view.findViewById(R.id.project_budget);
        type = view.findViewById(R.id.project_type);
        skills = view.findViewById(R.id.project_skills);
        employerName = view.findViewById(R.id.employer_name);
        contractImg = view.findViewById(R.id.contract_img);


    }

    @Override
    public void onProjectItemSelected(Project project) {
    this.project = project;

    }
    public void setProjectInformation(){


        if( (project.getTitle() != null))
            if(!project.getTitle().trim().equals(""))
                title.setText(project.getTitle());


        if(project.getDescription() != null )
            if(!project.getDescription().trim().equals(""))
                descreption.setText(project.getDescription());

        if(!(project.getBudget() == 0))
            budget.setText(project.getBudget() +"");

        if(project.getType() != null)
        if(!project.getType().trim().equals("") || !(project.getType() == null)){

            if(project.getType().equals("0")){
                type.setText("Online");
            }
            else {
                type.setText("On-field");
            }

        }


        String skill ="";

        if( project.getSkills() !=null){

            if (!project.getSkills().isEmpty()) {
                for (Skill s : project.getSkills()) {
                    skill += s.getName() + " \n";
                }
            }
            skills.setText(skill);
        }

        if(project.getEmployer() != null)
        employerName.setText(project.getEmployer().getUser().getUsername());

        if(project.getDeadline() != null){
            deadline.setText(project.getDeadline().substring(0,10));
        }

        if(project.getStatus().equals("0")){
            contractImg.setVisibility(View.INVISIBLE);
        }else{
            contractImg.setVisibility(View.VISIBLE);
            performAPIRequest(project.getId());

        }
    }


    public void setTheAdapter(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_b);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BidsAdapter(bids, project);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        if(isPending==1)
        adapter.showAccept();

    }

    @Override
    public void onBidItemClick(Bid bid) {

    }

    public void performAPIRequest(long project_id){


        ApiClients.getAPIs().getContractByProjectIdRequest(project_id).enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()){

                     contract = response.body();

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

    @Override
    public void onAcceptBidItemClick(Bid bid) {

        Fragment fragment = new AcceptBidConfirmation();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bid",bid);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
