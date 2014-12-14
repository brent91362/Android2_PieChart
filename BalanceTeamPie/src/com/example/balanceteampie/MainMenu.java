package com.example.balanceteampie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainMenu extends Activity implements OnClickListener {

	User myUser;
	Database db = new Database();
	String teamId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		myUser = (User) getIntent().getParcelableExtra("USER_INFO");
				
		findViewById(R.id.btnCreate).setOnClickListener(this);
		findViewById(R.id.btnJoin).setOnClickListener(this);
		findViewById(R.id.btnView2).setOnClickListener(this);
		findViewById(R.id.editPie).setOnClickListener(this);
		findViewById(R.id.btnTeamPie).setOnClickListener(this);
	}
	
	protected void onStart() {
	   super.onStart();
	   UserInfoGet uGet = new UserInfoGet();
      uGet.execute((Void) null);
	}

	public class UserInfoGet extends AsyncTask<Void, Void, Boolean> {
      protected Boolean doInBackground(Void... params) {
         // DB Calls
         teamId = db.getUserTeamID(myUser.getUsername());
         if(teamId != null) {
            myUser.setTeamId(Integer.parseInt(teamId));
            return true;
         }
         return false;
      }

      protected void onPostExecute(final Boolean success) {
         // Any post message
         if(success) {
            TextView teamIdText = (TextView) findViewById(R.id.teamIdText);
            teamIdText.setText("Team ID#: " + myUser.getTeamId());
         }
      }

      @Override
      protected void onCancelled() {
         
      }
   }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		
		switch (v.getId()) {
		case R.id.btnCreate:
			intent.setClass(MainMenu.this, CreateActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;
		case R.id.btnJoin:
			intent.setClass(MainMenu.this, JoinActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;
		case R.id.btnTeamPie:
		   if (teamId == null) {
            new AlertDialog.Builder(this)
            .setTitle("No Team Has Found")
            .setMessage("Please start with join a team or create a team.")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) { 
                  return;
               }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
            return;
         }
         intent.setClass(MainMenu.this, Manager.class);
         intent.putExtra("USER_INFO", myUser);
         intent.putExtra("teamMemName", "");
         break;
		case R.id.btnView2:
			intent.setClass(MainMenu.this, ProjectActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;
		case R.id.editPie:
		   if (teamId == null) {
		      new AlertDialog.Builder(this)
		      .setTitle("No Team Has Found")
		      .setMessage("Please start with join a team or create a team.")
		      .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int which) { 
		            return;
		         }
		      })
		      .setIcon(android.R.drawable.ic_dialog_alert)
		      .show();
		      return;
         }
			intent.setClass(MainMenu.this, MainActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;		
		}
		startActivity(intent);
	}
}
