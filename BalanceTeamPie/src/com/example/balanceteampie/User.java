package com.example.balanceteampie;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
   private String username;
   private String pword;
   private String fName;
   private String lName;
   private String email;
   private int teamId;
   private ArrayList<String> skillNames;
   private ArrayList<Integer> skillLevels;
   
   public static final Parcelable.Creator<User> CREATOR
         = new Parcelable.Creator<User>() {

      @Override
      public User createFromParcel(Parcel in) {
         return new User(in);
      }

      @Override
      public User[] newArray(int size) {
         return new User[size];
      }
   };
   
   public User (Parcel in) {
      username = in.readString();
      pword = in.readString();
      fName = in.readString();
      lName = in.readString();
      email = in.readString();
      teamId = in.readInt();
      skillNames = in.createStringArrayList();
      in.readList(skillLevels, null);
   }
   
   public User (String u, String p, String f, String l, String e) {
      username = u;
      pword = p;
      fName = f;
      lName = l;
      email = e;
      skillNames = new ArrayList<String>();
      skillLevels = new ArrayList<Integer>();
   }
   
   public String getUsername() { return username; }
   public String getPassword() { return pword; }
   public String getFirstName() { return fName; }
   public String getLastName() { return lName; }
   public String getEmail() { return email; }
   public int getTeamId() { return teamId; }
   public String getSkillName(int section) {
      return skillNames.get(section);
   }
   public int getSkillLevel(int section) {
      return skillLevels.get(section);
   }
   
   public boolean setPassword(String oldPw, String newPw) {
       if (pword.equals(oldPw)) {
          pword = newPw;
          return true;
       }
       return false;
   }
   public void setUsername(String u) { username = u; }
   public void setFirstName(String f) { fName = f; }
   public void setLastName(String l) { lName = l; }
   public void setTeamId(int id) { teamId = id; }
   public void setEmail(String e) { email = e; }
   public void setSkillNames(String[] sn) {
      for (int i = 0; i < sn.length; i++)
         skillNames.add(sn[i]);
   }
   public void setSkillLevels(int[] sl) {
      for (int i = 0; i < sl.length; i++)
         skillLevels.add(sl[i]);
   }

   @Override
   public int describeContents() {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(username);
      dest.writeString(pword);
      dest.writeString(fName);
      dest.writeString(lName);
      dest.writeString(email);
      dest.writeInt(teamId);
      dest.writeStringList(skillNames);
      dest.writeList(skillLevels);      
   }
}