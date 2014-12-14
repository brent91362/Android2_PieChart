package com.example.balanceteampie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateActivity extends Activity implements OnClickListener {
	public static final int MAX_SKILLS = 8;
	private EditText projView;
	private EditText[] skillView = new EditText[MAX_SKILLS];

	// database stuff
	Database db = new Database();
	User myUser;
	String userName = "";
	boolean onOrOff = true; //default is 8 skills: on for 8; off for 6
	int defaultPieValue = 10000000;
	
	String pName;
   String[] sName = new String[MAX_SKILLS];
   String pieName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu_create);
		
		myUser = (User) getIntent().getParcelableExtra("USER_INFO");
		userName = myUser.getUsername();
		
		projView = (EditText) findViewById(R.id.project_name);
		for (int i = 0; i < skillView.length; i++) {
			skillView[i] = (EditText) findViewById(R.id.skill_name1 + i);
		}

		findViewById(R.id.button_create).setOnClickListener(this);
		
		ToggleButton b = (ToggleButton) findViewById(R.id.sixOrEight);
		b.setOnClickListener(new View.OnClickListener() {
		   public void onClick(View v) {
		      if (v.getId() == R.id.sixOrEight) {
   		      if (((ToggleButton) v).isChecked()) {
   		         onOrOff = true;
   		         skillView[6].setEnabled(true);
                  skillView[7].setEnabled(true);
   		      }
   		      else {
   		         onOrOff = false;
   		         skillView[6].setText("");
   		         skillView[7].setText("");
   		         skillView[6].setError(null);
   		         skillView[7].setError(null);
   		         skillView[6].setEnabled(false);
   		         skillView[7].setEnabled(false);
   		      }
		      }
		   }
		});		
	}   
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
		return true;
	}	
	
	/**
	 * Respond to click event btnCreate : validate all input fields and setup
	 * new project
	 */
	public void onClick(View v) {
		// reset errors
		projView.setError(null);
		skillView[0].setError(null);

		boolean cancel = false;
		View focusView = null;

		pName = projView.getText().toString();
		for (int i = 0; i < skillView.length; i++)
			sName[i] = skillView[i].getText().toString();

		// TODO: all names cannot contain spaces??! Need more checks!!
		// Validate the project name
		if (TextUtils.isEmpty(pName)) {
			projView.setError(getString(R.string.error_field_required));
			cancel = true;
			focusView = projView;
		}

		// Validate the skill names
		for (int x = 0; x < 6; x++) {
			if (TextUtils.isEmpty(sName[x])) {
				skillView[x].setError(getString(R.string.error_field_required));
				cancel = true;
				focusView = skillView[x];
			}
		}
		if (onOrOff == true) {
		   // Validation for the case 8 skills are required
			if (TextUtils.isEmpty(sName[6])) {
				skillView[6].setError(getString(R.string.error_field_required));
				cancel = true;
				focusView = skillView[6];
			}
			if (TextUtils.isEmpty(sName[7])) {
				skillView[7].setError(getString(R.string.error_field_required));
				cancel = true;
				focusView = skillView[7];
			}			
		}		
		
		if (cancel)
			focusView.requestFocus();
		else {
			if (onOrOff == true){
				pieName = "eight";
				defaultPieValue = 10000000;
			} else {
				pieName = "six";
				defaultPieValue = 100000;
			}
			
			// Give a pop up dialog
			new AlertDialog.Builder(this)
			.setTitle("Confirm Creation")
			.setMessage(Html.fromHtml("Are you sure you want to create the team? " 
			      + "<font color='#FF0000'> (The skill names cannot be changed later.)</font>"))
			      .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			         public void onClick(DialogInterface dialog, int which) { 
			            // continue to Create
			            //  When a team is created, a pie is created to store the skill names
			            //  The creator automatically becomes a team leader and join the team
			            //  A team id# will be shown on the menu page so other users can join the team.
			            UserInfoGet uGet = new UserInfoGet();
			            uGet.execute((Void) null);
			            
			            Intent intent = new Intent();
			            intent.setClass(CreateActivity.this, MainMenu.class);
			            intent.putExtra("USER_INFO", myUser);
			            startActivity(intent);
			            CreateActivity.this.finish();
			         }
			      })
			      .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			         public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			         }
			      })
			      .setIcon(android.R.drawable.ic_dialog_alert)
			      .show();						
		}
	}
	
	public class UserInfoGet extends AsyncTask<Void, Void, Boolean> {
      protected Boolean doInBackground(Void... params) {
         // DB Calls
         db.createTeam(userName, pName);
         // TODO: bad design for pie value, needs to be fixed later
         db.CreatePie(userName, pieName, sName, defaultPieValue, 1);
         db.changeTeam(userName, Integer.parseInt(
               db.getLeaderTeamID(db.getAccountId(userName), pName)));
         return true;
      }

      protected void onPostExecute(final Boolean success) {
         // Any post message
         if(success) {
            Toast.makeText(getApplicationContext(), "Team created.", Toast.LENGTH_SHORT)
            .show();
            // Clear after a pie is created
            projView.setText("");
            for (int i = 0; i < skillView.length; i++) {
               skillView[i].setText("");
            }
         }
      }

      @Override
      protected void onCancelled() {
         
      }
   }
}
