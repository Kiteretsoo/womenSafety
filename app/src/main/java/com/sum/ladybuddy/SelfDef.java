package com.sum.ladybuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelfDef extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<VideoModel> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_self_def);

        recyclerView = findViewById(R.id.recyclerView);

        // Prepare the list of 3 pre-stored self-defense videos
        videoList = new ArrayList<>();
        videoList.add(new VideoModel("Self Defense Video 1", "https://youtu.be/T7aNSRoDCmg?feature=shared"));//"https://www.example.com/video1.mp4"));
        videoList.add(new VideoModel("Self Defense Video 2", "https://www.youtube.com/watch?v=T7aNSRoDCmg&pp=ygUhc2VsZiBkZWZlbmNlIHRlY2huaXF1ZXMgZm9yIHdvbWVu"));
        videoList.add(new VideoModel("Self Defense Video 3", "https://www.example.com/video3.mp4"));

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(this, videoList);
        recyclerView.setAdapter(videoAdapter);


    }
}
