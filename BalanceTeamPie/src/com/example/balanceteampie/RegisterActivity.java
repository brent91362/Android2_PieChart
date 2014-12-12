/**
 * Create by MinL 11292014
 * This activity is for new user registration.
 * The simple input form has fields including First Name, Last Name, E-mail,
 * Username and Password. All fields are required.
 */
package com.example.balanceteampie;

import com.example.balanceteampie.LoginActivity.UserLoginTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

public class RegisterActivity extends Activity implements OnClickListener{
   private final int MIN_STRING_LENGTH = 4;
   String username, password, fname, lname, email;
   Database db = new Database();
   private UserLoginTask mAuthTask = null;
   
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);
      
      findViewById(R.id.btnRegister).setOnClickListener(this);
      findViewById(R.id.link_to_login).setOnClickListener(this);
   }
   
   /**
    * Respond to click event
    * btnRegister : validate all input fields and setup new user account
    * link_to_login : finish the activity and go back to log in page
    */
   public void onClick(View v) {
      if (v.getId() == R.id.btnRegister) {
         TextView userView = (TextView) findViewById(R.id.input_username);
         TextView passView = (TextView) findViewById(R.id.input_password);
         TextView fnameView = (TextView) findViewById(R.id.input_first_name);
         TextView lnameView = (TextView) findViewById(R.id.input_last_name);
         TextView emailView = (TextView) findViewById(R.id.input_email);
         
         // reset errors.
         userView.setError(null);
         passView.setError(null);
         fnameView.setError(null);
         lnameView.setError(null);
         emailView.setError(null);
         
         boolean cancel = false;
         View focusView = null;
         
         username = userView.getText().toString().toLowerCase();
         password = passView.getText().toString();
         fname = fnameView.getText().toString();
         lname = lnameView.getText().toString();
         email = emailView.getText().toString();
         
         
         
         // Validate all the input fields...
         if (TextUtils.isEmpty(username)) {
            userView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = userView;
         } else if (username.length() < MIN_STRING_LENGTH) {
            userView.setError(getString(R.string.error_invalid_username));
            cancel = true;
            focusView = userView;
         } else if (db.getUser(username) != null) {
            userView.setError(getString(R.string.error_duplicate_username));
            cancel = true;
            focusView = userView;
         }
         
         if (TextUtils.isEmpty(password)) {
            passView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = passView;
         } else if (password.length() < MIN_STRING_LENGTH) {
            passView.setError(getString(R.string.error_invalid_password));
            cancel = true;
            focusView = passView;
         }
         
         if (TextUtils.isEmpty(fname)) {
            fnameView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = fnameView;
         }
         
         if (TextUtils.isEmpty(lname)) {
            lnameView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = lnameView;
         }
         
         if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = emailView;
         } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailView.setError(getString(R.string.error_invalid_email));
            cancel = true;
            focusView = emailView;
         }
         
         if (cancel) {
            focusView.requestFocus();
         } else {
//            db.createUser(username, password, fname, lname, email);
//            String s = "User has been successfully created.";
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//            finish();
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
         }         
      }
      if (v.getId() == R.id.link_to_login)
         finish();
   }
   
   public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
      protected Boolean doInBackground(Void... params) {
         db.createUser(username, password, fname, lname, email);
         String s = "User has been successfully created.";
         Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
         finish();
         return true;
      }

      protected void onPostExecute(final Boolean success) {
         if(success) {
            
         }
      }

      @Override
      protected void onCancelled() {
         
      }
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.register, menu);
      return true;
   }
}
