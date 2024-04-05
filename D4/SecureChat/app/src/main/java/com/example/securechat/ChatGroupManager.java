package com.example.securechat;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupManager extends ChatGroupManagement{

    private static DatabaseReference chatDb;
    private static String groupName;
    public ChatGroupManager() {
        chatDb = FirebaseDatabase.getInstance().getReference().child("Groups");
    }

    @Override
    public void getChatGroup(String chatGroupID) {
        DatabaseReference groupRef = chatDb.child(groupName).child("members");

        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> members = new ArrayList<>();
                for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                    String memberId = memberSnapshot.getKey();
                    members.add(memberId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void displayPage() {

    }

    @Override
    public void addUserToChatGroup(String userId) {
        groupName = userId; // temporarily
        DatabaseReference groupRef = chatDb.child(groupName).child("members");
        groupRef.child(userId).setValue(true);
    }

    @Override
    public void removeUserFromChatGroup() {

    }

    @Override
    public void retrieveAllMessages() {

    }

    @Override
    public void retrieveNewMessages() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void sendCommunicationKeys() {

    }
}
