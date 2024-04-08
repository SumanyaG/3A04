package com.example.securechat.Activities;

/*
Sets up Group Chat Interface where
user can send their messages to members
of the group
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securechat.ChatGroupManager;
import com.example.securechat.ChatGroupPage;
import com.example.securechat.ChatMessage;
import com.example.securechat.R;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securechat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String currentGroupName, currentUserID, currentUserName;
    private ChatGroupManager chatGroupManager;

    private RecyclerView chatMessagesRecyclerView;

    private MessageAdapter messageAdapter;

    private List<ChatMessage> messageList = new ArrayList<>();

    private EditText userMessageInput;
    private ScrollView mScrollView;
    private ImageButton sendMessageButton;
    private TextView displayTextMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentGroupName = getIntent().getExtras().get("groupName").toString();


        InitializeFields();




        sendMessageButton = findViewById(R.id.send_message_button);
        System.out.println("HEREE TESTing");
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("PRESSED SEND BUTTON!!!!!!");
                Log.d("BUTTONS", "BUTTON TAPPED");
                SaveMessageInfoToDatabase();
                userMessageInput.setText("");
            }
        });


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

    private void SaveMessageInfoToDatabase() {

        System.out.println("sending  message to db");
        String message = userMessageInput.getText().toString();

        if (TextUtils.isEmpty(message)){
            Toast.makeText(this, "Please write messge first...", Toast.LENGTH_SHORT);
        }else{
            String senderID = "sender_id1";
            boolean isEphemeral = false;
            chatGroupManager.sendMessage(senderID, message, isEphemeral);
        }
    }

    private void InitializeFields() {
        mToolbar = (Toolbar) findViewById(R.id.group_chat_bar_layout);
        getSupportActionBar().setTitle(currentGroupName);
        chatGroupManager = new ChatGroupManager(currentGroupName);



        userMessageInput = findViewById(R.id.input_group_message);
        displayTextMessages = findViewById(R.id.group_chat_chat_text_display);
        mScrollView = findViewById(R.id.my_scroll_view);
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

    private void initializeRecyclerView() {
        chatMessagesRecyclerView = findViewById(R.id.chat_messages_recycler_view);
        messageAdapter = new MessageAdapter(new ArrayList<>());
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessagesRecyclerView.setAdapter(messageAdapter);
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