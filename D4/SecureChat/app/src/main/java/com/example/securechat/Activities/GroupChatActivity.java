package com.example.securechat.Activities;

/*
Sets up Group Chat Interface where
user can send their messages to members
of the group
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String currentGroupName;
    private ChatGroupManager chatGroupManager;
    private RecyclerView chatMessagesRecyclerView; 
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentGroupName = getIntent().getExtras().get("groupName").toString();
        setContentView(R.layout.activity_group_chat);

        InitializeFields();
        initializeRecyclerView();
        loadMessages();

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupName);
        chatGroupManager = new ChatGroupManager(currentGroupName);
    }

    private void initializeRecyclerView() {
        chatMessagesRecyclerView = findViewById(R.id.chat_messages_recycler_view);
        messageAdapter = new MessageAdapter(new ArrayList<>());
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessagesRecyclerView.setAdapter(messageAdapter);
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
        }else if(item.getItemId() == R.id.view_members){
            System.out.println("viewing members");
            retrieveAndDisplayMembersInGroup();
        }


        return super.onOptionsItemSelected(item);
    }


    private void retrieveAndDisplayMembersInGroup() {

        System.out.println(" in retrieve and display members");
        chatGroupManager.retrieveAllMembers(currentGroupName, new ChatGroupManager.MembersRetrievedCallback() {
            @Override
            public void onMembersRetrieved(Set<String> members) {
                System.out.println("%%%%%%%%%%%");
                System.out.println(members);
                ListView list_view = findViewById(R.id.members_list_view);

                AlertDialog.Builder builder = new AlertDialog.Builder(GroupChatActivity.this, R.style.AlertDialog);
                builder.setTitle("Members");
                String[] memberArray = members.toArray(new String[0]);
                builder.setItems(memberArray, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void loadMessages() {
        DatabaseReference messagesRef = chatGroupManager.getMessagesReference(currentGroupName); 
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
    
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
    
                    if (message != null) {
                        if (!message.isEphemeral || System.currentTimeMillis() <= message.getExpirationTimestamp()) {
                            messageList.add(message);
                        } else {
                            snapshot.getRef().removeValue();
                        }
                    }
                }
    
                messageAdapter.notifyDataSetChanged(); 
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GroupChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    

}