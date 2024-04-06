package com.example.securechat.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securechat.ChatGroupManager;
import com.example.securechat.R;

import java.util.ArrayList;
import java.util.Set;

public class GroupParticipantAddActivity extends AppCompatActivity {

    private String currentGroupName;
    private ArrayAdapter<String> adapter;
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_participant_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EdgeToEdge.enable(this);

        currentGroupName = getIntent().getExtras().get("groupName").toString();
        InitializeFields();
        retrieveAndDisplayMembersNotInGroup();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_members_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void InitializeFields() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.group_chat_bar_layout);
        getSupportActionBar().setTitle("Add Members to " + currentGroupName);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveAndDisplayMembersNotInGroup() {
        ChatGroupManager chatGroupManager = new ChatGroupManager();
        chatGroupManager.retrieveMembersNotInGroup(currentGroupName, new ChatGroupManager.MembersRetrievedCallback() {
            @Override
            public void onMembersRetrieved(Set<String> membersNotInGroup) {
                System.out.println(membersNotInGroup);
                adapter = new ArrayAdapter<>(GroupParticipantAddActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(membersNotInGroup));
                list_view = findViewById(R.id.members_list_view);
                list_view.setAdapter(adapter);
            }
        });
    }

}