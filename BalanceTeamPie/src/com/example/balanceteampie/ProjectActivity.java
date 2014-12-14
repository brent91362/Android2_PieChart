package com.example.balanceteampie;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProjectActivity extends Activity {
	Database db = new Database();
	String userName = "";
	User myUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);
		
		myUser = (User) getIntent().getParcelableExtra("USER_INFO");
		populateListView();
		registerClickCallback();
	}

	private void populateListView() {
		ArrayList<String> team = db.getTeam(myUser.getTeamId());

		String[] teamNames = team.toArray(new String[team.size()]);

		// Build Adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.activity_project_list, teamNames);

		// Configure list view
		ListView list = (ListView) findViewById(R.id.ProjectlistView);
		list.setAdapter(adapter);
	}

	private void registerClickCallback() {
		final ListView list = (ListView) findViewById(R.id.ProjectlistView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				// Pass selected member info for pie view
				userName = (String) list.getItemAtPosition(position);				
				User userMember = new User(userName, "", "", "", "");
				userMember.setTeamId(myUser.getTeamId());
				intent.putExtra("USER_INFO", userMember);
				
				// Determine the pie is view only
				intent.putExtra("PERMISSION_TO_EDIT", false);
				
				intent.setClass(ProjectActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getActionBar().setTitle("Team Members");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.project, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.action_refresh:
		   populateListView();
			Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
			break;
		// action with ID action_settings was selected
		case R.id.action_signout:
			Toast.makeText(this, "Sign out selected", Toast.LENGTH_SHORT)
					.show();
			finish();
			break;
		default:
			break;
		}

		return true;
	}
}