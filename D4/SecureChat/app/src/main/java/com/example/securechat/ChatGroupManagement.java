package com.example.securechat;

import java.util.Set;

abstract class ChatGroupManagement {


    public abstract void getChatGroup(String chatGroupID);

    public abstract void displayPage();

    public abstract void addUserToChatGroup(String UserId);

    public abstract void removeUserFromChatGroup();

    public abstract boolean deleteChatGroup();

    public abstract void retrieveAllMessages();

    public abstract void retrieveNewMessages();


    public abstract void sendMessage(String senderId, String messageText, boolean isEphemeral);

    public abstract void sendCommunicationKeys();

    public abstract void retrieveAllMembers(String groupName, ChatGroupManager.MembersRetrievedCallback callback);

    public abstract void retrieveMembersNotInGroup(String groupName, ChatGroupManager.MembersRetrievedCallback callback);
}
