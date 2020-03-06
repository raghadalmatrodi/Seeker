package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp;

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

import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Project;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.R;

import java.util.List;

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

    Emp_MyProjects_Pending_Fragment emp_myProjects_pending_fragment;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    View view;
    List<Bid> bids;
    int isPending=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_view_project, container, false);
        backButton = view.findViewById(R.id.project_view_back);


        title = view.findViewById(R.id.project_title);
        descreption = view.findViewById(R.id.project_des);
        deadline = view.findViewById(R.id.project_date);
        budget = view.findViewById(R.id.project_budget);
        type = view.findViewById(R.id.project_type);
        skills = view.findViewById(R.id.project_skills);
        employerName = view.findViewById(R.id.employer_name);


        emp_myProjects_pending_fragment = new Emp_MyProjects_Pending_Fragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            project = (Project) bundle.getSerializable("project");
             isPending = bundle.getInt("pending" ,0);
        }

            if( (project.getTitle() != null))
                if(!project.getTitle().trim().equals(""))
            title.setText(project.getTitle());


        if(project.getDescription() != null )
            if(!project.getDescription().trim().equals(""))
            descreption.setText(project.getDescription());

        if(!(project.getBudget() == 0))
            budget.setText(project.getBudget() +"");

        if(!project.getType().trim().equals("") || !(project.getType() == null))
            type.setText(project.getType());
        String skills ="";

        if( !(project.getSkills() ==null))
            if(!project.getSkills().isEmpty()) {
                Object[] arraySkills = project.getSkills().toArray();
                for(int i=0 ; i<arraySkills.length; i++)
                    skills += arraySkills[i] + " \n";
            }


        employerName.setText(project.getEmployer().getUser().getUsername());


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

    @Override
    public void onProjectItemSelected(Project project) {
    this.project = project;

    }


    public void setTheAdapter(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_b);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BidsAdapter(bids);
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
}
