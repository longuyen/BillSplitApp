package com.app.billsplitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class SendActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		//TODO - get contacts list from Bundle B
		
		//Dynamically generate layout
		ScrollView contactsView = new ScrollView(this);
		LinearLayout sendActivityLayout = new LinearLayout(this);
		sendActivityLayout.setOrientation(LinearLayout.VERTICAL);
		contactsView.addView(sendActivityLayout);
		
		TextView sampleContact = new TextView(this);
		sampleContact.setText(R.string.list_of_friends);
		contactsView.addView(sampleContact);
		
		//TODO - loop through contacts and add TextView elements dynamically here
				
		
		//Add button to confirm the split payment
		Button confirmButton = new Button(this);
		confirmButton.setText(R.string.confirm_split_button);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick (View v) {
				//TODO - process the split payment
			}
		});
		//add Confirm button to scroll view
		contactsView.addView(confirmButton);
			
		//set the view
		this.setContentView(contactsView);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send, menu);
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

	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_activity_send,
					container, false);
			return rootView;
		}
	}
	

}
