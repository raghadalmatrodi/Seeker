package com.example.seeker.PostProject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;

import static com.example.seeker.PostProject.ProjectTypeFragment.*;

public class ProjectTypeFragment extends Fragment {

    private View view;
    private ProjectTypeListener projectTypeListener ;

    private RelativeLayout onlineProjectBtn;
    private RelativeLayout onFieldProjectBtn;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_type, container, false);


        init();

        onlineProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                projectTypeListener.onProjectTypeItemSelected("OnlineProject");

            }
        });

        onFieldProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                projectTypeListener.onProjectTypeItemSelected("OnFieldProject");
            }
        });


        return view;
    }//End of onCreateView()

    @Override
    public void onResume() {
        super.onResume();


    }

    private void init() {

        onlineProjectBtn = view.findViewById(R.id.online_project);
        onFieldProjectBtn = view.findViewById(R.id.onfield_project);
    }//End of init()

    public void setListener (ProjectTypeListener projectTypeListener)
    {
        this.projectTypeListener = projectTypeListener;
    }


    public interface ProjectTypeListener{

        public void onProjectTypeItemSelected(String projectType);

    }//End of interface

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        projectTypeListener = (ProjectTypeListener) getActivity();
//    }
//


}

