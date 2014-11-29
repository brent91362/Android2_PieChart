package com.example.balanceteampie;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;

public class MainActivity extends Activity implements AllPerson{
	private static final String TABLE = "Experience";
	SQLiteDatabase db;
	final String dbName = "PieChartdb";
   static final int MAX_SECTION = 8;
   static final int MAX_LEVEL = 4;
   static int myColor = Color.argb(150, 204, 0, 0); // Change color here
   static PieInfo pInfo[] = new PieInfo[MAX_SECTION];
   ImageButton secImgBtn[] = new ImageButton[MAX_SECTION];
   TextView secTxtVw[] = new TextView[MAX_SECTION]; // Add by MinL 11272014
   
   ImageButton hotspotImgBtn;   
   ImageView pieImgView;
   
   String project; // Add by MinL 11272014
   Button saveBtn; // Add by MinL 11272014
   Button hideBtn; // Add by MinL 11272014
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   
      // Add by MinL 11272014 ->
      //  Get the selected project name from previous project activity
      project = getIntent().getStringExtra("SELECTED_PROJ");
      setToast("You're now working on [" + project + "]");
      
      //  Then, get pie values from DB (if ever worked on)
      // End of add  11272014 <-
      
      setPieInfo();
      setImgBtn();
      
      hotspotImgBtn = (ImageButton) findViewById(R.id.ib_pie_hs);
      
      pieImgView = (ImageView) findViewById(R.id.image_pie);
      pieImgView.setOnTouchListener(hsTouchListener);
      
      // Add by MinL 11227014 ->
      saveBtn = (Button) findViewById(R.id.button_save);
      saveBtn.setOnClickListener(funcClickListener);     
      
      hideBtn = (Button) findViewById(R.id.button_hide);
      hideBtn.setOnClickListener(funcClickListener);
      
      for (int i = 0; i < MAX_SECTION; i++)
         secTxtVw[i] = (TextView) findViewById(R.id.skill_name1 + i);      
      // End of add  11227014 <-
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
            //Save pie info to DB
            int pieLevel[] = new int[MAX_SECTION];
            for (int i = 0; i < pInfo.length; i++) {
               pieLevel[i] = pInfo[i].getCountLevel();
            }            
            setToast("submit pieLevel[] to DB");
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
    * Use the touched color to find out which section levels to be set
    */
   private final OnTouchListener hsTouchListener = new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {         
         final int evX = (int) event.getX();
         final int evY = (int) event.getY();
         if(event.getAction() == MotionEvent.ACTION_UP) {
            int touchColor = getHotspotColor (R.id.ib_pie_hs, evX, evY);
            if (matchColor(pInfo[0].getColor(), touchColor)) {              
               setLevel(0);
            } else if (matchColor(pInfo[1].getColor(), touchColor)) {
               setLevel(1);
            } else if (matchColor(pInfo[2].getColor(), touchColor)) {
               setLevel(2);
            } else if (matchColor(pInfo[3].getColor(), touchColor)) {
               setLevel(3);
            } else if (matchColor(pInfo[4].getColor(), touchColor)) {
               setLevel(4);
            } else if (matchColor(pInfo[5].getColor(), touchColor)) {
               setLevel(5);
            } else if (matchColor(pInfo[6].getColor(), touchColor)) {
               setLevel(6);
            } else if (matchColor(pInfo[7].getColor(), touchColor)) {
               setLevel(7);
            } else {
               //setToast("Touched elsewhere");
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
   
   public void setLevel(int secId) {
      // increment level
      pInfo[secId].setCountLevel();
      
      // reset pie section
      if(pInfo[secId].getCountLevel() > MAX_LEVEL)
         pInfo[secId].resetLevel();
      
      setToast("Section: " + (secId + 1) + "\n" 
               + "Skill Level: " + pInfo[secId].getCountLevel());
      
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

@Override
public int getPieValues() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getColor() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void setPieValues(int[] experience) {
	try{
		db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, NAME TEXT, PLACE TEXT);");
        String exp="5, 6";
        db.execSQL("INSERT INTO " + TABLE + "(NAME, Value) VALUES('Employee1',exp )");
        Cursor getvalues = db.rawQuery("SELECT Value FROM"+ TABLE,null);
        Integer vales=getvalues.getCount();
        System.out.println("\n\n"+vales);
	}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	
}

@Override
public void setColor(int[] rgb) {
	// TODO Auto-generated method stub
	
}

@Override
public PieInfo getpie() {
	// TODO Auto-generated method stub
	return null;
}

}