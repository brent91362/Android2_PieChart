package com.example.balanceteampie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
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
		 * creates a new user
		 * Needs username and password, Team is important as well
		 * other values are not needed
		 */
		public void createUser(String username, String password, String first, String last, String email,int Team){
			if(!username.equals(null)&&!password.equals(null)){
				try{
					URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createuser"
							+ "&user="+username+"&pword="+MD5(password)+"&fname="+first+"&lname="+last
							+"&email="+email+"&team_id="+Team);
					URLConnection conn = url.openConnection();
					conn.getInputStream();
				}
				catch(Exception e){
					
				}
			}
		}
		public void createUser(String username, String password, String first, String last, String email){
			if(!username.equals(null)&&!password.equals(null)){
				try{
					URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createuser"
							+ "&user="+username+"&pword="+MD5(password)+"&fname="+first+"&lname="+last
							+"&email="+email);
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
		public ArrayList<int[]> getTeamPieValues(int i){
			ArrayList<int[]> names=new ArrayList<int[]>();
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getteampie&team_id="+i);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
				String line, holder = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=line;System.out.println(holder);
			    }
			    //parses the string to get the names in team
			    StringTokenizer st= new StringTokenizer(holder,"}");
			    boolean found=false;
			    while(st.hasMoreTokens()&&!found){
			    	String tempp=st.nextToken();
			    	StringTokenizer st2= new StringTokenizer(tempp,"{,\":");
			    	while(st2.hasMoreTokens()){
			    		if(st2.nextToken().equals("pc_value")){
			    			int [] temp = toIntArray(st2.nextToken());
			    			names.add(temp);
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch (IOException e) {
				e.printStackTrace();
			}
		    return names;
		}
		/*
		 * Change user's team, thus that the team pie
		 */
		public void changeTeam(String userID, int teamId){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action"
						+ "=updateuser&user="+userID+"&team_id="+teamId);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    conn.getInputStream();
			    }
			catch(Exception e){
				
			}
		}
		public void updatePassword(String userID, String password){
			try{
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action"
						+ "=updateuser&user="+userID+"&pword="+MD5(password));
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
		    String[] pieAttributes=new String[8];
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
						    		pieAttributes[i]=st4.nextToken();
						    		i++;
						    	}
						    	return pieAttributes;
			    			}
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch(Exception e){
				
			}
			return pieAttributes;
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
		/*
		 * gets all users in team
		 */
		public ArrayList<String> getTeam(int i){
			ArrayList<String> names=new ArrayList<String>();
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getteam&team_id="+i);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
				String line, holder = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      holder=line;System.out.println(holder);
			    }
			    //parses the string to get the names in team
			    StringTokenizer st= new StringTokenizer(holder,"}");
			    boolean found=false;
			    while(st.hasMoreTokens()&&!found){
			    	String tempp=st.nextToken();
			    	StringTokenizer st2= new StringTokenizer(tempp,"{,\":");
			    	while(st2.hasMoreTokens()){
			    		if(st2.nextToken().equals("ua_username")){
			    			names.add(st2.nextToken());
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch (IOException e) {
				e.printStackTrace();
			}
		    return names;
		}
		/*
		 * gets all teams in database
		 */
		public int[] getTeamPieValues(String username, String piename){
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
			      System.out.println(holder);
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
			    			return toIntArray(value);
			    		}
			    	}
			    	
			    }
			    reader.close();
			    }
			catch(Exception e){
				
			}
			return pievals;
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
		/*
		 * Convert a string to int[]
		 * based on position of number
		 */
		public int[] toIntArray(String value){
			int[] pievals=new int[8];
			if(!value.equals(null)){
				for(int i =0; i<pievals.length-1;i++){
					pievals[i]=value.charAt(i)-'0';
				}
			}
			return pievals;
		}
		/*
		 * encrypts using MD5 per server team request
		 */
		public static String MD5(String pass){
		    try{
		    	MessageDigest md;
		        md = MessageDigest.getInstance("MD5");
		        byte[] passBytes = pass.getBytes();
		        md.reset();
		        byte[] digested = md.digest(passBytes);
		        StringBuffer sb = new StringBuffer();
		        for(int i=0;i<digested.length;i++){
		            sb.append(Integer.toHexString(0xff & digested[i]));
		        }
		        return sb.toString();
		    }
		    catch (Exception ex) {}
		    return null;
		  }
		/*
		 * gets user team id
		 */
		public String getUserTeamID(String userID) {
			try {
				URL url = new URL(
						"http://btpie.ddns.net/dbfuncts.php?action=getuser&user="
								+ userID);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				String line, holder = null;
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					holder = (line);
				}
				reader.close();
				StringTokenizer st = new StringTokenizer(holder, "}\":");
				boolean found = false;
				while (st.hasMoreTokens() && !found) {
					String temp = st.nextToken();
					if (temp.equals("team_id")) {
						return st.nextToken();
					}
				}
			} catch (Exception e) {
			}
			return null;
		}
		/*
		 * returns get's the users pie name as string
		 */
		public String getUserPieName(String username){
			String name=null;
			try {
				URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="+username);
				URLConnection conn = url.openConnection();
			    conn.setDoOutput(true);
			    String line;
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			      name=line;
			    }
			    reader.close();
			    StringTokenizer st= new StringTokenizer(name,"[]{}\",:");
			    while(st.hasMoreTokens()){
			    	if(st.nextToken().contains("pc_name")){
			    		return st.nextToken();
			    	}
			    }
			}
		    catch(Exception e){}
		    return null;
		}
}
