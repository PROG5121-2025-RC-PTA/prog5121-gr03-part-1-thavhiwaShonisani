/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
class Message {
 private static int totalMessages = 0;
    private String messageID;
    private String recipientCell;
    private String content;
    private String senderUsername;

    // Create new message with sender info
    public Message(String recipientCell, String content, String senderUsername) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.content = content;
        this.senderUsername = senderUsername;
    }

    // Generate unique ID for message
    private String generateMessageID() {
        Random rand = new Random();
        return "MSG" + (1000000000L + (long)(rand.nextDouble() * 9000000000L));
    }

    // Check if phone number is valid
    public static int checkRecipientCell(String number) {
        if (number == null) return 2; // Null
        if (number.length() > 13) return 1; // Too long
        if (!number.matches("^\\+?\\d+")) return 2; // Bad format
        return 0; // Valid
    }

    // Create hash from message content
    public String createMessageHash() {
        String[] words = content.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length-1] : first;
        return (messageID.substring(0,3) + ":" + first + ":" + last).toUpperCase();
    }

    // Show confirmation when message sent
    public String sendMessage() {
        String hash = createMessageHash();
        JOptionPane.showMessageDialog(null, "Sent to: " + recipientCell + "\nHash: " + hash);
        return hash;
    }

    // Getter methods
    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getContent() { return content; }
    public String getSenderUsername() { return senderUsername; }
}