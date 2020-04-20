package com.example.seeker.Admin;

import android.content.Intent;

import android.media.Image;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.seeker.EmployerMainPages.AccountRelatedActivities.SampleWorkAdapter;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.ViewAttachmentActivity;
import com.example.seeker.Model.Bid;

import com.example.seeker.Model.Project;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.StorageDocument;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

public class ViewProjectActivity extends AppCompatActivity implements SampleWorkAdapter.OnItemClickListener , BidsAdapter.BidsAdapterListener {

    private static final String LOG = ViewProjectActivity.class.getSimpleName();

    Project project ;
    TextView title;
    TextView descreption;
    TextView budget;
    TextView type;
    TextView skills;
    TextView deadline;
    ImageView backButton;
    TextView employerName;
    TextView createdAt;
    LinearLayout EmployerView;
    ImageView employerPic;

    List<StorageDocument> files;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private SampleWorkAdapter sampleWorkAdapter;

    List<Bid> bids;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);

                Intent i = getIntent();
        project = (Project) i.getSerializableExtra("projectObj");

        init();
        setProjectInformation();
        bids = project.getBids();
        TextView number_of_bids = findViewById(R.id.activity_numberOfBids);
        if (bids!=null)
        number_of_bids.setText("("+ (bids.size() )+ ")");
        else {
            number_of_bids.setText("("+ 0+ ")");

        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        setTheAdapter();
        setTheAttachmentAdapter();

    }



    private void init() {

    backButton = findViewById(R.id.activity_project_view_back);
        title = findViewById(R.id.activity_project_title);
        descreption = findViewById(R.id.activity_project_des);
        deadline = findViewById(R.id.activity_project_date);
        budget = findViewById(R.id.activity_project_budget);
        type = findViewById(R.id.activity_project_type);
        skills = findViewById(R.id.activity_project_skills);
        employerName =findViewById(R.id.activity_employer_name);
        EmployerView = findViewById(R.id.activity_employer_row);
        createdAt = findViewById(R.id.activity_createdAt_project);
        employerPic = findViewById(R.id.activity_employer_picture);


    }


    public void setProjectInformation(){

        if(project.getEmployer()!=null)
        if(project.getEmployer().getId() == MySharedPreference.getLong(this,Constants.Keys.EMPLOYER_ID,-1)){
            EmployerView.setVisibility(View.GONE);
        }

        if(project.getEmployer().getUser().getAvatar()!= null)
            Glide.with(this)
                    .load(project.getEmployer().getUser().getAvatar())
                    .placeholder(R.drawable.user).apply(RequestOptions.circleCropTransform())
                    .into(employerPic);


        if( (project.getTitle() != null))
            if(!project.getTitle().trim().equals(""))
                title.setText(project.getTitle());


        if(project.getDescription() != null )
            if(!project.getDescription().trim().equals(""))
                descreption.setText(project.getDescription());

        if(!(project.getBudget() == 0))
            budget.setText(project.getBudget() +"");

        if(project.getType() != null)
        if(!project.getType().trim().equals("") || !(project.getType() == null)){

            if(project.getType().equals("0")){
                type.setText("Online");
            }
            else {
                type.setText("On-field");
            }

        }


        String skill ="";

        if( project.getSkills() !=null){

            if (!project.getSkills().isEmpty()) {
                for (Skill s : project.getSkills()) {
                    skill += s.getName() + " \n";
                }
            }
            skills.setText(skill);
        }

        if(project.getEmployer() != null)
        employerName.setText(project.getEmployer().getUser().getUsername());

        if(project.getDeadline() != null){
            deadline.setText(project.getDeadline().substring(0,10));
        }
        if(project.getCreatedAt() != null){
            createdAt.setText(project.getCreatedAt().substring(0,10));
        }


    }


    public void setTheAdapter(){

        recyclerView = (RecyclerView) findViewById(R.id.activity_recycler_view_b);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BidsAdapter(this,bids, project);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
  adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }
    public void setTheAttachmentAdapter()  {
        files = project.getAttachments();
        if(!files.isEmpty()) {
            TextView noAttachments = findViewById(R.id.activity_no_attachment);
            noAttachments.setVisibility(View.GONE);
        }
        recyclerView =  findViewById(R.id.activity_attachment_recycle_view);
        sampleWorkAdapter = new SampleWorkAdapter(this, files);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sampleWorkAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(sampleWorkAdapter);
    }

    @Override
    public void onItemClick(StorageDocument storageDocument) {
        List<String> images = new ArrayList<>();
        List<Image> images1 =null;
        project.getAttachments().forEach(sampleWork -> {
            images.add(sampleWork.getUrl());
        });

        Intent intent = new Intent(this , ViewAttachmentActivity.class);
        intent.putExtra("image" , storageDocument.getUrl());
        startActivity(intent);
    }

    @Override
    public void onBidItemClick(Bid bid){

    }
    @Override
    public void onAcceptBidItemClick(Bid bid){

    }


}
