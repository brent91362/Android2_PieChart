package com.example.balanceteampie;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);
		
		populateListView();
		registerClickCallback();
	}


	private void populateListView() {
		// Create Dummy list of projects using ArrayAdapter
		// TODO Generate dynamic list from database
		
		String[] projects = {"Project 1", "Project 2", "Project 3", "Project 4",
				"Project 5", "Project 6", "Project 7", "Project 8","Project 9", 
				"Project 10", "Project 11", "Project 12", "Project 13"};
		
		//Build Adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, 
				R.layout.activity_project_list,
				projects);
		
		// Configure list view
		ListView list = (ListView) findViewById(R.id.ProjectlistView);
		list.setAdapter(adapter);		
	}
	
	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.ProjectlistView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
	            intent.setClass(ProjectActivity.this, MainActivity.class);
	            
	            //TODO Pass data to MainActivity for pie creation
	            
	            startActivity(intent);	
			}
		});
	
	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getActionBar().setTitle("Project Selection");
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.project, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // action with ID action_refresh was selected
	    case R.id.action_refresh:
	      Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    // action with ID action_settings was selected
	    case R.id.action_signout:
	      Toast.makeText(this, "Sign out selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  } 
}