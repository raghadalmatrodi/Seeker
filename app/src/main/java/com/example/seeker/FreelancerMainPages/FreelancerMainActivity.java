package com.example.seeker.FreelancerMainPages;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seeker.EmployerMainPages.Chat_Emp.Emp_MessagesFragment;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.Freelancer_MyProjectsFragment;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.Freelancer_viewProjectFragment;
import com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.Freelancer_SearchFragment;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.seeker.R.string.Emp_title_msg;

public class FreelancerMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_freelancer);

        Log.d("Token" , MySharedPreference.getString(getApplicationContext(), Constants.Keys.TOKEN_ID,""));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationF);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();
        Project project = null;


        if(intent.hasExtra("project")){
         project = (Project) intent.getSerializableExtra("project");
        }

        if(intent.hasExtra("Fragment")){

            if(intent.getStringExtra("Fragment").equalsIgnoreCase("project")) {
                Fragment fragment = new Freelancer_viewProjectFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("project", project);

                fragment.setArguments(bundle);

                loadFragment(fragment);
        }
        }else{
            loadFragment(new Emp_MessagesFragment());


        }


        // toolbar.setTitle("Home");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_msg:
                    loadFragment(new Freelancer_MessagesFragment());
                    Toast.makeText(FreelancerMainActivity.this, Emp_title_msg, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_projects:
                    loadFragment(new Freelancer_MyProjectsFragment());
                    Toast.makeText(FreelancerMainActivity.this, R.string.Emp_title_project, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_search:
                    loadFragment(new Freelancer_SearchFragment());
                    Toast.makeText(FreelancerMainActivity.this, R.string.Emp_title_search, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_account:
                    loadFragment(new Freelancer_AccountFragment());
                    Toast.makeText(FreelancerMainActivity.this, R.string.Emp_title_account, LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_freelancer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }//end load fragment


}//end class
