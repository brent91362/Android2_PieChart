package com.example.balanceteampie;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenu extends Activity implements OnClickListener {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_menu);
      
      findViewById(R.id.btnCreate).setOnClickListener(this);
      findViewById(R.id.btnJoin).setOnClickListener(this);
      findViewById(R.id.btnView).setOnClickListener(this);
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
      switch(v.getId()) {
      case R.id.btnCreate:
         intent.setClass(MainMenu.this, CreateActivity.class);
         break;
      case R.id.btnJoin:
         intent.setClass(MainMenu.this, JoinActivity.class);
         break;
      case R.id.btnView:
         intent.setClass(MainMenu.this, MainActivity.class);
         User myUser = (User) getIntent().getParcelableExtra("USER_INFO");
         intent.putExtra("USER_INFO", myUser);
         break;
      }
      startActivity(intent);
      MainMenu.this.finish();      
   }

}
