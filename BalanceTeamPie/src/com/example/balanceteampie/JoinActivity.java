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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity implements OnClickListener {
	private EditText teamView;
	
	Database db = new Database();
	User myUser;
	String teamId = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu_join);
		
		myUser = (User) getIntent().getParcelableExtra("USER_INFO");
		teamView = (EditText) findViewById(R.id.join_team_id);
		
		Button joinBtn = (Button) findViewById(R.id.button_join);
		joinBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join, menu);
		return true;
	}

	/**
	 * Respond to click event btnJoin : validate all input fields and join team
	 */
	public void onClick(View v) {
		teamView.setError(null);

		teamId = teamView.getText().toString();
		if (TextUtils.isEmpty(teamId)) {
			teamView.setError(getString(R.string.error_field_required));
			teamView.requestFocus();
		} else {
			// Give a pop up dialog
			new AlertDialog.Builder(this)
			.setTitle("Confirm Join")
			.setMessage(Html.fromHtml("Are you sure you want to join the team? " 
			   + "<font color='#FF0000'>(This will replace the current team you're in.)</font>"))
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) { 
			      // continue to join
			      UserInfoGet uGet = new UserInfoGet();
		         uGet.execute();
		         teamView.setText("");
		         
		         Intent intent = new Intent();
		         intent.setClass(JoinActivity.this, MainMenu.class);
		         intent.putExtra("USER_INFO", myUser);
		         startActivity(intent);
		         JoinActivity.this.finish();
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
         int teamID = Integer.parseInt(teamId);
         db.changeTeam(myUser.getUsername(), teamID);
         String userId = db.getUserTeamID(myUser.getUsername());
         if(userId != null) {
            myUser.setTeamId(Integer.parseInt(userId));
            return true;
         }
         return false;
      }

      protected void onPostExecute(final Boolean success) {
         // Any post message
         if(success) {
            Toast.makeText(getApplicationContext(), "Team changed", Toast.LENGTH_SHORT)
            .show();
         }
      }

      @Override
      protected void onCancelled() {
         
      }
   }

}
