package com.example.securechat;

import android.view.View;

import com.google.firebase.database.DatabaseReference;

import com.example.securechat.CreateChatGroup;
import com.example.securechat.ChatListDatabase;

import java.util.ArrayList;

abstract class ChatListManagement {

    public abstract void displayPage(View groupFragmentView);

    public abstract ArrayList<String> getAllUSerChatGroups(int UserID);

    public abstract String createChatGroup(String groupName);

    public abstract void addUserToChatGroup();

    public abstract void removeUserFromChatGroup();

}
