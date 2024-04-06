package com.example.securechat.Activities;

/*
Sets up Group Chat Interface where
user can send their messages to members
of the group
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.securechat.ChatGroupManager;
import com.example.securechat.ChatGroupPage;
import com.example.securechat.R;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securechat.R;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String currentGroupName;

    private ChatGroupManager chatGroupManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentGroupName = getIntent().getExtras().get("groupName").toString();


        InitializeFields();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void InitializeFields() {
        mToolbar = (Toolbar) findViewById(R.id.group_chat_bar_layout);
        getSupportActionBar().setTitle(currentGroupName);
        chatGroupManager = new ChatGroupManager(currentGroupName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upper_nav_chat_menu, menu);
        return true;
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_members) {
            Intent intent = new Intent(this, GroupParticipantAddActivity.class);
            intent.putExtra("groupName", currentGroupName);
            startActivity(intent);
            Toast.makeText(this, "adding members", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete_chat){
            boolean isDeleteSuccess = chatGroupManager.deleteChatGroup();

            if (isDeleteSuccess){
                Toast.makeText(getApplicationContext(), currentGroupName+" deleted successfully", Toast.LENGTH_SHORT).show();
                recreate();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "failed to delete group", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}