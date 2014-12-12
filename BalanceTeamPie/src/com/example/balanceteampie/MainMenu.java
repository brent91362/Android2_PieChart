package com.example.balanceteampie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button btnCreate = (Button) findViewById(R.id.createTeam);
	      btnCreate.setOnClickListener(new OnClickListener(){
	    	  public void onClick(View v) {
	    		  Intent intent = new Intent(v.getContext(), Manager.class);
	    		  startActivityForResult(intent, 0);
	    	  }
	      });
		
	      Button btnSelect = (Button) findViewById(R.id.selectTeam);
	      btnSelect.setOnClickListener(new OnClickListener(){
	    	  public void onClick(View v) {
	    		  Intent intent = new Intent(v.getContext(), Manager.class);
	    		  startActivityForResult(intent, 0);
	    	  }
	      });
	      
	      Button btnManage = (Button) findViewById(R.id.manageTeam);
	      btnManage.setOnClickListener(new OnClickListener(){
	    	  public void onClick(View v) {
	    		  Intent intent = new Intent(v.getContext(), Manager.class);
	    		  startActivityForResult(intent, 0);
	    	  }
	      });
		
		
	      Button btnJoin = (Button) findViewById(R.id.joinTeam);
	      btnJoin.setOnClickListener(new OnClickListener(){
	    	  public void onClick(View v) {
	    		  Intent intent = new Intent(v.getContext(), Manager.class);
	    		  startActivityForResult(intent, 0);
	    	  }
	      });
	      
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
