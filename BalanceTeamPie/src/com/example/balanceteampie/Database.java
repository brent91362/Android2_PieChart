package com.example.balanceteampie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
		public void CreatePie(String username, String piename, String[] pieattributes, int pievalue, int order){
			String att=piename+"~";
			for(String s: pieattributes){
				att+=s;
				att+=",";
			}
			if(Integer.toString(pievalue).length()==8){
				try{
					URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createpie&user="
							+ username+"&pie_name="+att+"&pie_value="
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

		public String[] getPieValues(String username, String piename){
			String value = null;
		    String[] pievals=new String[8];
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="+username);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line, holder = null;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=line;
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
			    		if(temp.contains("pc_name")){
			    			String temp2 = st2.nextToken();
			    			if(temp2.equals(piename)){
			    				found=true;
			    				value=st2.nextToken();
			    				StringTokenizer st3= new StringTokenizer(value,"~");
						    	st3.nextToken();
						    	value=st3.nextToken();
						    	StringTokenizer st4= new StringTokenizer(value,", ");
						    	int i=0;
						    	while(st4.hasMoreTokens()){
						    		pievals[i]=st4.nextToken();
						    		i++;
						    	}
						    	return pievals;
			    			}
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch(Exception e){
				
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
		
		public void updatePassword(String userID, String password){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action"
						+ "=updateuser&user="+userID+"&pword="+password);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    conn.getInputStream();
			    }
			catch(Exception e){
				
			}
		}
		/*
		 * change team leader
		 */
		public void updateTeamLeader(String userId, int newTeamLeader){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=updateteam&team_id="
						+userId+"&team_leader="+newTeamLeader);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    conn.getInputStream();
			    }
			catch(Exception e){
				
			}
		}
		/*
		 * changes pi values
		 */
		public void updateuserPie(String pieId, int[] values) throws IOException{
			try {
				URL url= new URL("http://btpie.ddns.net/dbfuncts.php?action=updateuserpie&pie_id="
						+ pieId+"&pie_value="+values);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    conn.getInputStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/*
		 * returns pie attributes in same order as pie values order
		 * ie the placement of the attributes in the return string array
		 * corresponds to the int array that pievalues method returns
		 */
		public String[] getPieAttributes(String username, String piename){
			String value = null;
		    String[] pievals=new String[8];
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="+username);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line, holder = null;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=line;
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
			    		if(temp.equals("pc_name")){
			    			String temp2 = st2.nextToken();
			    			if(temp2.contains(piename)){
			    				found=true;
			    				StringTokenizer st3= new StringTokenizer(temp2,"~");
						    	st3.nextToken();
						    	value=st3.nextToken();
						    	StringTokenizer st4= new StringTokenizer(value,", ");
						    	int i=0;
						    	while(st4.hasMoreTokens()){
						    		pievals[i]=st4.nextToken();
						    		i++;
						    	}
						    	return pievals;
			    			}
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch(Exception e){
				
			}
			return pievals;
		}
		//gets all team info: leader, ids, etc
		public int getTeamName(String teamName){
			int name=0;
			String toparse=getAllTeams();
			StringTokenizer st = new StringTokenizer(toparse,"{");
			while(st.hasMoreTokens()){
				StringTokenizer st2 = new StringTokenizer(st.nextToken(),",");
				while(st2.hasMoreTokens()){
					String temp = st2.nextToken();
					if(temp.contains("team_id")){
						StringTokenizer st3 = new StringTokenizer(temp,"\":");
						st3.nextToken();
						name= Integer.parseInt(st3.nextToken());
					}
					if(temp.contains(teamName)){
						return name;
					}
				}
			}
			return name;
		}

		public String getAllTeams(){
			String output=null;
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getteamlist");
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
				String line;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      output=line;
			    }
			    reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return output;
		}
}
