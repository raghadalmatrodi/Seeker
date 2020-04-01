package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.ProjectAdapter;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_MyProjects_Pending_Fragment extends Fragment implements Serializable, FRProjectAdapter.ProjectAdapterListener {

    //TODO HIND #?
    private View view;
    private RecyclerView recyclerView;
    private FRProjectAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private Freelancer_MyProjects_Pending_Fragment.ProjectListener projectListener;
    private TextView pendingText;

    private static final String LOG = Freelancer_MyProjects_Pending_Fragment.class.getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =  inflater.inflate(R.layout.fragment_freelancer_pending_projects, container, false);

        pendingText = view.findViewById(R.id.freelancer_pending_projects_text);

        return view;
    }

    public interface ProjectListener{

        void onProjectItemSelected(Project project);
    }//End of interface

    @Override
    public void onProjectItemClick(Project project) {

//        projectListener.onProjectItemSelected(project);
//        todo 3? hind
//        Intent i = new Intent(getContext(), ViewBid.class);
//
//        //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
//        i.putExtra("projectObj", project);
//        startActivity(i);

        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project",project);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_freelancer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }

    public void setListener (ProjectListener projectListener)
    {
        this.projectListener = projectListener;

    }


    @Override
    public void onResume() {
        super.onResume();

        ApiClients.getAPIs().getProjectsByStatusOnly("0").enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"SUCCESS",Toast.LENGTH_LONG).show();
                    /**
                     *     int responseSize = response.body().size();
                     *                     for (int i = 0; i< responseSize; i++){
                     *                         if (response.body().get(i).getFreelancer() != null)
                     *                         if (response.body().get(i).getFreelancer().getId() == 252){
                     *                             bidList.add(response.body().get(i));
                     *                         }
                     *                     }
                     */



//                    int responseSize = response.body().size();
//                    List<Bid> testBidList = new ArrayList<>();
//                    List<Project> testP = new ArrayList<>();
//
//                    int tryy = 0;
                    long currFree = MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID,-1);
//                    Freelancer currf = new Freelancer(currFree);
//
//
//                    for (int i = 0; i< responseSize; i++){
////                        if (response.body().get(i).getBids() != null)
//
//                        if (response.body().get(i).getBids().size() > 0){
////                                tryy++;
//                            testP.add(response.body().get(i));
//                            testBidList = response.body().get(i).getBids();
//                        }
//
//                    }

                    /**
                     * LET'S RESTART, FROM THE BEGINNING.
                     * ASSUMING ALL PROJECTS HAS LIST OF BIDS.
                     * 1ST: WE FOR LOOP IN ALL PROJECTS, K
                     * 2ND: WE FOR LOOP IN ALL BIDS IN PROJECT K, H
                     * 3RD: NOW I'M ON THE FIRST BID H OF THE FIRST PROJECT K, (ASSUMING THAT ALL BIDS CONTAINS FREELANCER OBJS) -> unfortunately not, so check on that plz :)
                     * -> I'LL CHECK WHETHER PROJECT K . BID H . FREELANCER . FRID EQUALS CURRENT FREELANCER ID
                     * IF YES, I'LL ADD THIS PROJECT K TO MY PROJECTSLIST. IF NOT, MOVE TO BID H+1.
                     *
                     */

                    int rSize = response.body().size();
                    int bidSize = 0;

                    //Projects loop
                    for (int k = 0; k < rSize; k++){
                        bidSize = response.body().get(k).getBids().size();

                        //Bids on project k loop
                        for (int h = 0; h < bidSize; h++ ){

                            if (response.body().get(k).getBids().get(h).getFreelancer() != null)
                                if (response.body().get(k).getBids().get(h).getFreelancer().getId() == currFree)
                                    projectList.add(response.body().get(k));

                        }//Bids loop
                    }//Projects loop.

//                    int bidlistSize = testBidList.size();
//                    int testPSize = testP.size();
//                    for (int k = 0; k< testPSize; k++){
////                        if (response.body().get(k).getBids().get(k).getFreelancer() != null)
//                        for (int h = 0; h<bidlistSize; h++){
//                            if (testP.get(k).getBids().get(h).getFreelancer() != null)
//                                if (testP.get(k).getBids().get(h).getFreelancer().getId() == currFree){
//                                    tryy++;
//                                    projectList.add(testP.get(k));
//                                }
//                        }
//
//                    }

//                    projectList =  response.body();
                    pendingText.setText("");

                    recyclerView = (RecyclerView) view.findViewById(R.id.fr_pending_recycler_view);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    adapter = new FRProjectAdapter(projectList);
                    projectList = new ArrayList<>();
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    adapter.setListener(Freelancer_MyProjects_Pending_Fragment.this);
                    recyclerView.setNestedScrollingEnabled(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);


                }else{
                    Toast.makeText(getContext(),"not success",Toast.LENGTH_LONG).show();
                    //todo: fix the message, should be when there are no projects [when projects list is empty]
                    pendingText.setText("There are no pending projects");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Toast.makeText(getContext(),"fail",Toast.LENGTH_LONG).show();
                pendingText.setText("Error, Something went wrong..");
            }
        });



    }




}

