package com.example.securechat.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    private ChatGroupManager chatGroupManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_participant_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_members_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentGroupName = getIntent().getExtras().get("groupName").toString();
        InitializeFields();
        retrieveAndDisplayMembersNotInGroup();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMember = parent.getItemAtPosition(position).toString();
                showConfirmationDialog(selectedMember);

            }
        });


    }

    private void showConfirmationDialog(String selectedMember) {
        AlertDialog.Builder builder = new AlertDialog.Builder(list_view.getContext(), R.style.AlertDialog);
        builder.setTitle("Confirm Addition");
        builder.setMessage(Html.fromHtml("Do you want to add <b>" + selectedMember + "</b> to the group?"));

        builder.setPositiveButton("Yes", (dialog, which) -> {
            ChatGroupManager chatGroupManager = new ChatGroupManager(currentGroupName);
            chatGroupManager.addUserToChatGroup(selectedMember);
            retrieveAndDisplayMembersNotInGroup();
            Toast.makeText(GroupParticipantAddActivity.this, selectedMember + " was added successfully to " +currentGroupName, Toast.LENGTH_SHORT).show();

        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

    private void InitializeFields() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.group_chat_bar_layout);
        getSupportActionBar().setTitle("Add Members to " + currentGroupName);
        list_view = findViewById(R.id.members_list_view);
        chatGroupManager = new ChatGroupManager(currentGroupName);
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
        chatGroupManager.retrieveMembersNotInGroup(currentGroupName, new ChatGroupManager.MembersRetrievedCallback() {
            @Override
            public void onMembersRetrieved(Set<String> membersNotInGroup) {
                System.out.println(membersNotInGroup);
                adapter = new ArrayAdapter<>(GroupParticipantAddActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(membersNotInGroup));
                list_view.setAdapter(adapter);
            }
        });
    }

}