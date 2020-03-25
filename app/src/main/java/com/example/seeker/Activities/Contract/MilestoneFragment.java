package com.example.seeker.Activities.Contract;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_MyProjectsFragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MilestoneFragment extends Fragment {

    private View view;


    private TextView amount;
    private ImageView backBtn;
    private ImageView addBtn;
    private Button saveBtn;
    private Button cancelBtn;
    private Contract contract;
    private RecyclerView recyclerView;
    private MilestoneSecondAdapter adapter;
    private List<Milestone> milestoneList  = new ArrayList<>();
    private double totalBudget = 0;
    private Project project;



    //dialog fixedPrice attribute
    private String expiryDate;
    private TextView deadline;
    private DatePickerDialog picker;
    private String title;
    private String budget;
    private double doubleBudget;
    private String timeString;
    private LocalDateTime finalLDT;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_milestone, container, false);

        init();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            contract =(Contract)bundle.getSerializable("contract");
            setInformation();
        }

        setRecyclerView();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (project.getPayment_type().equals("Hourly")) {

                     milestoneHourlyDialog();


                } else {

                    getTotalBudget();

                    if (totalBudget == contract.getPrice()) {
                        showDialog("you can't add more milestones");

                    } else {

                        milestoneFixedPriceDialog();
                    }

                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leaveFragment();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveFragment();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(project.getPayment_type().equals("FixedPrice")){
                    getTotalBudget();

                    if(totalBudget < contract.getPrice()){
                        showDialog("you have to complete all budget");
                    }else{

                        finalDialog();

                    }

                }else{

                    finalDialog();
                }



            }
        });

        return view;
    }

    private void finalDialog() {

        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        // Setting Dialog Message
        alertDialog.setTitle("Are you sure you want to save your milestones ?");


        //Setting positive "ok" Button
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {



                deleteMilestone();

                for(int i = 0; i < milestoneList.size(); i++){

                    addMilestone(milestoneList.get(i));
                }

                leaveFragment();






            }//end onClick
        });//end setPositiveButton


        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }//End of Dialog()

    private void deleteMilestone() {
        ApiClients.getAPIs().deleteMilestone(project.getMilestones().get(0).getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful()){
                    Log.i("onResponse successful milestone delete ",response.message());
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

    }

    private void leaveFragment() {

        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

    private void setRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MilestoneSecondAdapter(milestoneList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    private void addMilestone(Milestone milestone) {


            ApiClients.getAPIs().createMilestoneRequest(milestone).enqueue(new Callback<Milestone>() {
                @Override
                public void onResponse(Call<Milestone> call, Response<Milestone> response) {
                    if (response.isSuccessful()){
                        Log.i("onResponse successful ",response.message());
                    }
                    Log.i("onResponse Notsuccessful ",response.message());
                }


                @Override
                public void onFailure(Call<Milestone> call, Throwable t) {

                    Log.i("onResponse fail ", "fail");
                }
            });


        }

    private void setInformation() {
        amount.setText(String.valueOf(contract.getPrice()));

        project = contract.getProject();

    }

    private void init() {

        amount = view.findViewById(R.id.milestone_amount);
        backBtn = view.findViewById(R.id.milestone_back);
        addBtn = view.findViewById(R.id.add_milestone_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        recyclerView = view.findViewById(R.id.milestoneM_recycler_view);
    }

    private void milestoneFixedPriceDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.milestone_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText titleTextView = (EditText) dialogView.findViewById(R.id.dialog_title);
        deadline = (TextView) dialogView.findViewById(R.id.milestone_dialog_deadline);
        final EditText budgetTextView = (EditText) dialogView.findViewById(R.id.dialog_budget);
        final TextView visibleText  = (TextView) dialogView.findViewById(R.id.milestone_date_text);



        dialogBuilder.setTitle("Create Milestone");
        dialogBuilder.setMessage(" please fill all information");
        dialogBuilder.setCancelable(false);

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visibleText.setVisibility(TextView.VISIBLE);
                calendarDialog();

            }
        });
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                title = titleTextView.getText().toString();
                budget = budgetTextView.getText().toString();

                if(validate()){

                    // compare date
                    if(changeToLocalDate())
                    {
                       getTotalBudget();

                       doubleBudget = Double.valueOf(budget);

                       if(totalBudget+doubleBudget <= contract.getPrice()){

                           setTime();
                           Milestone milestone = new Milestone(doubleBudget,"0", finalLDT.toString(), title,contract.getProject());

                           milestoneList.add(milestone);
                           adapter.notifyDataSetChanged();


                       }else{

                           showDialog("Over budget!");
                       }




                    }else{


                        showDialog("Please Enter a valid date");
                    }




                }else{

                        showDialog("Missing Information");

                }







            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void milestoneHourlyDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.milestone_hourly_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText titleTextView = (EditText) dialogView.findViewById(R.id.dialog_hourly_title);
        deadline = (TextView) dialogView.findViewById(R.id.dialog_hourly_deadline);
        final TextView visibleText  = (TextView) dialogView.findViewById(R.id.milestone_date_text);


        dialogBuilder.setTitle("Create Milestone");
        dialogBuilder.setMessage(" please fill all information");
        dialogBuilder.setCancelable(false);

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visibleText.setVisibility(TextView.VISIBLE);
                calendarDialog();

            }
        });
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                title = titleTextView.getText().toString();

                if(validate()){

                    // compare date
                    if(changeToLocalDate())
                    {

                        doubleBudget = contract.getPrice();



                            setTime();
                            Milestone milestone = new Milestone(doubleBudget,"0", finalLDT.toString(), title,contract.getProject());

                            milestoneList.add(milestone);
                            adapter.notifyDataSetChanged();





                    }else{


                        showDialog("Please Enter a valid date");
                    }




                }else{

                    showDialog("Missing Information");

                }







            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void setTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dateString = formatter.format(date);
        timeString=  dateString.substring(10);

        String finalDate =  deadline.getText().toString()+timeString;

        finalLDT = convertStringToLocalDateTime(finalDate);




    }

    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    private void getTotalBudget() {

        totalBudget = 0;
        if( !milestoneList.isEmpty()){
            for(int i = 0; i < milestoneList.size(); i++){
                totalBudget += milestoneList.get(i).getAmount();
            }


        }

    }

    private boolean changeToLocalDate() {
        LocalDate today = LocalDate.parse(expiryDate);
        Log.i("Local date is ", today.toString());

        LocalDate mainDate = LocalDate.parse(contract.getDeadline().substring(0,10));
        Log.i("Local 2 date is ", mainDate.toString());

        Log.i("Compare To ", String.valueOf(mainDate.compareTo(today)));

        if(mainDate.compareTo(today) >= 0)
        {
            return true;
        }else {
            return false;
        }


//        contract.getDeadline()

    }

    private boolean validate() {

        if(title.isEmpty() || expiryDate.isEmpty()){

            return false;
        }

        return true;
    }

    private void calendarDialog() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int month = monthOfYear + 1;
                        if(month <= 9)
                        {
                            if(dayOfMonth <= 9){

                                deadline.setText("0"+ dayOfMonth + "-" +"0"+ month + "-" + year);

                                expiryDate = year+ "-" + "0" + month + "-" + "0" + dayOfMonth;

                            }else
                            {
                                deadline.setText(dayOfMonth + "-" +"0"+ month + "-" + year);
                                expiryDate = year+ "-" + "0" + month + "-" + dayOfMonth;
                            }


                        }else{

                            if(dayOfMonth <= 9){

                                deadline.setText("0"+dayOfMonth + "-" + month + "-" + year);
                                expiryDate = year + "-" + month + "-" +"0" + dayOfMonth ;
                            }else
                            {
                                deadline.setText(dayOfMonth + "-" + month + "-" + year);
                                expiryDate = year + "-" + month + "-" + dayOfMonth;

                            }

                        }




                    }
                }, year, month, day);

        picker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        picker.show();




    }

    public void showDialog(final String msg ) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        // alertDialog.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dialogbackground));
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.show();

    }//end showDialog
}
