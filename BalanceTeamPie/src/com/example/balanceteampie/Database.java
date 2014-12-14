package com.example.balanceteampie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.StrictMode;
import android.util.Log;

public class Database {
	/*
	 * creates a pie for a user fill in all parameters piename to find pie,
	 * pievalue are the values of the chart the user filled out DON'T FORGET the
	 * piename
	 */
	public void CreatePie(String username, String piename,
			String[] pieattributes, int pievalue, int order) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		String att = piename + "~";
		for (String s : pieattributes) {
			att += s;
			att += ",";
		}
		if (Integer.toString(pievalue).length() == 8
		      || Integer.toString(pievalue).length() == 6) {
			try {
				URL url = new URL(
						"http://btpie.ddns.net/dbfuncts.php?action=createpie&user="
								+ username + "&pie_name=" + att + "&pie_value="
								+ pievalue + "&pie_order=" + order);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				conn.getInputStream();
			} catch (Exception e) {
			}
		} else {
			Log.e("ERROR", "cannot create pie");
		}
	}

	/*
	 * creates a new user Needs username and password, Team is important as well
	 * other values are not needed
	 */
	public void createUser(String username, String password, String first,
			String last, String email, int Team) {
		if (!username.equals(null) && !password.equals(null)) {
			try {
				URL url = new URL(
						"http://btpie.ddns.net/dbfuncts.php?action=createuser"
								+ "&user=" + username + "&pword="
								+ MD5(password) + "&fname=" + first + "&lname="
								+ last + "&email=" + email + "&team_id=" + Team);
				URLConnection conn = url.openConnection();
				conn.getInputStream();
			} catch (Exception e) {
			}
		}
	}

	public void createUser(String username, String password, String first,
			String last, String email) {
		if (!username.equals(null) && !password.equals(null)) {
			try {
				URL url = new URL(
						"http://btpie.ddns.net/dbfuncts.php?action=createuser"
								+ "&user=" + username + "&pword="
								+ MD5(password) + "&fname=" + first + "&lname="
								+ last + "&email=" + email);
				URLConnection conn = url.openConnection();
				conn.getInputStream();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * gets user info as a string not parsed
	 */
	public String getUser(String userID) {
		String st = null;
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getuser&user="
							+ userID);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				st = line;
			}
			reader.close();
		} catch (Exception e) {
		}
		return st;
	}

	/*
	 * parsed version of ge user to retrieve password
	 */
	public String getUserPassword(String userID) {
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
				if (temp.equals("ua_password")) {
					return st.nextToken();
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	/*
	 * Trying to get to retreive all
	 */
	public ArrayList<int[]> getTeamPieValues(int i) {
		ArrayList<int[]> names = new ArrayList<int[]>();
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getteampie&team_id="
							+ i);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line, holder = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				holder = line;
				System.out.println(holder);
			}
			// parses the string to get the names in team
			StringTokenizer st = new StringTokenizer(holder, "}");
			boolean found = false;
			while (st.hasMoreTokens() && !found) {
				String tempp = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(tempp, "{,\":");
				while (st2.hasMoreTokens()) {
					if (st2.nextToken().equals("pc_value")) {
						int[] temp = toIntArray(st2.nextToken());
						names.add(temp);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}

	/*
	 * Change user's team, thus that the team pie
	 */
	public void changeTeam(String userID, int teamId) {
		try {
			URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action"
					+ "=updateuser&user=" + userID + "&team_id=" + teamId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try {
			   conn.setDoOutput(true);
			   conn.getInputStream();
			} finally {
			   conn.disconnect();
			}
		} catch (Exception e) {
		   Log.e("ERROR", "Cannot change team");
		}
	}

	public void updatePassword(String userID, String password) {
		try {
			URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action"
					+ "=updateuser&user=" + userID + "&pword=" + MD5(password));
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.getInputStream();
		} catch (Exception e) {
		}
	}

	/*
	 * change team leader
	 */
	public void updateTeamLeader(String userId, String newTeamLeader) {
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=updateteam&team_id="
							+ userId + "&team_leader=" + newTeamLeader);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.getInputStream();
		} catch (Exception e) {
		}
	}

	/*
	 * changes pi values
	 */
	public void updateuserPie(String pieId, int[] values) throws IOException {
		try {
		   
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=updateuserpie&pie_id="
							+ pieId + "&pie_value=" + values);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.getInputStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add by MinL
	 * To update the values as a string of levels
	 * @param pieId
	 * @param values
	 * @throws IOException
	 */
	public void updateuserPie(String pieId, String values) throws IOException {
	   try {

	      URL url = new URL(
	            "http://btpie.ddns.net/dbfuncts.php?action=updateuserpie&pie_id="
	                  + pieId + "&pie_value=" + values);
	      URLConnection conn = url.openConnection();
	      conn.setDoOutput(true);
	      conn.getInputStream();
	   } catch (MalformedURLException e) {
	      e.printStackTrace();
	   }
	}

	/*
	 * returns pie attributes in same order as pie values order ie the placement
	 * of the attributes in the return string array corresponds to the int array
	 * that pievalues method returns
	 */
	public String[] getPieAttributes(String username, String piename) {
		String value = null;
		String[] pieAttributes = new String[8];
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="
							+ username);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line, holder = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				holder = line;
			}
			// parses the string to get the values as a string
			// if there is more the one pie with same piename
			// will return the first one's values
			StringTokenizer st = new StringTokenizer(holder, "}");
			boolean found = false;
			while (st.hasMoreTokens() && !found) {
				String tempp = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(tempp, "\":");
				while (st2.hasMoreTokens()) {
					String temp = st2.nextToken();
					if (temp.equals("pc_name")) {
						String temp2 = st2.nextToken();
						if (temp2.contains(piename)) {
							found = true;
							StringTokenizer st3 = new StringTokenizer(temp2,
									"~");
							st3.nextToken();
							value = st3.nextToken();
							StringTokenizer st4 = new StringTokenizer(value,
									", ");
							int i = 0;
							while (st4.hasMoreTokens()) {
								pieAttributes[i] = st4.nextToken();
								i++;
							}
							return pieAttributes;
						}
					}
				}
			}
			reader.close();
		} catch (Exception e) {
		}
		return pieAttributes;
	}

	// gets all team info: leader, ids, etc
	public int getTeamName(String teamName) {
		int name = 0;
		String toparse = getAllTeams();
		StringTokenizer st = new StringTokenizer(toparse, "{");
		while (st.hasMoreTokens()) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(), ",");
			while (st2.hasMoreTokens()) {
				String temp = st2.nextToken();
				if (temp.contains("team_id")) {
					StringTokenizer st3 = new StringTokenizer(temp, "\":");
					st3.nextToken();
					name = Integer.parseInt(st3.nextToken());
				}
				if (temp.contains(teamName)) {
					return name;
				}
			}
		}
		return name;
	}

	/*
	 * gets all users in team
	 */
	public ArrayList<String> getTeam(int i) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getteam&team_id="
							+ i);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line, holder = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				holder = line;
				System.out.println(holder);
			}
			// parses the string to get the names in team
			StringTokenizer st = new StringTokenizer(holder, "}");
			boolean found = false;
			while (st.hasMoreTokens() && !found) {
				String tempp = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(tempp, "{,\":");
				while (st2.hasMoreTokens()) {
					if (st2.nextToken().equals("ua_username")) {
						names.add(st2.nextToken());
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}

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
	/*
	 * gets all teams in database
	 */
	public int[] getTeamPieValues(String username, String piename) {
		String value = null;
		int[] pievals = new int[8];
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="
							+ username);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line, holder = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				holder = line;
				System.out.println(holder);
			}
			// parses the string to get the values as a string
			// if there is more the one pie with same piename
			// will return the first one's values
			StringTokenizer st = new StringTokenizer(holder, "}");
			boolean found = false;
			while (st.hasMoreTokens() && !found) {
				String tempp = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(tempp, "\":");
				while (st2.hasMoreTokens()) {
					String temp = st2.nextToken();
					if (temp.equals("pie_chart_id")) {
						String temp2 = st2.nextToken();
						if (temp2.equals(piename)) {
							found = true;
						}
					}
					if (temp.equals("pc_value")) {
						String temp2 = st2.nextToken();
						value = temp2;
						return toIntArray(value);
					}
				}
			}
			reader.close();
		} catch (Exception e) {
		}
		return pievals;
	}

	public String getAllTeams() {
		String output = null;
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getteamlist");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				output = line;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/*
	 * Convert a string to int[] based on position of number
	 * Modified by MinL 12132014
	 */
	public int[] toIntArray(String value) {
	   int[] pievals = null;
      if (!value.equals(null)) {
         pievals = new int[value.length()];
         for (int i = 0; i < pievals.length; i++) {
            pievals[i] = value.charAt(i) - '0';
         }
      }
      return pievals;
	}

	/*
	 * encrypts using MD5 per server team request
	 */
	public static String MD5(String pass) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (Exception ex) {
		}
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
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try {
   			conn.setDoOutput(true);
   			conn.setDoInput(true);
   			
   			String line, holder = null;
   			
   			BufferedReader reader = new BufferedReader(new InputStreamReader(
   					conn.getInputStream()));
   			
   			while ((line = reader.readLine()) != null)
   				holder = (line);

   			reader.close();

   			StringTokenizer st = new StringTokenizer(holder, "}\":");
   			boolean found = false;
   			
   			while (st.hasMoreTokens() && !found) {
   				String temp = st.nextToken();
   				if (temp.equals("team_id")) {
   				   String tID = st.nextToken();
   				   if (tID.contains("null"))
   				      return null;
   					return tID;
   				}
   			}
			}
			finally {
			   conn.disconnect();
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Get the leader's team id
	 * @param userID: user_account_id number
	 * @param teamName: team_name (or project name)
	 * @return a string of teamID number
	 */
	public String getLeaderTeamID(String userID, String teamName) {
      try {
         URL url = new URL(
               "http://btpie.ddns.net/dbfuncts.php?action=getteamlist");
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         try {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            String line, holder = null;
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                  conn.getInputStream()));
            
            while ((line = reader.readLine()) != null)
               holder = (line);

            reader.close();

            StringTokenizer st = new StringTokenizer(holder, "}");
            boolean found = false;
            String teamId = "";
            
            while (st.hasMoreTokens() && !found) {
               String tempp = st.nextToken();
               StringTokenizer st2 = new StringTokenizer(tempp, "\":,");
               while (st2.hasMoreTokens()) {
                  String temp = st2.nextToken();
                  if (temp.equals("team_id")) {
                     String temp2 = st2.nextToken();
                     teamId = temp2;                   
                  }
                  if (temp.equals("team_leader_id")) {
                     String temp2 = st2.nextToken();
                     if (temp2.equals(userID)) {
                        temp = st2.nextToken();
                        if (temp.equals("team_name")) {
                           String temp3 = st2.nextToken();
                           if (temp3.equals(teamName)) {
                              return teamId;
                           }                     
                        }
                     }                     
                  }                  
               }
            }
         }
         finally {
            conn.disconnect();
         }
      } catch (Exception e) {
      }
      return null;
   }
	
	 /**
    * Get user's account id number from username
    * @param username
    * @return a string of account_id number
    */
	public String getAccountId(String username) {
	   String id_number = null;
	   try {
	      URL url = new URL(
	            "http://btpie.ddns.net/dbfuncts.php?action=getuser&user="
	                  + username);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      try {
	         conn.setDoOutput(true);
	         conn.setDoInput(true);

	         String line, holder = null;

	         BufferedReader reader = new BufferedReader(new InputStreamReader(
	               conn.getInputStream()));

	         while ((line = reader.readLine()) != null)
	            holder = (line);

	         reader.close();

	         StringTokenizer st = new StringTokenizer(holder, "}\":");
	         boolean found = false;

	         while (st.hasMoreTokens() && !found) {
	            String temp = st.nextToken();
	            if (temp.equals("user_account_id"))
	               return st.nextToken();
	         }
	      }
	      finally {
	         conn.disconnect();
	      }
	   } catch (Exception e) {
	      Log.e("ERROR", "Account id not found.");
	   }
	   return id_number;
	}

	/*
	 * returns get's the users pie name as string
	 */
	public String getUserPieName(String username) {
		String name = null;
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="
							+ username);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				name = line;
			}
			reader.close();
			StringTokenizer st = new StringTokenizer(name, "[]{}\",:");
			while (st.hasMoreTokens()) {
				if (st.nextToken().contains("pc_name")) {
					return st.nextToken();
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	
	/*
	 * returns get's the users pie id as string
	 */
	public String getUserPieId(String username) {
		String name = null;
		try {
			URL url = new URL(
					"http://btpie.ddns.net/dbfuncts.php?action=getuserpie&user="
							+ username);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				name = line;
			}
			reader.close();
			StringTokenizer st = new StringTokenizer(name, "[]{}\",:");
			while (st.hasMoreTokens()) {
				if (st.nextToken().contains("pie_chart_id")) {
					return st.nextToken();
				}
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	
	public void createTeam(String username, String TeamName) {
		try {
			URL url = new URL("http://btpie.ddns.net/dbfuncts.php?action=createteam"
					+ "&team_leader=" + username + "&team_name=" + TeamName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try {
   			conn.setDoOutput(true);
   			conn.getInputStream();
			} finally {
			   conn.disconnect();
			}
		} catch (Exception e) {
		   Log.e("ERROR", "cannot create team");
		}
	}
}