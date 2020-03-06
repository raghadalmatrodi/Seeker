package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
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

                    /**
                     *     int responseSize = response.body().size();
                     *                     for (int i = 0; i< responseSize; i++){
                     *                         if (response.body().get(i).getFreelancer() != null)
                     *                         if (response.body().get(i).getFreelancer().getId() == 252){
                     *                             bidList.add(response.body().get(i));
                     *                         }
                     *                     }
                     */



                    projectList =  response.body();
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

                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });



    }




}
