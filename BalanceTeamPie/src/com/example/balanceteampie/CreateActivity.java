package com.example.balanceteampie;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class CreateActivity extends Activity implements OnClickListener {
   public static final int MAX_SKILLS = 8;
   private EditText projView;
   private EditText[] skillView = new EditText[MAX_SKILLS];
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.fragment_menu_create);
      
      projView = (EditText) findViewById(R.id.project_name);
      for(int i = 0; i < skillView.length; i++) {
         skillView[i] = (EditText) findViewById(R.id.skill_name1 + i);
      }
      
      findViewById(R.id.button_create).setOnClickListener(this);
      
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.create, menu);
      return true;
   }

   /**
    * Respond to click event
    * btnCreate : validate all input fields and setup new project
    */
   public void onClick(View v) {
      // reset errors
      projView.setError(null);
      skillView[0].setError(null);
      
      boolean cancel = false;
      View focusView = null;
      
      String pName;
      String[] sName = new String[MAX_SKILLS];
      pName = projView.getText().toString();
      for(int i = 0; i < skillView.length; i++)
         sName[i] = skillView[i].getText().toString();
      
      // Validate the project name
      if(TextUtils.isEmpty(pName)) {
         projView.setError(getString(R.string.error_field_required));
         cancel = true;
         focusView = projView;
      }
      
      // Validate the first skill name
      if(TextUtils.isEmpty(sName[0])) {
         skillView[0].setError(getString(R.string.error_field_required));
         cancel = true;
         focusView = skillView[0];
      }         
      
      if(cancel)
         focusView.requestFocus();
      else {
         // TODO: Send to DB
         // Clear all the input
         projView.setText("");
         for(int i = 0; i < skillView.length; i++) {
            skillView[i].setText("");
         }            
      }
      
   }

}
