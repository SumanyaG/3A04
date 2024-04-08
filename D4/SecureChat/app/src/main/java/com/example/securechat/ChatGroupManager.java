package com.example.securechat;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatGroupManager extends ChatGroupManagement {

    private static DatabaseReference chatDb;
    private String groupName;

    public ChatGroupManager(String groupName) {
        chatDb = FirebaseDatabase.getInstance().getReference().child("Groups");
        this.groupName = groupName;
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
        DatabaseReference groupRef = chatDb.child(groupName).child("Members");
        groupRef.child(userId).setValue(true);
    }

    @Override
    public void removeUserFromChatGroup() {

    }

    @Override
    public boolean deleteChatGroup() {
        return ChatListDatabase.removeChatGroups(this.groupName);
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


    public interface MembersRetrievedCallback {
        void onMembersRetrieved(Set<String> members);
    }

    @Override
    public void retrieveAllMembers(String groupName, MembersRetrievedCallback callback){
        DatabaseReference groupMembersRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupName).child("Members");
        final Set<String> members = new HashSet<>();

        groupMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                    String memberId = memberSnapshot.getKey();
                    members.add(memberId);
                }
                callback.onMembersRetrieved(members);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void retrieveMembersNotInGroup(String groupName, MembersRetrievedCallback callback) {
        DatabaseReference groupMembersRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupName).child("Members");
        DatabaseReference contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        final Set<String> membersNotInGroup = new HashSet<>();
        final Set<String> groupMembers = new HashSet<>();
        final Set<String> allContacts = new HashSet<>();

        groupMembersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot groupSnapshot) {
                for (DataSnapshot memberSnapshot : groupSnapshot.getChildren()) {
                    String memberId = memberSnapshot.getKey();
                    groupMembers.add(memberId);
                }

                contactsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot contactsSnapshot) {
                        for (DataSnapshot contactSnapshot : contactsSnapshot.getChildren()) {
                            String contactId = contactSnapshot.getKey();
                            allContacts.add(contactId);
                        }

                        membersNotInGroup.addAll(allContacts);
                        membersNotInGroup.removeAll(groupMembers);

                        callback.onMembersRetrieved(membersNotInGroup);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle potential errors
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });
    }







}
