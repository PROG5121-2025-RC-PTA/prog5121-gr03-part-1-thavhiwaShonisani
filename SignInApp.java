

package com.mycompany.signinapp;

import javax.swing.JOptionPane;


public class SignInApp {

    public static void main(String[] args) {
    // Ask user for their first and last name
    String name = JOptionPane.showInputDialog(null,"Please enter first name");
    String surname = JOptionPane.showInputDialog(null,"Please enter your last name");
    //Call the method checkUsername();
    boolean isUsernameValid = checkUsername();
    if (!isUsernameValid) {
    return; //cancell the code when input is false
        }
        //Call the method checkPassword();
        boolean isPasswordValid = checkPassword();
        if (!isPasswordValid) {
            
            return; //cancell the code when input is false
        }

        //Call the method validateSouthAfricanPhoneNumber();
        boolean isPhoneValid = validateSouthAfricanPhoneNumber();
        if (!isPhoneValid) {
            
            return; //cancell the code when input is false
        }
        //Call the loginUser method
        loginUser();
        //Store the result of loginUser()
        boolean login = loginUser(); 
        //Call the loginStatus method    
        String loginMessage = loginStatus(login, name, surname);
        JOptionPane.showMessageDialog(null, loginMessage);

    }
    //Method to register the user
     public static String registerUser(String username,String password){
    //Loop to check if the user input is empty
    if (username == null|| username.isEmpty() ){
    JOptionPane.showMessageDialog(null,"Please enter username ");
           
    }
    //Loop to check if the user input is empty
    if(password ==null|| password.isEmpty()){
    JOptionPane.showMessageDialog(null,"Please enter password");
            }
        
         return null;  //Cancell the code if input is empty
     }
     //Method to display the necessary login message
     public static String loginStatus(boolean isLoggedIn, String name, String surname){
         if(isLoggedIn){
             return "Welcome " + name +" "+ surname + " it is great to see you again.";
         }else{
             return "Username or password is incorrect,please try again";
             }
 }
    // Method to check the username
    public static boolean checkUsername() {
        String username = JOptionPane.showInputDialog(null, "Enter username:");
        if (username == null) {
            return false; //cancell the code when input is false
        }
        //check if the username has less or equals to 5 characters and contains an underscore
        boolean isLengthValid = username.length() <= 5;
        boolean hasUnderscore = username.contains("_");
        //displays messeges when the conditions are met
        if (!isLengthValid || !hasUnderscore) {
            JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Please ensure that your username contains an underscore and is not more than five characters in length.");
            return false; //cancell the code when input is false
        } else {
            JOptionPane.showMessageDialog(null, "Username successfully captured");
            return true; //cancell the code when input is true
        }
    }

    // Method to check password
    public static boolean checkPassword() {
        String password = JOptionPane.showInputDialog(null, "Enter password:");
        if (password == null) {
            return false; //cancell the code when input is false
        }
        //Code refferd from chatGPT
        boolean hasSpecialChar = password.matches(".*[!@$%^&*()_\\-+=<>?/{}#~].*");
        boolean hasCapitalLetter = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean isLengthValid = password.length() >= 8;
        //displays messeges when the conditions are met
        if (!isLengthValid || !hasSpecialChar || !hasCapitalLetter || !hasNumber) {
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted. Please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false; //cancell the code when input is false
        } else {
            JOptionPane.showMessageDialog(null, "Password successfully captured");
            return true; //cancell the code when input is true
        }
    }

    // Method to check for the South African phone number
    public static boolean validateSouthAfricanPhoneNumber() {
        String phoneNumber = JOptionPane.showInputDialog(null, "Enter South African Phone Number:");
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone number cannot be empty.");
            return false;
        }
        //check if the phone number starts with +27
        if (!phoneNumber.startsWith("+27")) {
            JOptionPane.showMessageDialog(null, "Phone number must start with '+27'.");
            return false; //cancell the code when input is false
        }
        //After the +27, the method will check that there are exactly 9 digits 
        String digits = phoneNumber.substring(3); //The substring(3) extracts everything after the first three characters of +27, leaving only the digits.
        if (digits.length() != 9) {
            JOptionPane.showMessageDialog(null, "Phone number must have exactly 9 digits after '+27'.");
            return false; //cancell the code when input is false
        }
        for (int i = 0; i < digits.length(); i++) {
            if (!Character.isDigit(digits.charAt(i))) {
                JOptionPane.showMessageDialog(null, "Phone number must contain only digits after '+27'.");
                return false; //cancell the code when input is false
            }
        }
        //shows a message when conditions are met
        JOptionPane.showMessageDialog(null, "Phone number successfully added");
        return true; //cancell the code when input is true
       
    }

   
    
    private static String storedUsername ="";
    private static String storedPassword ="";
    
    public static boolean loginUser(){
        String inputUsername = storedUsername;
        String inputPassword = storedPassword;
        
        if (inputUsername== null|| inputPassword==null){
            JOptionPane.showMessageDialog(null,"Login cancelled");
            return false;
        }
        if (inputUsername.equals(storedUsername)&& inputPassword.equals(storedPassword)){
            JOptionPane.showMessageDialog(null,"Login successful");
            return true;
        }else{
            JOptionPane.showMessageDialog(null,"Invalid username or password.");
            return false;
        }
        
    }
 }