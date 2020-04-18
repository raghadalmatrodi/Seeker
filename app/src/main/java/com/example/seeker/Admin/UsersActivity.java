package com.example.seeker.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.UserSearchAdapter;
import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerViewUser;
    SearchView searchView;
    private List<User> userList;
    ImageView backBtn;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        searchView=findViewById(R.id.search_user);
        backBtn = findViewById(R.id.users_back);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String queryString) {


                usersAdapter.getFilter().filter(queryString);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                usersAdapter.getFilter().filter(queryString);


                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllUsers();
    }

    private void getAllUsers(){

        ApiClients.getAPIs().getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){

                    userList = (List) response.body();
                    setUserRecyclerView();

                }
                else{

                    Log.i("UserActivity", "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Log.i("UserActivity", "Fail");
            }
        });

    }

    private void setUserRecyclerView() {

        recyclerViewUser = (RecyclerView) findViewById(R.id.recycler_view_user);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(userList,this);
        recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUser.setAdapter(usersAdapter);
        recyclerViewUser.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewUser.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewUser.addItemDecoration(dividerItemDecoration);
    }

}
