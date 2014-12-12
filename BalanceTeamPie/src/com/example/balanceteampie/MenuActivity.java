/**
 * Created by MinL 12052014
 * The Activity has four tabs for Create/Select/Manage/Join Team.
 */
package com.example.balanceteampie;

import java.util.Locale;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MenuActivity extends FragmentActivity implements
      ActionBar.TabListener {

   SectionsPagerAdapter mSectionsPagerAdapter;
   ViewPager mViewPager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_menu);

      final ActionBar actionBar = getActionBar();
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

      mSectionsPagerAdapter = new SectionsPagerAdapter(
            getSupportFragmentManager());

      mViewPager = (ViewPager) findViewById(R.id.pager);
      mViewPager.setAdapter(mSectionsPagerAdapter);
      mViewPager
            .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
               @Override
               public void onPageSelected(int position) {
                  actionBar.setSelectedNavigationItem(position);
               }
            });

      for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
         actionBar.addTab(actionBar.newTab()
               .setText(mSectionsPagerAdapter.getPageTitle(i))
               .setTabListener(this));
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu, menu);
      return true;
   }

   @Override
   public void onTabSelected(ActionBar.Tab tab,
         FragmentTransaction fragmentTransaction) {
      // When the given tab is selected, switch to the corresponding page in
      // the ViewPager.
      mViewPager.setCurrentItem(tab.getPosition());
   }

   @Override
   public void onTabUnselected(ActionBar.Tab tab,
         FragmentTransaction fragmentTransaction) {
   }

   @Override
   public void onTabReselected(ActionBar.Tab tab,
         FragmentTransaction fragmentTransaction) {
   }

   public class SectionsPagerAdapter extends FragmentPagerAdapter {

      public SectionsPagerAdapter(FragmentManager fm) {
         super(fm);
      }

      @Override
      public Fragment getItem(int position) {
         Fragment fragment = new Fragment();
         Bundle args = new Bundle();
         args.putInt(ListFragment.ARG_SECTION_NUMBER, position + 1);
         
         switch(position + 1) {
         case 1:
            fragment = new CreateFragment();
            break;
         case 2:
            fragment = new ListFragment();
            break;
         case 3:
            fragment = new JoinFragment();
            break;
         }
         fragment.setArguments(args);
         return fragment;
      }

      @Override
      public int getCount() {
         // Show 3 total pages.
         return 3;
      }

      @Override
      public CharSequence getPageTitle(int position) {
         Locale l = Locale.getDefault();
         switch (position + 1) {
         case 1:
            return getString(R.string.title_fragment_create).toUpperCase(l);
         case 2:
            return getString(R.string.title_fragment_manage).toUpperCase(l);
         case 3:
            return getString(R.string.title_fragment_join).toUpperCase(l);
         }
         return null;
      }
   }

   /**
    * Create Team Tab
    * @author MinL
    */
   public static class CreateFragment extends Fragment implements OnClickListener{
      public static final int MAX_SKILLS = 8;
      private EditText projView;
      private EditText[] skillView = new EditText[MAX_SKILLS];
      
      public CreateFragment() { }

      public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_menu_create,
               container, false);
                
         projView = (EditText) rootView.findViewById(R.id.project_name);
         for(int i = 0; i < skillView.length; i++) {
            skillView[i] = (EditText) rootView.findViewById(R.id.skill_name1 + i);
         }
         
         rootView.findViewById(R.id.button_create).setOnClickListener(this);

         return rootView;
      }

      /**
       * Respond to click event
       * btnCreate : validate all input fields and setup new project
       */
      public void onClick(View v) {
         // reset errors
         projView.setError(null);
         skillView[0].setError(null);
         
         boolean cancel = false;
         View focusView = null;
         
         String pName;
         String[] sName = new String[MAX_SKILLS];
         pName = projView.getText().toString();
         for(int i = 0; i < skillView.length; i++)
            sName[i] = skillView[i].getText().toString();
         
         // Validate the project name
         if(TextUtils.isEmpty(pName)) {
            projView.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = projView;
         }
         
         // Validate the first skill name
         if(TextUtils.isEmpty(sName[0])) {
            skillView[0].setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = skillView[0];
         }         
         
         if(cancel)
            focusView.requestFocus();
         else {
            // TODO: Send to DB
            // Clear all the input
            projView.setText("");
            for(int i = 0; i < skillView.length; i++) {
               skillView[i].setText("");
            }            
         }            
      }
   }
   
   /**
    * Team Lists for both Select Team Tab and Manage Team Tab
    * @author MinL
    */
   public static class ListFragment extends Fragment {
      public static final String ARG_SECTION_NUMBER = "section_number";
      private ListView list;
      private String[] teamList;
      private String[] selectTeam = {"Team 1", "Team 2", "Team 3", "Team 4",
            "Team 5", "Team 6", "Team 7", "Team 8","Team 9", 
            "Team 10"};
      private String[] manageTeam = {"Member 1", "Member 2", "Member 3", "Member 4",
            "Member 5", "Member 6", "Member 7", "Member 8","Member 9", 
            "Member 10"};
      
      public ListFragment() {}
      
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.activity_project,
               container, false);

         // Get different list
         switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
         case 2:
            teamList = manageTeam;
            break;
         }
         list = (ListView) rootView.findViewById(R.id.ProjectlistView);

         populateListView();
         registerClickCallback();

         return rootView;
      }
      
      private void populateListView() {
         // Build Adapter
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(
               getActivity(), 
               R.layout.activity_project_list,
               teamList);
         
         // Configure list view
         list.setAdapter(adapter);     
      }
      
      private void registerClickCallback() {
         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {
               Toast.makeText(getActivity(), "Clicked " + teamList[position],
                     Toast.LENGTH_SHORT).show(); 
            }
         });
      
      }      
   }
   
   /**
    * Join Team Tab
    * @author MinL
    */
   public static class JoinFragment extends Fragment implements OnClickListener{
      private EditText teamView;
      public JoinFragment() {}
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_menu_join,
               container, false);
         
         teamView = (EditText) rootView.findViewById(R.id.join_team_id);
         
         rootView.findViewById(R.id.button_join).setOnClickListener(this);
         
         return rootView;
      }
      
      /**
       * Respond to click event
       * btnJoin : validate all input fields and join team
       */
      public void onClick(View v) {
         teamView.setError(null);
         
         String teamId = teamView.getText().toString();
         if(TextUtils.isEmpty(teamId)) {
            teamView.setError(getString(R.string.error_field_required));
            teamView.requestFocus();
         }
         else {
            teamView.setText("");
            // TODO: send to DB
         }
      }      
   }
}
