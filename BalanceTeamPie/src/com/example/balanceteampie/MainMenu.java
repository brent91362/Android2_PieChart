package com.example.balanceteampie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity implements OnClickListener {

	User myUser;
	Database db = new Database();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		myUser = (User) getIntent().getParcelableExtra("USER_INFO");		
		UserInfoGet uGet = new UserInfoGet();
		uGet.execute((Void) null);
		
		findViewById(R.id.btnCreate).setOnClickListener(this);
		findViewById(R.id.btnJoin).setOnClickListener(this);
		findViewById(R.id.btnView2).setOnClickListener(this);
		findViewById(R.id.editPie).setOnClickListener(this);
		findViewById(R.id.btnTeamPie).setOnClickListener(this);
	}

	public class UserInfoGet extends AsyncTask<Void, Void, Boolean> {
      protected Boolean doInBackground(Void... params) {
         // DB Calls
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
            TextView teamIdText = (TextView) findViewById(R.id.teamIdText);
            teamIdText.setText("Team ID#: " + myUser.getTeamId());
//            String s = "UserId has been found.";
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
		case R.id.btnView2:
			intent.setClass(MainMenu.this, ProjectActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;
		case R.id.editPie:
			intent.setClass(MainMenu.this, MainActivity.class);
			intent.putExtra("USER_INFO", myUser);
			break;
		case R.id.btnTeamPie:
			intent.setClass(MainMenu.this, Manager.class);
			intent.putExtra("USER_INFO", myUser);
			intent.putExtra("teamMemName", "");
			break;
		}
		startActivity(intent);
		MainMenu.this.finish();
	}
}
