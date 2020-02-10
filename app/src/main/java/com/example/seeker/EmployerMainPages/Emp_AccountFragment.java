package com.example.seeker.EmployerMainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.R;

public class Emp_AccountFragment extends Fragment implements View.OnClickListener {

    View view;
    Button switch_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_emp_account, container, false);

        switch_btn = (Button) view.findViewById(R.id.switch_to_f_btn);
        switch_btn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(Emp_AccountFragment.this.getActivity(), FreelancerMainActivity.class);
        startActivity(intent);
    }
}
