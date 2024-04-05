package com.example.securechat;

abstract class ChatGroupManagement {
    public abstract void getChatGroup(int chatGroupID);

    public abstract void displayPage();

    public abstract void addUserToChatGroup();

    public abstract void removeUserFromChatGroup();

    public abstract void retrieveAllMessages();

    public abstract void retrieveNewMessages();

    public abstract void sendMessage();

    public abstract void sendCommunicationKeys();
}
