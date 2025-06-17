/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
public class QuickChat {
 private static final int MAX_USERS = 100;
    private static final int MAX_MESSAGES = 1000;
    private static User[] users = new User[MAX_USERS];
    private static int userCount = 0;
    private static User currentUser = null;
    private static Message[] sentMessages = new Message[MAX_MESSAGES];
    private static Message[] disregardedMessages = new Message[MAX_MESSAGES];
    private static String[] messageHashes = new String[MAX_MESSAGES];
    private static String[] messageIDs = new String[MAX_MESSAGES];
    private static int sentCount = 0;
    private static int disregardedCount = 0;

    // Main program entry point
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to Quickchat");
        while (true) {
            String choice = JOptionPane.showInputDialog(
                "Main Menu:\n1. Register\n2. Login\n3. Quit");
            
            if (choice == null) continue;
            
            switch (choice) {
                case "1": registerUser(); break;
                case "2": loginUser(); if (currentUser != null) mainMenu(); break;
                case "3": System.exit(0);
                default: JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    // Register new user account
    private static void registerUser() {
        String username;
        while (true) {
            username = JOptionPane.showInputDialog("Enter username:");
            if (username == null) return;
            username = username.trim();
            
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                continue;
            }
            
            if (findUser(username) != null) {
                JOptionPane.showMessageDialog(null, "Username taken!");
                continue;
            }
            break;
        }

        String phoneNumber;
        while (true) {
            phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
            if (phoneNumber == null) return;
            phoneNumber = phoneNumber.trim();
            
            if (phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Number cannot be empty!");
                continue;
            }
            break;
        }

        String password;
        while (true) {
            password = JOptionPane.showInputDialog("Create password (8+ chars, 1 uppercase, 1 number, 1 special):");
            if (password == null) return;
            
            User tempUser = new User(username, phoneNumber, password);
            if (tempUser.isPasswordValid()) break;
            JOptionPane.showMessageDialog(null, "Invalid password format!");
        }

        if (userCount < MAX_USERS) {
            users[userCount++] = new User(username, phoneNumber, password);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(null, "System user limit reached!");
        }
    }

    // Login existing user
    private static void loginUser() {
        String username;
        while (true) {
            username = JOptionPane.showInputDialog("Enter username:");
            if (username == null) return;
            username = username.trim();
            
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                continue;
            }
            break;
        }

        User user = findUser(username);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "User not found!");
            return;
        }

        String password;
        while (true) {
            password = JOptionPane.showInputDialog("Enter password:");
            if (password == null) return;
            
            if (password.equals(user.getPassword())) {
                currentUser = user;
                JOptionPane.showMessageDialog(null, "Login successful!");
                return;
            }
            JOptionPane.showMessageDialog(null, "Wrong password!");
        }
    }

    // Find user by username
    private static User findUser(String username) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equalsIgnoreCase(username.trim())) {
                return users[i];
            }
        }
        return null;
    }

    // Main menu after login
    private static void mainMenu() {
        while (true) {
            String choice = JOptionPane.showInputDialog(
                "Welcome " + currentUser.getUsername() + 
                "!\n1. Send Message\n2. View Reports\n3. Logout");
            
            if (choice == null) continue;
            
            switch (choice) {
                case "1": sendMessage(); break;
                case "2": reportsMenu(); break;
                case "3": currentUser = null; return;
                default: JOptionPane.showMessageDialog(null, "Invalid choice");
            }
        }
    }

    // Send new message
    private static void sendMessage() {
        if (sentCount >= MAX_MESSAGES) {
            JOptionPane.showMessageDialog(null, "Message limit reached!");
            return;
        }

        // Get recipient number
        String recipientNumber;
        while (true) {
            recipientNumber = JOptionPane.showInputDialog("Enter recipient number:");
            if (recipientNumber == null) return;
            
            int check = Message.checkRecipientCell(recipientNumber);
            if (check == 0) break;
            if (check == 1) JOptionPane.showMessageDialog(null, "Number too long!");
            if (check == 2) JOptionPane.showMessageDialog(null, "Invalid number format!");
        }

        // Get message content
        String content;
        while (true) {
            content = JOptionPane.showInputDialog("Enter message (max 250 chars):");
            if (content == null) return;
            
            if (content.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Message cannot be empty!");
                continue;
            }
            if (content.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message too long!");
                continue;
            }
            break;
        }

        // Create and store message
        Message msg = new Message(recipientNumber, content, currentUser.getUsername());
        String hash = msg.sendMessage();
        
        sentMessages[sentCount] = msg;
        messageHashes[sentCount] = hash;
        messageIDs[sentCount] = msg.getMessageID();
        sentCount++;
        
        currentUser.addSentMessage(msg);
    }

    // Reports menu
    private static void reportsMenu() {
        while (true) {
            String choice = JOptionPane.showInputDialog(
                "Reports Menu:\n" +
                "1. View All Messages\n" +
                "2. View Longest Message\n" +
                "3. Search by Recipient\n" +
                "4. Search by Message ID\n" +
                "5. Back to Main Menu");
            
            if (choice == null) continue;
            
            switch (choice) {
                case "1": showAllMessages(); break;
                case "2": showLongestMessage(); break;
                case "3": searchByRecipient(); break;
                case "4": searchByMessageID(); break;
                case "5": return;
                default: JOptionPane.showMessageDialog(null, "Invalid option");
            }
        }
    }

    // Show all sent messages
    private static void showAllMessages() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages sent yet!");
            return;
        }
        
        StringBuilder sb = new StringBuilder("All Sent Messages:\n\n");
        for (int i = 0; i < sentCount; i++) {
            Message msg = sentMessages[i];
            sb.append(i+1).append(". To: ").append(msg.getRecipientCell())
              .append("\nContent: ").append(msg.getContent())
              .append("\nID: ").append(msg.getMessageID())
              .append("\nHash: ").append(messageHashes[i])
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Show longest message
    private static void showLongestMessage() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages sent yet!");
            return;
        }
        
        Message longest = sentMessages[0];
        for (int i = 1; i < sentCount; i++) {
            if (sentMessages[i].getContent().length() > longest.getContent().length()) {
                longest = sentMessages[i];
            }
        }
        
        JOptionPane.showMessageDialog(null, 
            "Longest Message (" + longest.getContent().length() + " chars):\n" +
            "To: " + longest.getRecipientCell() + "\n" +
            "Content: " + longest.getContent());
    }

    // Search messages by recipient
    private static void searchByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number:");
        if (recipient == null || recipient.trim().isEmpty()) return;
        
        StringBuilder sb = new StringBuilder("Messages to " + recipient + ":\n\n");
        boolean found = false;
        
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getRecipientCell().equals(recipient)) {
                sb.append(i+1).append(". ").append(sentMessages[i].getContent())
                  .append("\nID: ").append(sentMessages[i].getMessageID())
                  .append("\n\n");
                found = true;
            }
        }
        
        if (!found) sb.append("No messages found for this recipient");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Search message by ID
    private static void searchByMessageID() {
        String id = JOptionPane.showInputDialog("Enter message ID:");
        if (id == null || id.trim().isEmpty()) return;
        
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageID().equalsIgnoreCase(id.trim())) {
                JOptionPane.showMessageDialog(null, 
                    "Message Found:\n" +
                    "To: " + sentMessages[i].getRecipientCell() + "\n" +
                    "Content: " + sentMessages[i].getContent() + "\n" +
                    "Hash: " + messageHashes[i]);
                return;
            }
        }
        
        JOptionPane.showMessageDialog(null, "Message not found!");
    }
}