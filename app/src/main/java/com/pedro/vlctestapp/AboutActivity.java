package com.pedro.vlctestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    private ArrayList<AboutObj> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActivityCollector.addActivity(this);
        initAbout();
        RecyclerView recyclerView = findViewById(R.id.recycle_view_about);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AboutAdaptor adaptor = new AboutAdaptor(mList);
        recyclerView.setAdapter(adaptor);
        Intent intent = getIntent();
    }

    private void initAbout() {
        AboutObj app_name = new AboutObj("APP Name:", "Intelligent Lab");
        mList.add(app_name);

        AboutObj developer_team = new AboutObj("Developer Team:", "-");
//                "Research Center of Intelligent Robotics' android group");
        mList.add(developer_team);
        AboutObj supervisor = new AboutObj("Supervisor:", "-");
//                "Su Jianbo");
        mList.add(supervisor);
        AboutObj group_member = new AboutObj("Group Members:", "-");
        mList.add(group_member);
        AboutObj tech_support = new AboutObj("Technical Support:", "-");
        mList.add(tech_support);
        AboutObj design_support = new AboutObj("Design Support:", "-");
        mList.add(design_support);
        AboutObj version = new AboutObj("Release Version:", "V1.0");
        mList.add(version);
        AboutObj copy_right = new AboutObj("Copy right:", "-");
//        "©  RCIR in SJTU");
        mList.add(copy_right);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}