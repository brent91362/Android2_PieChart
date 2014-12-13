package com.example.balanceteampie;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Manager extends Activity {
	static final int MAX_SECTION = 32;
	static final int MAX_LEVEL = 4;
	static int myColor = Color.argb(150, 255, 0, 255);
	static PieInfo pInfo[] = new PieInfo[MAX_SECTION];
	ImageView secImgBtn[] = new ImageView[MAX_SECTION];
	
	ImageButton hotspotImgBtn;
	ImageView pieImgView;
	private ListView mainListView ;
	
	TextView skillName1 = (TextView)findViewById(R.id.skill_name1);
	TextView skillName2 = (TextView)findViewById(R.id.skill_name2);
	TextView skillName3 = (TextView)findViewById(R.id.skill_name3);
	TextView skillName4 = (TextView)findViewById(R.id.skill_name4);
	TextView skillName5 = (TextView)findViewById(R.id.skill_name5);
	TextView skillName6 = (TextView)findViewById(R.id.skill_name6);
	TextView skillName7 = (TextView)findViewById(R.id.skill_name7);
	TextView skillName8 = (TextView)findViewById(R.id.skill_name8);
	
//	Intent mIntent = getIntent();
//	String userName = mIntent.getExtras().getString("teamMemName");
	
	Database db = new Database();
	Intent i = getIntent();
	//User myUser = (User) i.getParcelableExtra("USER_INFO");
	String userPartner = i.getExtras().getString("teamMemName");
	String userName = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);
		
		
		Intent mIntent = getIntent();
		User myUser = (User) mIntent.getParcelableExtra("USER_INFO");
		userName = myUser.getUsername();
		//String[] users = {"Joe", "Steve", "Bob", "Jose", "Jane"};
		
		if(userPartner == ""){
			entireTeam(myUser);
		}else{
			oneTeamMember();
		}
		
		
		
		
//		popultateListVIew(users);
		
//		setPieInfo();
//		setImgBtn();
//		int[] array0 = { 2, 0, 2, 4, 3, 0, 1, 0 };
//		int[] array1 = { 2, 3, 4, 4, 2, 1, 1, 0 };
//		int[] array2 = { 2, 1, 3, 2, 0, 2, 4, 0 };
//		int[] array3 = { 2, 4, 1, 1, 1, 4, 2, 0 };
//		int[] array4 = { 2, 1, 0, 3, 4, 2, 3, 0 };
//		int[][] arrays =  { array0, array1, array2, array3, array4 };
//		
//		ColorCustom[] colorTracker = new ColorCustom[32];
//		
//		for(int i = 0; i < colorTracker.length ; i++)
//		{
//		    colorTracker[i] = new ColorCustom();
//		}
//		
//		ColorCustom[] colors = new ColorCustom[16];
//		
//		for(int i = 0; i < colors.length ; i++)
//		{
//		    colors[i] = new ColorCustom();
//		}
//
//		
//		
//		colors[0].setRGB(255, 0, 0);
//		colors[1].setRGB(255, 128, 0);
//		colors[2].setRGB(255, 255, 0);
//		colors[3].setRGB(155, 255, 51);
//		colors[4].setRGB(0, 102, 0);
//		colors[5].setRGB(255, 153, 153);
//		colors[6].setRGB(102, 255, 178);
//		colors[7].setRGB(0, 0, 51);
//		colors[8].setRGB(51, 0, 102);
//		colors[9].setRGB(102, 0, 102);
//		colors[10].setRGB(153, 51, 255);
//		colors[11].setRGB(255, 0, 127);
//		colors[12].setRGB(128, 128, 128);
//		colors[13].setRGB(128, 0, 0);
//		colors[14].setRGB(184, 134, 11);
//		colors[15].setRGB(139, 69, 19);
//		
//		int tracker = 0;
//		for(int i = 0; i < arrays.length; i++){
//			Log.d("YOOOOO","NEW I");
//			tracker = 0;
//			for(int j = 0; j < 8; j++){
//				
//				Log.d("YOOOO"," NEW J same i");
//				
//				switch(arrays[i][j]){
//				case 0:
//					tracker = tracker + 4;
//					break;
//				case 1:
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					tracker = tracker + 3;
//					break;
//				case 2:
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker = tracker + 3;
//					break;
//				case 3:
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker = tracker + 2;
//					
//					break;
//				case 4:
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
//					tracker++;
//					break;
//				
//				
//				}
//				
//				
//			}
//			
//			
//			
//		}
//		
//		for (int i = 0; i < 32; i++){
//			myColor = Color.argb(150, colorTracker[i].getR(), colorTracker[i].GetG(), colorTracker[i].GetB());
//			secImgBtn[i].setImageResource(pInfo[i].getLv1Pic());
//			secImgBtn[i].setColorFilter(myColor, Mode.MULTIPLY);
//		}
		
		
	
	
	}
	
	public void entireTeam(User myUser){
		setPieInfo();
		setImgBtn();
//		int[] array0 = { 2, 0, 2, 4, 3, 0, 1, 0 };
//		int[] array1 = { 2, 3, 4, 4, 2, 1, 1, 0 };
//		int[] array2 = { 2, 1, 3, 2, 0, 2, 4, 0 };
//		int[] array3 = { 2, 4, 1, 1, 1, 4, 2, 0 };
//		int[] array4 = { 2, 1, 0, 3, 4, 2, 3, 0 };
//		int[][] arrays =  { array0, array1, array2, array3, array4 };
		
		String pieName = db.getUserPieName(userPartner);
		String[] skillNames = db.getPieAttributes(userPartner, pieName);
		//int[] skillValues = db.getTeamPieValues(userPartner, pieName);
		//ArrayList<String> team = db.getTeam(myUser.getTeamId());
		
		// Add by Brent 12112014 ->
		ArrayList<int[]> totalValues = new ArrayList<int[]>();
		ArrayList<String> teamMembers =db.getTeam(23);
		for(int i = 0; i < teamMembers.size(); i++){
		   totalValues.add(db.getPieValues(teamMembers.get(i), db.getUserPieName(teamMembers.get(i))));
		}
		// End of add 12112014 <-
		
		ArrayList<int[]> skills = db.getTeamPieValues(myUser.getTeamId());
		//Object[] arrays = skills.toArray();
		//int[] arrays1 = (int[]) arrays[0];
		
		int sixOrEight = 0;
		String checkNumber = "Eight";
		checkNumber = pieName.substring(0, pieName.indexOf('~'));
		if(checkNumber == "six"){
			sixOrEight = 6;
			skillName1.setText(skillNames[0]);
			skillName2.setText(skillNames[1]);
			skillName3.setText(skillNames[2]);
			skillName4.setText(skillNames[3]);
			skillName5.setText(skillNames[4]);
			skillName6.setText(skillNames[5]);
			
		}else{
			sixOrEight = 8;
			skillName1.setText(skillNames[0]);
			skillName2.setText(skillNames[1]);
			skillName3.setText(skillNames[2]);
			skillName4.setText(skillNames[3]);
			skillName5.setText(skillNames[4]);
			skillName6.setText(skillNames[5]);
			skillName5.setText(skillNames[6]);
			skillName6.setText(skillNames[7]);
		}
		
		
		
		ColorCustom[] colorTracker = new ColorCustom[32];
		
		for(int i = 0; i < colorTracker.length ; i++)
		{
		    colorTracker[i] = new ColorCustom();
		}
		
		ColorCustom[] colors = new ColorCustom[16];
		
		for(int i = 0; i < colors.length ; i++)
		{
		    colors[i] = new ColorCustom();
		}

		
		
		colors[0].setRGB(255, 0, 0);
		colors[1].setRGB(255, 128, 0);
		colors[2].setRGB(255, 255, 0);
		colors[3].setRGB(155, 255, 51);
		colors[4].setRGB(0, 102, 0);
		colors[5].setRGB(255, 153, 153);
		colors[6].setRGB(102, 255, 178);
		colors[7].setRGB(0, 0, 51);
		colors[8].setRGB(51, 0, 102);
		colors[9].setRGB(102, 0, 102);
		colors[10].setRGB(153, 51, 255);
		colors[11].setRGB(255, 0, 127);
		colors[12].setRGB(128, 128, 128);
		colors[13].setRGB(128, 0, 0);
		colors[14].setRGB(184, 134, 11);
		colors[15].setRGB(139, 69, 19);
		
		int tracker = 0;
		for(int i = 0; i < skills.size(); i++){
			Log.d("YOOOOO","NEW I");
			tracker = 0;
			for(int j = 0; j < 8; j++){
				
				Log.d("YOOOO"," NEW J same i");
				
				switch(skills.get(i)[j]){
				case 0:
					tracker = tracker + 4;
					break;
				case 1:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					tracker = tracker + 3;
					break;
				case 2:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker = tracker + 3;
					break;
				case 3:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker = tracker + 2;
					
					break;
				case 4:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					break;
				
				
				}
				
				
			}
			
			
			
		}
		
		for (int i = 0; i < 32; i++){
			myColor = Color.argb(150, colorTracker[i].getR(), colorTracker[i].GetG(), colorTracker[i].GetB());
			secImgBtn[i].setImageResource(pInfo[i].getLv1Pic());
			secImgBtn[i].setColorFilter(myColor, Mode.MULTIPLY);
		}
		
		
	}
	
	
	public void oneTeamMember(){
		
		
		String pieName = db.getUserPieName(userPartner);
		String[] skillNames = db.getPieAttributes(userPartner, pieName);
		int[] skillValues = db.getTeamPieValues(userPartner, pieName);
		
		int sixOrEight = 0;
		String checkNumber = "Eight";
		checkNumber = pieName.substring(0, pieName.indexOf('~'));
		if(checkNumber == "six"){
			sixOrEight = 6;
			skillName1.setText(skillNames[0]);
			skillName2.setText(skillNames[1]);
			skillName3.setText(skillNames[2]);
			skillName4.setText(skillNames[3]);
			skillName5.setText(skillNames[4]);
			skillName6.setText(skillNames[5]);
			
		}else{
			sixOrEight = 8;
			skillName1.setText(skillNames[0]);
			skillName2.setText(skillNames[1]);
			skillName3.setText(skillNames[2]);
			skillName4.setText(skillNames[3]);
			skillName5.setText(skillNames[4]);
			skillName6.setText(skillNames[5]);
			skillName5.setText(skillNames[6]);
			skillName6.setText(skillNames[7]);
		}
		
		
		
		setPieInfo();
		setImgBtn();
//		int[] array0 = { 2, 0, 2, 4, 3, 0, 1, 0 };
//		int[] array1 = { 2, 3, 4, 4, 2, 1, 1, 0 };
//		int[] array2 = { 2, 1, 3, 2, 0, 2, 4, 0 };
//		int[] array3 = { 2, 4, 1, 1, 1, 4, 2, 0 };
//		int[] array4 = { 2, 1, 0, 3, 4, 2, 3, 0 };
//		int[][] arrays =  { array0, array1, array2, array3, array4 };
		
		ColorCustom[] colorTracker = new ColorCustom[32];
		
		for(int i = 0; i < colorTracker.length ; i++)
		{
		    colorTracker[i] = new ColorCustom();
		}
		
		ColorCustom[] colors = new ColorCustom[16];
		
		for(int i = 0; i < colors.length ; i++)
		{
		    colors[i] = new ColorCustom();
		}

		
		
		colors[0].setRGB(255, 0, 0);
		colors[1].setRGB(255, 128, 0);
		colors[2].setRGB(255, 255, 0);
		colors[3].setRGB(155, 255, 51);
		colors[4].setRGB(0, 102, 0);
		colors[5].setRGB(255, 153, 153);
		colors[6].setRGB(102, 255, 178);
		colors[7].setRGB(0, 0, 51);
		colors[8].setRGB(51, 0, 102);
		colors[9].setRGB(102, 0, 102);
		colors[10].setRGB(153, 51, 255);
		colors[11].setRGB(255, 0, 127);
		colors[12].setRGB(128, 128, 128);
		colors[13].setRGB(128, 0, 0);
		colors[14].setRGB(184, 134, 11);
		colors[15].setRGB(139, 69, 19);
		
		int tracker = 0;
//		for(int i = 0; i < 1; i++){
			//Log.d("YOOOOO","NEW I");
			tracker = 0;
			for(int i = 0; i < sixOrEight; i++){
				
				Log.d("YOOOO"," NEW J same i");
				
				switch(skillValues[i]){
				case 0:
					tracker = tracker + 4;
					break;
				case 1:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					tracker = tracker + 3;
					break;
				case 2:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker = tracker + 3;
					break;
				case 3:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker = tracker + 2;
					
					break;
				case 4:
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					colorTracker[tracker].addRGB(colors[i].getR(),colors[i].GetG(),colors[i].GetB());
					tracker++;
					break;
				
				
				}
				
				
			}
			
			
			
//		}
		
		for (int i = 0; i < 32; i++){
			myColor = Color.argb(150, colorTracker[i].getR(), colorTracker[i].GetG(), colorTracker[i].GetB());
			secImgBtn[i].setImageResource(pInfo[i].getLv1Pic());
			secImgBtn[i].setColorFilter(myColor, Mode.MULTIPLY);
		}
		
		
	}
	

//	private void popultateListVIew(String[] users) {
//		String[] userColors = {"Red", "Ornge", "Yellow", "Light Yellow", "Dark Green", "Pink", "Light Blue", "Dark Blue",
//				"Blue", "Purple", "Light Purple", "Hot Pink", "Grey", "Dark Red", "Gold", "Brown"};
//		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, R.layout.user_colors, userColors);
//	}

	public void setPieInfo() {
		// initialize pie info
		//Section 1
		pInfo[0] = new PieInfo(R.id.piesec1_1_r1,
				R.drawable.piesec1_1);
		pInfo[1] = new PieInfo(R.id.piesec1_2_r1,
				R.drawable.piesec1_2_r);
		pInfo[2] = new PieInfo(R.id.piesec1_3_r1,
				R.drawable.piesec1_3_r);
		pInfo[3] = new PieInfo(R.id.piesec1_4_r1,
				R.drawable.piesec1_4_r);
		
		//Section 2
		pInfo[4] = new PieInfo(R.id.piesec2_1_r1,
				R.drawable.piesec2_1);
		pInfo[5] = new PieInfo(R.id.piesec2_2_r1,
				R.drawable.piesec2_2_r);
		pInfo[6] = new PieInfo(R.id.piesec2_3_r1,
				R.drawable.piesec2_3_r);
		pInfo[7] = new PieInfo(R.id.piesec2_4_r1,
				R.drawable.piesec2_4_r);
		
		//Section 3
		pInfo[8] = new PieInfo(R.id.piesec3_1_r1,
				R.drawable.piesec3_1);
		pInfo[9] = new PieInfo(R.id.piesec3_2_r1,
				R.drawable.piesec3_2_r);
		pInfo[10] = new PieInfo(R.id.piesec3_3_r1,
				R.drawable.piesec3_3_r);
		pInfo[11] = new PieInfo(R.id.piesec3_4_r1,
				R.drawable.piesec3_4_r);
		
		//Section 4
		pInfo[12] = new PieInfo(R.id.piesec4_1_r1,
				R.drawable.piesec4_1);
		pInfo[13] = new PieInfo(R.id.piesec4_2_r1,
				R.drawable.piesec4_2_r);
		pInfo[14] = new PieInfo(R.id.piesec4_3_r1,
				R.drawable.piesec4_3_r);
		pInfo[15] = new PieInfo(R.id.piesec4_4_r1,
				R.drawable.piesec4_4_r);
		
		//Section 5
		pInfo[16] = new PieInfo(R.id.piesec5_1_r1,
				R.drawable.piesec5_1);
		pInfo[17] = new PieInfo(R.id.piesec5_2_r1,
				R.drawable.piesec5_2_r);
		pInfo[18] = new PieInfo(R.id.piesec5_3_r1,
				R.drawable.piesec5_3_r);
		pInfo[19] = new PieInfo(R.id.piesec5_4_r1,
				R.drawable.piesec5_4_r);
		
		//Section 6
		pInfo[20] = new PieInfo(R.id.piesec6_1_r1,
				R.drawable.piesec6_1);
		pInfo[21] = new PieInfo(R.id.piesec6_2_r1,
				R.drawable.piesec6_2_r);
		pInfo[22] = new PieInfo(R.id.piesec6_3_r1,
				R.drawable.piesec6_3_r);
		pInfo[23] = new PieInfo(R.id.piesec6_4_r1,
				R.drawable.piesec6_4_r);
		
		//Section 7
		pInfo[24] = new PieInfo(R.id.piesec7_1_r1,
				R.drawable.piesec7_1);
		pInfo[25] = new PieInfo(R.id.piesec7_2_r1,
				R.drawable.piesec7_2_r);
		pInfo[26] = new PieInfo(R.id.piesec7_3_r1,
				R.drawable.piesec7_3_r);
		pInfo[27] = new PieInfo(R.id.piesec7_4_r1,
				R.drawable.piesec7_4_r);
		
		//Section 8
		pInfo[28] = new PieInfo(R.id.piesec8_1_r1,
				R.drawable.piesec8_1);
		pInfo[29] = new PieInfo(R.id.piesec8_2_r1,
				R.drawable.piesec8_2_r);
		pInfo[30] = new PieInfo(R.id.piesec8_3_r1,
				R.drawable.piesec8_3_r);
		pInfo[31] = new PieInfo(R.id.piesec8_4_r1,
				R.drawable.piesec8_4_r);
		
		
	}

	public void setImgBtn() {
		for (int i = 0; i < MAX_SECTION; i++)
			secImgBtn[i] = (ImageView) findViewById(pInfo[i].getResId());
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
