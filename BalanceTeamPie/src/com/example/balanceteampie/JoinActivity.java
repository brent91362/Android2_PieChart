package com.example.balanceteampie;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class JoinActivity extends Activity implements OnClickListener {
	private EditText teamView;
	
	
	
	Database db = new Database();
	//Intent mIntent = getIntent();
	//String userName = mIntent.getExtras().getString("USER_NAME");
	//User myUser = (User) mIntent.getParcelableExtra("USER_INFO");
	String userName;
	
	User myUser2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu_join);
		
		Intent i = getIntent();
		User myUser = (User) i.getParcelableExtra("USER_INFO");
		teamView = (EditText) findViewById(R.id.join_team_id);
		userName = myUser.getUsername();
		myUser2 = myUser;
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

		String teamId = teamView.getText().toString();
		if (TextUtils.isEmpty(teamId)) {
			teamView.setError(getString(R.string.error_field_required));
			teamView.requestFocus();
		} else {
			teamView.setText("");
			// TODO: send to DB
//			if (userName == null) {
//				userName = "Mark";
//			}
			int teamID = Integer.parseInt(teamId);
			db.changeTeam("009", teamID);
			Intent intent = new Intent();
			intent.setClass(JoinActivity.this, CreateActivity.class);
			intent.putExtra("USER_INFO", myUser2);
			startActivity(intent);
			JoinActivity.this.finish();
		}
	}

}
