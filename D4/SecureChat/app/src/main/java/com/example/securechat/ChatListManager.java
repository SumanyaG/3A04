package com.example.securechat;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Context;


import androidx.annotation.NonNull;

import com.example.securechat.Activities.GroupChatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatListManager extends ChatListManagement{

    private static int userID = 123;
    private static ArrayList<String> list_of_groups = new ArrayList<>();
    private static ArrayAdapter<String> arrayAdapter;
    private static ListView list_view;
    private static DatabaseReference chatDb;

    private Button btnCreateGroup;

    Dialog dialog;
    Button btnDialogCreate, btnDialogCancel;


    public ChatListManager() {
        chatDb = FirebaseDatabase.getInstance().getReference().child("Groups");
    }

    public void displayPage(View groupFragmentView){
        InitializeFields(groupFragmentView);
        getAllUSerChatGroups(userID);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentGroupName = parent.getItemAtPosition(position).toString();
                Intent groupChatIntent = new Intent(groupFragmentView.getContext(), GroupChatActivity.class);
                groupChatIntent.putExtra("groupName", currentGroupName);
                groupFragmentView.getContext().startActivity(groupChatIntent);
            }
        });

        Button createGroupButton = groupFragmentView.findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNewGroup();
            }
        });
    }


   private void InitializeFields(View groupFragmentView) {
        list_view = (ListView) groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(groupFragmentView.getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
    }

    @Override
    public ArrayList<String> getAllUSerChatGroups(int userID) {
        chatDb = ChatListDatabase.getAllChatGroups(userID);
        chatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    set.add(((DataSnapshot) iterator.next()).getKey());
                }
                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list_of_groups;
    }

    private void requestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(list_view.getContext(), R.style.AlertDialog);
        builder.setTitle("Enter Group Name: ");

        final EditText groupNameField = new EditText(list_view.getContext());
        groupNameField.setHint("e.g. Team 1");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupName = groupNameField.getText().toString();
                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(list_view.getContext(), "Please Write Group Name...", Toast.LENGTH_SHORT).show();

                } else {
                    createChatGroup(groupName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }



    @Override
    public String createChatGroup(String groupName) {
        boolean isGroupAdded = ChatListDatabase.addChatGroups(groupName);
        if (isGroupAdded) {
            Toast.makeText(arrayAdapter.getContext(), groupName + " group is created successfully!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(arrayAdapter.getContext(), "failed to create group: " + groupName, Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    public void addUserToChatGroup() {


    }

    @Override
    public void removeUserFromChatGroup() {

    }
}
