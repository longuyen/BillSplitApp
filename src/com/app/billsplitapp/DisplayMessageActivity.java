package com.app.billsplitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.billsplitapp.util.SendEmailAsyncTask;

public class DisplayMessageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get the message from the intent
		Intent intent = getIntent();
		Double total = intent.getDoubleExtra(BillSplitCalculatorActivity.EXTRA_BILL_TOTAL, 0.0);
		//String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		String message = total.toString();
		
		//Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(message);
		
		// Set the text view as the activ layout
		setContentView(textView);
		
        try {   
        	String[] toAddress = {"hoanglong@gmail.com"};	
        	String emailBody = "<html><p>Greetings from fake Amazon Payments!</p><p>You have received a payment request, please click on the button to pay:</p><a href= \"https://payments.amazon.com/sdui/sdui/paymentsend?&targetAccount=hoanglong%40gmail.com&amount=11.44&note=blah\"><img src=\"https://images-na.ssl-images-amazon.com/images/G/01/EP/offAmazonPayments/us/live/devo/image/pwa/orange/large/dark/button.png\" border=\"0\"/></a><p>Thank you for using fake Amazon Payments!</p></html>";
        	new SendEmailAsyncTask(toAddress, emailBody ).execute();
        } catch (Exception e) {   
            System.out.println(e);   
        } 
		
/*		setContentView(R.layout.activity_display_message);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
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
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
}
