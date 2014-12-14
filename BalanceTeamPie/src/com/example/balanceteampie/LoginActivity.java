package com.example.balanceteampie;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
   /**
    * Keep track of the login task to ensure we can cancel it if requested.
    */
   private UserLoginTask mAuthTask = null;

   // Values for username and password at the time of the login attempt.
   private String username;
   private String password;
   
   private final int MIN_STRING_LENGTH = 4;
   private static ArrayList<String> dummyCredentials = new ArrayList<String>();

   // UI references.
   private EditText userView;
   private EditText passwordView;
   private View mLoginFormView;
   private View mLoginStatusView;
   private TextView mLoginStatusMessageView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_login);
      getActionBar().hide();
      
      dummyCredentials.add("test:test");
      
      // Set up the login form.
      userView = (EditText) findViewById(R.id.username);
      userView.setText(username);
      
      passwordView = (EditText) findViewById(R.id.password);
      passwordView
            .setOnEditorActionListener(new TextView.OnEditorActionListener() {
               @Override
               public boolean onEditorAction(TextView textView, int id,
                     KeyEvent keyEvent) {
                  if (id == R.id.login || id == EditorInfo.IME_NULL) {
                     attemptLogin();
                     return true;
                  }
                  return false;
               }
            });

      mLoginFormView = findViewById(R.id.login_form);
      mLoginStatusView = findViewById(R.id.login_status);
      mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

      findViewById(R.id.sign_in_button).setOnClickListener(
         new View.OnClickListener() {
            public void onClick(View view) {
               attemptLogin();
            }
      });
      
      // Add by MinL 11292014 ->
      findViewById(R.id.link_to_register).setOnClickListener(
         new View.OnClickListener() {
            public void onClick(View view) {
               // go to Registration Page
               Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
               startActivity(i);
            }               
      });
      // End of add 11292014 <-
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      getMenuInflater().inflate(R.menu.login, menu);
      return true;
   }

   /**
    * Attempts to sign in or register the account specified by the login form.
    * If there are form errors (invalid email, missing fields, etc.), the errors
    * are presented and no actual login attempt is made.
    */
   public void attemptLogin() {
      if (mAuthTask != null) {
         return;
      }
      // Reset errors.
      userView.setError(null);
      passwordView.setError(null);

      // Store values at the time of the login attempt.
      username = userView.getText().toString().toLowerCase();
      password = passwordView.getText().toString();

      boolean cancel = false;
      View focusView = null;

      // Check for a valid username.
      if (TextUtils.isEmpty(username)) {
         userView.setError(getString(R.string.error_field_required));
         focusView = userView;
         cancel = true;
      }
      
      // Check for a valid password.
      if (TextUtils.isEmpty(password)) {
         passwordView.setError(getString(R.string.error_field_required));
         focusView = passwordView;
         cancel = true;
      } else if (password.length() < MIN_STRING_LENGTH) {
         passwordView.setError(getString(R.string.error_invalid_password));
         focusView = passwordView;
         cancel = true;
      }

      if (cancel) {
         // There was an error; don't attempt login and focus the first
         // form field with an error.
         focusView.requestFocus();
      } else {
         // Show a progress spinner, and kick off a background task to
         // perform the user login attempt.
         mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
         showProgress(true);
         mAuthTask = new UserLoginTask();
         mAuthTask.execute((Void) null);
      }
   }

   /**
    * Shows the progress UI and hides the login form.
    */
   @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
   private void showProgress(final boolean show) {
      // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
      // for very easy animations. If available, use these APIs to fade-in
      // the progress spinner.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
         int shortAnimTime = getResources().getInteger(
               android.R.integer.config_shortAnimTime);

         mLoginStatusView.setVisibility(View.VISIBLE);
         mLoginStatusView.animate().setDuration(shortAnimTime)
               .alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                     mLoginStatusView.setVisibility(show ? View.VISIBLE
                           : View.GONE);
                  }
               });

         mLoginFormView.setVisibility(View.VISIBLE);
         mLoginFormView.animate().setDuration(shortAnimTime)
               .alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                     mLoginFormView.setVisibility(show ? View.GONE
                           : View.VISIBLE);
                  }
               });
      } else {
         // The ViewPropertyAnimator APIs are not available, so simply show
         // and hide the relevant UI components.
         mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
         mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      }
   }

   /**
    * Represents an asynchronous login/registration task used to authenticate
    * the user.
    */
   public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
      protected Boolean doInBackground(Void... params) {
         try {
            // Simulate network access.
            Thread.sleep(2000);
         } catch (InterruptedException e) {
            return false;
         }
         
         for (String credential : dummyCredentials) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(username)) {
               // Account exists, return true if the password matches.
               if (pieces[1].equals(password))
                  return true;
            }
         }
         
         Database db = new Database();
//         if (db.getUser(username) != null) {
            // Account exists, return true if the password matches.
            return db.getUserPassword(username).equals(Database.MD5(password));
//         }

      }

      protected void onPostExecute(final Boolean success) {
         mAuthTask = null;
         showProgress(false);
         
         if (success) {
            // Go on to the next activity
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainMenu.class);
            User myUser = new User(username, password, "", "", "");
            intent.putExtra("USER_INFO", myUser);
            startActivity(intent);
            LoginActivity.this.finish();
         } else {
            passwordView
                  .setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
         }
      }

      @Override
      protected void onCancelled() {
         mAuthTask = null;
         showProgress(false);
      }
   }
}
