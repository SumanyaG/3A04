package com.example.securechat;

public class ChatMessage {
    private String senderId;
    private String messageText;
    private long timestamp;
    private long expirationTimestamp;
    private boolean isEphemeral; //basically means if it's ephemeral it will disappear eventually (not permanent)

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String messageText, long timestamp, long expirationTimestamp, boolean isEphemeral) {
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.expirationTimestamp = expirationTimestamp;
        this.isEphemeral = isEphemeral;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(long expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public boolean getIsEphemeral() {
        return isEphemeral;
    }

    public void setIsEphemeral(boolean isEphemeral) {
        this.isEphemeral = isEphemeral;
    }
}
