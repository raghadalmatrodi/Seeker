package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_MyProjects_In_Progress_Fragment extends Fragment  implements ProjectAdapter.ProjectAdapterListener {


    private View view;
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private ProjectListener projectListener;
    private TextView pendingText;
    private Employer employer;
    private ProgressBar mProgressBar;


    private static final String LOG = Emp_MyProjects_In_Progress_Fragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_in_progress_projects, container, false);

        mProgressBar = view.findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        long employer_id = MySharedPreference.getLong(getContext(), Constants.Keys.EMPLOYER_ID, -1);
        employer = new Employer(employer_id);


        pendingText = view.findViewById(R.id.emp_InProgress_text);


        pendingText.setText("No projects");


        return view;
    }



    public interface ProjectListener{

        void onProjectItemSelected(Project project);
    }//End of interface

    @Override
    public void onProjectItemSelectedAdapter(Project project) {

        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project",project);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void setListener (ProjectListener projectListener)
    {
        this.projectListener = projectListener;

    }

    private void wrongInfoDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // Setting Dialog Title
        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    @Override
    public void onResume() {
        super.onResume();

        ApiClients.getAPIs().getProjectByStatus("1", employer).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                mProgressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    projectList = (List) response.body();
                    pendingText.setVisibility(View.GONE);
                    setTheAdapter();

                }
                else{

                    pendingText.setText("No Projects");
                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                pendingText.setText("Please try again later");

            }
        });

    }

public void setTheAdapter(){
    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    if(!projectList.isEmpty()) {
        adapter = new ProjectAdapter(projectList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
}
