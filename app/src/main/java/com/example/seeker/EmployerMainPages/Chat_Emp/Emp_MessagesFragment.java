package com.example.seeker.EmployerMainPages.Chat_Emp;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_In_Progress_Fragment;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Emp_MessagesFragment extends Fragment  implements ChatAdapter.OnItemClickListener{


    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Chat> chatList;
    private static final String LOG = Emp_MessagesFragment.class.getSimpleName();
    View view;
    int position =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_emp_messages, container, false);
        chatList = new ArrayList<>();


        prepareChats();

        // adapter.setAmIParent(true);
        return view;
    }

    private void prepareAdapter() {

        adapter = new ChatAdapter(getContext(), chatList);
        recyclerView = (RecyclerView) view.findViewById(R.id.chats_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void prepareChats() {
      ApiClients.getAPIs().findChatsByUser(MySharedPreference.getLong(getContext(),Constants.Keys.USER_ID,-1))
              .enqueue(new Callback<List<Chat>>() {
          @Override
          public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
              if(response.isSuccessful()){
                  Log.i(LOG, "onResponse suc: " + response.toString());
                  Log.i(LOG,"The user id:" + MySharedPreference.getLong(getContext(),Constants.Keys.USER_ID,-1) );

                  chatList = response.body();
                  prepareAdapter();

              }else{
                  Log.i(LOG, "onResponse not suc: " + response.toString());

              }

          }

          @Override
          public void onFailure(Call<List<Chat>> call, Throwable t) {
              Log.i(LOG, "onFailure : " + t.toString());

          }
      });

    }



    @Override
    public void onItemClick(Chat chat) {
        Log.i(LOG, "onClick : " );

        Intent intent = new Intent(getActivity(),Emp_ChatMessages.class);
        intent.putExtra("chat" , chat);
        startActivity(intent);

    }
}
