package com.example.securechat;

abstract class ChatGroupManagement {


    public abstract void getChatGroup(String chatGroupID);

    public abstract void displayPage();

    public abstract void addUserToChatGroup(String UserId);

    public abstract void removeUserFromChatGroup();

    public abstract void retrieveAllMessages();

    public abstract void retrieveNewMessages();

    public abstract void sendMessage();

    public abstract void sendCommunicationKeys();
}
