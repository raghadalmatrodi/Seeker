package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.Emp_PostFragment;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Responses.ProjectResponse;
import com.example.seeker.PostBid.ViewBid;
import com.example.seeker.PostProject.CategoryAdapter;
import com.example.seeker.PostProject.ProjectCategoryFragment;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo 6 hind implemented serializable
public class Emp_MyProjects_Pending_Fragment extends Fragment implements Serializable ,ProjectAdapter.ProjectAdapterListener{

    private View view;
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private ProjectListener projectListener;
    private TextView pendingText;
    private Employer employer;

    private ProgressBar mProgressBar;

    private static final String LOG = Emp_MyProjects_Pending_Fragment.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_pending_projects, container, false);

        mProgressBar = view.findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        long employer_id = MySharedPreference.getLong(getContext(), Constants.Keys.EMPLOYER_ID, -1);
        employer = new Employer(employer_id);

        pendingText = view.findViewById(R.id.emp_pending_text);



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
        bundle.putInt("pending",1);
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

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    @Override
    public void onResume() {
        super.onResume();


        ApiClients.getAPIs().getProjectByStatus("0", employer ).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                mProgressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    projectList =  response.body();
                    pendingText.setVisibility(View.GONE);
                   // adapter.notifyDataSetChanged();
                    setTheAdapter();

                }
                else{

                    pendingText.setText("No Projects");
                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                pendingText.setText("fail");

            }
        });

    }


public void setTheAdapter(){
    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    if(!projectList.isEmpty())
        adapter = new ProjectAdapter(projectList);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
    if(!projectList.isEmpty())
   adapter.setListener(this);
    recyclerView.setNestedScrollingEnabled(true);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
    recyclerView.addItemDecoration(dividerItemDecoration);

}


}
