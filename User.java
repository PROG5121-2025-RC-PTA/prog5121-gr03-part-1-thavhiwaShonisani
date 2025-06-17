/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author RC_Student_lab
 */
class User {
 private String username;
    private String phoneNumber;
    private String password;
    private Message[] sentMessages;
    private int sentCount;
    private static final int MAX_MESSAGES = 500;

    // Create new user account
    public User(String username, String phoneNumber, String password) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.sentMessages = new Message[MAX_MESSAGES];
        this.sentCount = 0;
    }

    // Add message to user's sent messages
    public void addSentMessage(Message msg) {
        if (sentCount < MAX_MESSAGES) {
            sentMessages[sentCount++] = msg;
        }
    }

    // Check if password meets requirements
    public boolean isPasswordValid() {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    // Getter methods
    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public Message[] getSentMessages() { 
        Message[] result = new Message[sentCount];
        System.arraycopy(sentMessages, 0, result, 0, sentCount);
        return result;
    }
    public int getSentCount() { return sentCount; }
}