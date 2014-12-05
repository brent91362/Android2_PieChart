package com.example.balanceteampie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class Database {
		/*
		 * creates a pie for a user
		 * fill in all parameters piename to find pie,
		 * pievalue are the values of the chart the user filled out
		 * DON'T FORGET the piename
		 */
		public void CreatePie(String username, String piename, int pievalue, int order){
			if(Integer.toString(pievalue).length()==8){
				try{
					URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createpie&user="
							+ username+"&pie_name="+piename+"&pie_value="
							+pievalue+"&pie_order="+order);
				    URLConnection conn = url.openConnection();
				    conn.setDoOutput(true);
				    conn.getInputStream();
				    }
				catch(Exception e){}
			}
			else{
				//show error
			}
		}
		/*
		 * gets the values from piename and user
		 * return as int[]
		 */

		public int[] getPieValues(String username, String piename){
			String value = null;
		    int[] pievals=new int[8];
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="+username);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line, holder = null;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=line;
			      System.out.println(line);
			    }
			    //parses the string to get the values as a string
			    //if there is more the one pie with same piename
			    //will return the first one's values
			    StringTokenizer st= new StringTokenizer(holder,"}");
			    boolean found=false;
			    while(st.hasMoreTokens()&&!found){
			    	String tempp=st.nextToken();
			    	StringTokenizer st2= new StringTokenizer(tempp,"\":");
			    	while(st2.hasMoreTokens()){
			    		String temp = st2.nextToken();
			    		if(temp.equals("pie_chart_id")){
			    			String temp2 = st2.nextToken();
			    			if(temp2.equals(piename)){
			    				found=true;
			    			}
			    		}
			    		if(temp.equals("pc_value")){
			    			String temp2 = st2.nextToken();
			    			value=temp2;
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch(Exception e){}
			//converts to int[]
			if(!value.equals(null)){
				for(int i =0; i<pievals.length-1;i++){
					pievals[i]=value.charAt(i)-'0';
				}
			}
			return pievals;
		}
		/*
		 * creates a new user
		 * Needs username and password, Team is important as well
		 * other values are not needed
		 */
		public void createUser(String username, String password, String first, String last, String email,int Team){
			if(!username.equals(null)&&!password.equals(null)){
				try{
					URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createuser"
							+ "&user="+username+"&pword="+password+"&fname="+first+"&lname="+last
							+"&email="+email+"&team_id="+Team);
					URLConnection conn = url.openConnection();
					conn.getInputStream();
				}
				catch(Exception e){
					
				}
			}
		}
		/*
		 * DOESNT WORK, server group example :P
		 */
		public String setUserInfo(String userID, String values){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuser&user="+userID);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    
			    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			    writer.write("value="+values);
			    writer.flush();
			    writer.close();
			}
		    catch(Exception e){}
		    return null;
		}
		/*
		 * gets user info as a string
		 * not parsed
		 */
		public String getUser(String userID){
			String st=null;
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuser&user="+userID);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      st=line;
			    }
			    reader.close();
			}
		    catch(Exception e){}
		    return st;
		}
		/*
		 * parsed version of ge user to retrieve password
		 */
		public String getUserPassword(String userID){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuser&user="+userID);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line,holder = null;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=(line);
			    }
			    reader.close();
			    StringTokenizer st= new StringTokenizer(holder,"}\":");
			    boolean found=false;
			    while(st.hasMoreTokens()&&!found){
			    	String temp=st.nextToken();
			    		if(temp.equals("ua_password")){
			    			return st.nextToken();
			    	}
			    	
			    }
			}
		    catch(Exception e){}
		    return null;
		}
		/*
		 * again need to talk with server group
		 */
		public String[] getTeam(int i){
			String[] names=null;
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getteam&team_id="+i);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
				String line;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      System.out.println(line);
			    }
			    reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return names;
		}
		/*
		 * Trying to get to retreive all
		 */
		public String[] getTeamPie(int TeamID){
			String[] names=null;
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getteampie&team_id=team_id"+TeamID);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      System.out.println(line);
			    }
			    reader.close();
			}
		    catch(Exception e){}
		    return names;
		}
		/*
		 * Updates Not Working
		 */
		public void updateteam(String teamLeader, String teamName){
//			try {
//				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=updateteam&team_id=team_id[&team_leader="+teamLeader+"&team_name=]"+teamName);
//			} 
//			catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
		public void updateUser(String userID, String password, String first, String last, String Team,int teamId){
			try{
//				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=updateuser"
//						+ "&user="+userID+"[&pword="+password+"&team_id="+teamId+"&fname="+first+"&lname="+last
//						+"&email="+Team+"]");
//				URLConnection conn = url.openConnection();
//			    conn.setDoOutput(true);
//			    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
//			    writer.write("&fname=newRamiro");
//			    writer.flush();
//			    String line;
//			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			    while ((line = reader.readLine()) != null) {
//			      System.out.println(line);
//			    }
//			    System.out.println("line");
//			    writer.close();
//			    reader.close();
			    }
			catch(Exception e){
				
			}
		}
}
