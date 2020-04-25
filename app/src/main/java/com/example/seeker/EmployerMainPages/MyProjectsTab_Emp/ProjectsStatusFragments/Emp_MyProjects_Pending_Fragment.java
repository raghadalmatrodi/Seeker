package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.seeker.Model.Responses.ApiResponse;

import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo 6 hind implemented serializable
public class Emp_MyProjects_Pending_Fragment extends Fragment implements Serializable, ProjectAdapter.ProjectAdapterListener {


    String expiryDate;
    private String timeString;
    private LocalDateTime expiryLocalDateTime;
    private View view;
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private List<Project> projectList;
    private ProjectListener projectListener;
    private TextView pendingText;
    private Employer employer;



    private ProgressBar mProgressBar;

    private static final String LOG = Emp_MyProjects_Pending_Fragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pending_projects, container, false);

        mProgressBar = view.findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        long employer_id = MySharedPreference.getLong(getContext(), Constants.Keys.EMPLOYER_ID, -1);
        employer = new Employer(employer_id);

        pendingText = view.findViewById(R.id.emp_pending_text);


        return view;
    }

    public interface ProjectListener {

        void onProjectItemSelected(Project project);

    }//End of interface

    @Override
    public void onProjectItemSelectedAdapter(Project project) {

        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        bundle.putInt("pending", 1);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @Override
    public void onProjectExtendsSelectedAdapter(Project project) {

        expiryDate = project.getExpiry_date();

        showDialog("Are you sure you want to extend this project for five days more?", 1, project);

    }

    @Override
    public void onProjectDeleteSelectedAdapter(Project project) {

        showDialog("Are you sure you want to delete this project?", 0, project);


    }


    public void setListener(ProjectListener projectListener) {
        this.projectListener = projectListener;

    }

    private void infoDialog(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    @Override
    public void onResume() {
        super.onResume();


prepareProjects();


    }

    private void prepareProjects() {




        ApiClients.getAPIs().getProjectByStatus("0", employer).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                        projectList = response.body();
                    if(projectList != null) {
                        for (int i = 0; i < projectList.size(); i++) {


                            if (checkToDelete(projectList.get(i).getExpiry_date())) {
//                                deleteProject(projectList.get(i));
//                                projectList.remove(i);
                            }


                        }
                        pendingText.setVisibility(View.GONE);
                        setTheAdapter();
                        // adapter.notifyDataSetChanged();
                    }else{

                        pendingText.setText("No Projects");
                        Log.i(LOG, "onResponse not suc: " + response.toString());

                    }

                } else {

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


    public void setTheAdapter() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (!projectList.isEmpty())
            adapter = new ProjectAdapter( projectList, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!projectList.isEmpty())
            adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        if (!projectList.isEmpty())
        adapter.notifyDataSetChanged();


    }


    private void deleteProject(Project project) {
        ApiClients.getAPIs().deleteProject(project.getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
//to refresh the list
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }


    private void updateProject(Project project) {
        expiryDate = setExpiryDate();
        expiryLocalDateTime = convertStringToLocalDateTime(expiryDate);
        project.setExpiry_date(expiryLocalDateTime.toString());


        ApiClients.getAPIs().updateExpiryDate(project).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
       getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }


    private String setExpiryDate() {

        expiryDate = expiryDate.substring(0, 10);
        LocalDate today = LocalDate.parse(expiryDate);
        // increment days by 5
        System.out.println("Current Date: " + today);
        today = today.plusDays(5);
        System.out.println("New Date: " + today);
        String formattedDate = today.toString();
        today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println("Formatted Date: " + formattedDate);


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateString = formatter.format(date);
        timeString = dateString.substring(10);
        formattedDate = formattedDate + timeString;
        return formattedDate;


    }


    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }


    public void showDialog(final String msg, int action, Project project) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (action == 0) {
                            deleteProject(project);
                            infoDialog("Successful", "Your project has been deleted successfully.");
                        }


                        if (action == 1) {
                            if (checkExpiryDate(project.getExpiry_date(), project.getCreatedAt())) {
                                updateProject(project);
                                infoDialog("Successful", "Your project has been extended successfully.");
                            } else
                                infoDialog("Wrong", "You cannot extend the project before 10 days of its posted date.");
                        }


                    }//End onClick()


                });//End BUTTON_POSITIVE


        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();

                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.show();

    }//end showDialog


    public boolean checkExpiryDate(String expiryDate, String createdAt) {

        expiryDate = expiryDate.substring(0, 10);
        createdAt = createdAt.substring(0, 10);

        LocalDate dateExpiry = LocalDate.parse(expiryDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dateCreated = LocalDate.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(dateCreated.atStartOfDay(), dateExpiry.atStartOfDay());
        long diffDays = diff.toDays();
        System.out.println("difference for expiry is"+ diffDays);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateString = formatter.format(date);
        dateString = dateString.substring(0, 10);
        if (diffDays == 1 && dateString.equals(expiryDate))
            return true;

        return false;
    }



    public boolean checkToDelete(String expiryDate) {

        expiryDate = expiryDate.substring(0, 10);
        LocalDate localDate = LocalDate.now();

        LocalDate dateExpiry = LocalDate.parse(expiryDate, DateTimeFormatter.ISO_LOCAL_DATE);
       // LocalDate todayDate = LocalDate.parse(today,DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between( localDate.atStartOfDay(),dateExpiry.atStartOfDay());
        long diffDays = diff.toDays();

        //to test
System.out.println("difference for delete is"+ diffDays);


        if (diffDays == 0 )
            return true;

        return false;
    }


}
