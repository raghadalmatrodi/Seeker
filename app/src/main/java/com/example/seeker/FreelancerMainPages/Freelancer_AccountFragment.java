package com.example.seeker.FreelancerMainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.R;

public class Freelancer_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_freelancer_account, container, false);

        switch_btn = (Button) view.findViewById(R.id.switch_to_e_btn);
        switch_btn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(Freelancer_AccountFragment.this.getActivity(), EmployerMainActivity.class);
        startActivity(intent);
    }
}
