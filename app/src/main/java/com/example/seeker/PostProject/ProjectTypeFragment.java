package com.example.seeker.PostProject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;

public class ProjectTypeFragment extends Fragment {

    private View view;

    private RelativeLayout onlineProjectBtn;
    private RelativeLayout onFieldProjectBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_type, container, false);


        init();

        onlineProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we will do something

            }
        });

        onFieldProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we will do something
            }
        });


        return view;
    }//End of onCreateView()

    private void init() {

        onlineProjectBtn = view.findViewById(R.id.online_project);
        onFieldProjectBtn = view.findViewById(R.id.onfield_project);
    }//End of init()
}
