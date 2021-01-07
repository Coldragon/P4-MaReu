package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mareu.databinding.ActivityMeetingListBinding;
import com.example.mareu.generator.GenerateMeetingList;
import com.example.mareu.events.RemoveMeetingEvent;
import com.example.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MeetingListActivity extends AppCompatActivity {

    RecyclerView mMeetingsRecyclerView;
    List<Meeting> mMeetingsList;
    ActivityMeetingListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMeetingListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mMeetingsRecyclerView = mBinding.meetingList;

        initRecyclerView();
        initButtons();
    }

    private void initButtons() {
        mBinding.addMeetingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MeetingAddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMeetingsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void initRecyclerView()
    {
        Context context = mMeetingsRecyclerView.getContext();
        mMeetingsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mMeetingsList = GenerateMeetingList.generateMeetings();
        mMeetingsRecyclerView.setAdapter(new MeetingListRecyclerViewAdapter(mMeetingsList));

    }

    @Subscribe
    public void onRemoveMeeting(RemoveMeetingEvent event) {
        int index = mMeetingsList.indexOf(event.mMeeting);
        mMeetingsList.remove(event.mMeeting);
        Toast.makeText(getApplicationContext(),"Delete meeting",Toast.LENGTH_SHORT).show();
        mMeetingsRecyclerView.getAdapter().notifyItemRemoved(index);
    }
}