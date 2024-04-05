package com.example.securechat;

import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

public class ChatListDatabase {

    private static DatabaseReference chatGroups;
    public static DatabaseReference getAllChatGroups(int userID){
        /*
        Get all chat groups for userID
        */
        return FirebaseDatabase.getInstance().getReference().child("Groups");
    }

    public static boolean addChatGroups(String groupName){
        try{
            chatGroups = FirebaseDatabase.getInstance().getReference();
            chatGroups.child("Groups").child(groupName).setValue("");
        }catch(Exception e){

            return false;
        }
        return true;
    }
    private void removeChatGroups(){

    }
}


