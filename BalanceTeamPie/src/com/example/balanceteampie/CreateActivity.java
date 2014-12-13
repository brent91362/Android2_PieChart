package com.example.balanceteampie;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class CreateActivity extends Activity implements OnClickListener {
	public static final int MAX_SKILLS = 8;
	private EditText projView;
	private EditText[] skillView = new EditText[MAX_SKILLS];

	// database stuff
	Database db = new Database();
	// Intent mIntent = getIntent();
	// String userName = mIntent.getExtras().getString("USER_NAME");
	//User myUser = (User) i.getParcelableExtra("USER_INFO");
	//myUser = (User) getIntent().getParcelableExtra("USER_INFO");
	
	
	String userName = "";
	boolean onOrOff = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_menu_create);
		
		
		
		Intent mIntent = getIntent();
		User myUser = (User) mIntent.getParcelableExtra("USER_INFO");
		userName = myUser.getUsername();
		projView = (EditText) findViewById(R.id.project_name);
		for (int i = 0; i < skillView.length; i++) {
			skillView[i] = (EditText) findViewById(R.id.skill_name1 + i);
		}

		findViewById(R.id.button_create).setOnClickListener(this);
		
		ToggleButton b = (ToggleButton) findViewById(R.id.sixOrEight);
		b.setOnClickListener(new View.OnClickListener() {
		      public void onClick(View v) {
		        if (((ToggleButton) v).isChecked())
		        	onOrOff = true;
		        else
		        	onOrOff = false;
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
		String pieName = "";
		// reset errors
		projView.setError(null);
		skillView[0].setError(null);

		boolean cancel = false;
		View focusView = null;

		String pName;
		String[] sName = new String[MAX_SKILLS];
		pName = projView.getText().toString();
		for (int i = 0; i < skillView.length; i++)
			sName[i] = skillView[i].getText().toString();

		// Validate the project name
		if (TextUtils.isEmpty(pName)) {
			projView.setError(getString(R.string.error_field_required));
			cancel = true;
			focusView = projView;
		}

		// Validate the first skill name
		for (int x = 0; x < 6; x++) {
			if (TextUtils.isEmpty(sName[x])) {
				skillView[x].setError(getString(R.string.error_field_required));
				cancel = true;
				focusView = skillView[x];
			}
		}
		if(onOrOff == true){
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
			
		}else {
			skillView[6].setText("");
			skillView[7].setText("");
		}
		
		
		if (cancel)
			focusView.requestFocus();
		else {
			// TODO: Send to DB
			// Clear all the input
			if(onOrOff == true){
				pieName = "eight";
			}else{
				pieName = "six";
			}
			db.createTeam(userName, "tester");
			db.CreatePie(userName, pieName, sName, 00000000, 0);
			projView.setText("");
			for (int i = 0; i < skillView.length; i++) {
				skillView[i].setText("");
			}
		}

	}

}
