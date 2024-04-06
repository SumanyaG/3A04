package com.example.securechat;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatGroup {

    private DatabaseReference chatDb;

    public ChatGroup() {
        chatDb = FirebaseDatabase.getInstance().getReference().child("Groups");
    }


    private void updateCommunicationKeys(){

    }

    private void sendMessage(){

    }

    private void addUserToChatGroup(String user){
        chatDb.child("members").child(user).setValue(true);
    }

    private void removeUserToChatGroup(){

    }

}
