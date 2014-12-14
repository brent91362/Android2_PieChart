package com.example.balanceteampie;

import java.io.IOException;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;

public class MainActivity extends Activity {
	static final int MAX_SECTION = 8;
   static final int MAX_LEVEL = 4;
   static int myColor = Color.argb(150, 204, 0, 0); // Change color here
   static PieInfo pInfo[] = new PieInfo[MAX_SECTION];
   ImageButton secImgBtn[] = new ImageButton[MAX_SECTION];
   TextView secTxtVw[] = new TextView[MAX_SECTION]; // Add by MinL 11272014
   
   ImageButton hotspotImgBtn;   
   ImageView pieImgView;
   
   Button saveBtn; // Add by MinL 11272014
   Button hideBtn; // Add by MinL 11272014
   
   User myUser; // Add by MinL 12112014
   
   Database db = new Database();
   
   int pieLevel[];
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   
      // Add by MinL 12112014 ->
      myUser = (User) getIntent().getParcelableExtra("USER_INFO");
      String pieName = db.getUserPieName(myUser.getUsername());
      boolean canEdit = getIntent().getBooleanExtra("PERMISSION_TO_EDIT", true);
      // End of add  12112014 <-
      
      setPieInfo();
      setImgBtn();
      
      hotspotImgBtn = (ImageButton) findViewById(R.id.ib_pie_hs);
      
      pieImgView = (ImageView) findViewById(R.id.image_pie);
      
      // Add by MinL 11227014 ->
      saveBtn = (Button) findViewById(R.id.button_save);
      saveBtn.setOnClickListener(funcClickListener);
      
      hideBtn = (Button) findViewById(R.id.button_hide);
      hideBtn.setOnClickListener(funcClickListener);
      
      for (int i = 0; i < MAX_SECTION; i++)
         secTxtVw[i] = (TextView) findViewById(R.id.skill_name1 + i);      
      // End of add  11227014 <-
      
      if (!canEdit) {
         saveBtn.setEnabled(false);
         saveBtn.setVisibility(View.INVISIBLE);
      }
      else {
         pieImgView.setOnTouchListener(hsTouchListener);
      }
      
      setPieAttributes(pieName);
      setPieValues(pieName);
   }
   
   /**
    * Add by MinL 11272014
    * ClickListener for Hide button and Save button
    * Hide: Show/Hide the skill name labels
    * Save: Send the pie values to DB
    */
   private final OnClickListener funcClickListener = new OnClickListener() {
      public void onClick(View v) {
         Button btn = (Button) v;
         if (btn.getId() == R.id.button_save) {
            int pieLevel[] = new int[myUser.getNumOfSkills()];
            String pieValue = "";
            String pieId = db.getUserPieId(myUser.getUsername());
            for (int i = 0; i < pieLevel.length; i++) {
               pieLevel[i] = pInfo[i].getCountLevel();
               pieValue += pieLevel[i];               
            }
            myUser.setSkillLevels(pieLevel);
            
            // update or create user pie            
            if (pieId == null) {
               String pieName = "";
               if (myUser.getNumOfSkills() == 6) pieName = "six";
               if (myUser.getNumOfSkills() == 8) pieName = "eight";
               
               db.CreatePie(myUser.getUsername(), pieName, 
                     myUser.getSkillNameList(), Integer.parseInt(pieValue), 1);
            }
            else {
               try {
                  // TODO: bad design, pie value cannot start with 0 level
                  db.updateuserPie(pieId, pieValue);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
         if (btn.getId() == R.id.button_hide) {
            if (secTxtVw[0].getVisibility() == View.INVISIBLE) {
               for (int i = 0; i < MAX_SECTION; i++) {
                  btn.setText(R.string.button_text_hide);
                  secTxtVw[i].setVisibility(View.VISIBLE);
               }
            }
            else {
               for (int i = 0; i < MAX_SECTION; i++) {
                  btn.setText(R.string.button_text_show);
                  secTxtVw[i].setVisibility(View.INVISIBLE);
               }
            }
         }
      }      
   };
   
   /**
    * Use the touched color to find out which section and levels to be set
    */
   private final OnTouchListener hsTouchListener = new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {         
         final int evX = (int) event.getX();
         final int evY = (int) event.getY();
         if(event.getAction() == MotionEvent.ACTION_UP) {
            int touchColor = getHotspotColor (R.id.ib_pie_hs, evX, evY);
            for (int i = 0; i < myUser.getNumOfSkills(); i++) {
               if (matchColor(pInfo[i].getColor(), touchColor)) {              
                  setLevel(i);
                  return true;
               }
            }
         }
         return true;
      }      
   };

   /**
    * Use the touched position to find the color on the pie_full_hs.png image 
    * @param hotspotId
    * @param x
    * @param y
    * @return color code
    */
   public int getHotspotColor (int hotspotId, int x, int y) {
      int t = 0;
      try {
         hotspotImgBtn.setDrawingCacheEnabled(true); 
         Bitmap hotspots = 
            Bitmap.createBitmap(hotspotImgBtn.getDrawingCache());
         hotspotImgBtn.setDrawingCacheEnabled(false);
         t = hotspots.getPixel(x, y);
      } catch(Exception e) {
         // sometimes bitmap can cause OutOfMemory exception
         setToast("Error!!" + e.getMessage());
      }
      return t;
   }
   
   /**
    * Match the destined color to the touched color
    * @param destColor
    * @param touchColor
    * @return
    */
   public boolean matchColor(int destColor, int touchColor) {
      if(destColor == touchColor)
         return true;
      else
         return false;
   }

   /**
    * Add by MinL 12122014
    * Set the pie name labels depends on the number of sections
    * @param piename: pie name from database
    */
   public void setPieAttributes(String piename) {
      String[] sn = new String[MAX_SECTION];
      if (piename != null) {
         sn = db.getPieAttributes(myUser.getUsername(), piename);
         if (sn != null) {
            myUser.setSkillNames(sn);
            // Set pie section(slices) names on the screen
            int numOfSkills = myUser.getNumOfSkills();
            for (int i = 0; i < numOfSkills; i++) {
               secTxtVw[i].setText(sn[i]);
            }
            // Hide the unused name labels
            for (int i = numOfSkills; i < MAX_SECTION; i++) {
               secTxtVw[i].setText("");
               secTxtVw[i].setVisibility(View.INVISIBLE);
            }            
         }
      }
   }
   
   /**
    * Add By MinL 12112014
    * Set the pie values depends on the number of sections
    * @param piename: pie name from database
    */
   public void setPieValues(String piename) {
      int[] vals = db.getPieValues(myUser.getUsername(), piename);
      for(int i = 0; i < vals.length; i++) {
         for(int j = 0; j < vals[i]; j++) {
            setLevel(i);
         }         
      }
      myUser.setSkillLevels(vals);
   }
   /**
    * Increment the skill level from zero to four
    * @param secId
    */
   public void setLevel(int secId) {
      // increment level
      pInfo[secId].setCountLevel();
      
      // reset pie section
      if(pInfo[secId].getCountLevel() > MAX_LEVEL)
         pInfo[secId].resetLevel();
      
//      setToast("Section: " + (secId + 1) + "\n" 
//               + "Skill Level: " + pInfo[secId].getCountLevel());
      
      secImgBtn[secId].setVisibility(View.VISIBLE); // fixed by MinL 12112014
      
      switch(pInfo[secId].getCountLevel()) {
      case 1:
         secImgBtn[secId].setImageResource(pInfo[secId].getLv1Pic());
         break;
      case 2:
         secImgBtn[secId].setImageResource(pInfo[secId].getLv2Pic());
         break;
      case 3:
         secImgBtn[secId].setImageResource(pInfo[secId].getLv3Pic());
         break;
      case 4:
         secImgBtn[secId].setImageResource(pInfo[secId].getLv4Pic());
         break;
      default:
         secImgBtn[secId].setVisibility(View.INVISIBLE);
         break;
      }
      secImgBtn[secId].setColorFilter(myColor,Mode.MULTIPLY);
   }
   
   public void setPieInfo() {
      // initialize pie info
      pInfo[0] = new PieInfo(R.id.ib_pie1_4,
                             R.drawable.piesec1_1,
                             R.drawable.piesec1_2,
                             R.drawable.piesec1_3,
                             R.drawable.piesec1_4,
                             Color.rgb(255,0,255)); // Pink
      pInfo[1] = new PieInfo(R.id.ib_pie2_4,
                             R.drawable.piesec2_1,
                             R.drawable.piesec2_2,
                             R.drawable.piesec2_3,
                             R.drawable.piesec2_4,
                             Color.rgb(255,144,0)); // Orange
      pInfo[2] = new PieInfo(R.id.ib_pie3_4,
                             R.drawable.piesec3_1,
                             R.drawable.piesec3_2,
                             R.drawable.piesec3_3,
                             R.drawable.piesec3_4,
                             Color.rgb(0,0,255)); // Blue
      pInfo[3] = new PieInfo(R.id.ib_pie4_4,
                             R.drawable.piesec4_1,
                             R.drawable.piesec4_2,
                             R.drawable.piesec4_3,
                             R.drawable.piesec4_4,
                             Color.rgb(0,255,0)); // Green
      pInfo[4] = new PieInfo(R.id.ib_pie5_4,
                             R.drawable.piesec5_1,
                             R.drawable.piesec5_2,
                             R.drawable.piesec5_3,
                             R.drawable.piesec5_4,
                             Color.rgb(255,0,0)); // Red
      pInfo[5] = new PieInfo(R.id.ib_pie6_4,
                             R.drawable.piesec6_1,
                             R.drawable.piesec6_2,
                             R.drawable.piesec6_3,
                             R.drawable.piesec6_4,
                             Color.rgb(255,255,0)); // Yellow
      pInfo[6] = new PieInfo(R.id.ib_pie7_4,
                             R.drawable.piesec7_1,
                             R.drawable.piesec7_2,
                             R.drawable.piesec7_3,
                             R.drawable.piesec7_4,
                             Color.rgb(153,0,255)); // Purple
      pInfo[7] = new PieInfo(R.id.ib_pie8_4,
                             R.drawable.piesec8_1,
                             R.drawable.piesec8_2,
                             R.drawable.piesec8_3,
                             R.drawable.piesec8_4,
                             Color.rgb(0,255,255)); // Light blue
   }
   
   public void setImgBtn() {
      for(int i = 0; i < MAX_SECTION; i ++)
         secImgBtn[i] = (ImageButton) findViewById(pInfo[i].getResId());
   }
   
   public void setToast(String s) {
      Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      switch (id) {
      case R.id.action_settings:
         return true;
      case R.id.action_exit:
         finish();
         System.exit(0);
      }
      return super.onOptionsItemSelected(item);
   }
}