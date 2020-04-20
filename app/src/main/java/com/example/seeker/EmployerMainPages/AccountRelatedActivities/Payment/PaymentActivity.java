package com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.List;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    List<Project> projectList;
    ImageView backBtn;
    TextView noPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void init() {
        backBtn = findViewById(R.id.payment_back);
        noPayment = findViewById(R.id.no_payment);

    }

    @Override
    public void onResume() {
        super.onResume();

        long employer_id = MySharedPreference.getLong(this, Constants.Keys.EMPLOYER_ID, -1);
        Employer employer = new Employer(employer_id);
        ApiClients.getAPIs().getProjectByStatus("1", employer).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if(response.isSuccessful()){

                    projectList = (List) response.body();

                    if(!projectList.isEmpty()){

                        noPayment.setVisibility(View.GONE);
                    }

                    ExpandableRecyclerView eRecyclerView = findViewById(R.id.purchase_list);
                    PaymentAdapter mPIAdapter = new PaymentAdapter(projectList, PaymentActivity.this); // purchaseItemAdapter
                    eRecyclerView.setAdapter(mPIAdapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    eRecyclerView.setLayoutManager(mLayoutManager);


                }
                else{


                    Log.i("test", "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {



            }
        });

    }
}
