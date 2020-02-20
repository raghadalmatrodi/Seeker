package com.example.seeker.EmployerMainPages;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_MyProjectsFragment;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.Emp_SearchFragment;
import com.example.seeker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.seeker.R.string.Emp_title_msg;

public class EmployerMainActivity extends AppCompatActivity {


    // private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_employer);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Emp_MessagesFragment());
        // toolbar.setTitle("Home");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_msg:
                    loadFragment(new Emp_MessagesFragment());
                    Toast.makeText(EmployerMainActivity.this, Emp_title_msg, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_projects:
                    loadFragment(new Emp_MyProjectsFragment());
                    Toast.makeText(EmployerMainActivity.this, R.string.Emp_title_project, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_post:
                    loadFragment(new Emp_PostFragment());
                    Toast.makeText(EmployerMainActivity.this, R.string.Emp_title_post, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_search:
                    loadFragment(new Emp_SearchFragment());
                    Toast.makeText(EmployerMainActivity.this, R.string.Emp_title_search, LENGTH_LONG).show();
                    return true;
                case R.id.navigation_account:
                    loadFragment(new Emp_AccountFragment());
                    Toast.makeText(EmployerMainActivity.this, R.string.Emp_title_account, LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_emp, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }//end load fragment


}//end class
