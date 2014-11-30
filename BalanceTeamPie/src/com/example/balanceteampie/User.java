package com.example.balanceteampie;

public class User {
   private String username;
   private String pword;
   private String fName;
   private String lName;
   private String email;
   private int teamId;
   
   User (String u, String p, String f, String l, String e) {
      username = u;
      pword = p;
      fName = f;
      lName = l;
      email = e;
   }
   
   public String getUsername() { return username; }
   public String getPassword() { return pword; }
   public String getFirstName() { return fName; }
   public String getLastName() { return lName; }
   public String getEmail() { return email; }
   public boolean setPassword(String oldPw, String newPw) {
       if (pword.equals(oldPw)) {
          pword = newPw;
          return true;
       }
       return false;
   }
   public void setFirstName(String f) { fName = f; }
   public void setLastName(String l) { lName = l; }
   public void setTeamId(int id) { teamId = id; }
   public void setEmail(String e) { email = e; }
}