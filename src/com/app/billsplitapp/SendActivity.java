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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SendActivity extends ActionBarActivity {

    List<PaymentInfo> paymentInfos = new ArrayList<PaymentInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_send);

		Intent intent = getIntent();
        String [] paymentStrings = intent.getExtras().getStringArray(AddFriends.PAYMENT_LIST);
        getPaymentInfos(paymentStrings);

        populatePaymentSummary();

		
		//Dynamically generate layout
		/*ScrollView contactsView = new ScrollView(this);
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
		this.setContentView(contactsView);  */

	}

    private void getPaymentInfos(String [] paymentInfoArray) {
        paymentInfos = new ArrayList<PaymentInfo>();
        for (int i = 0; i < paymentInfoArray.length; ++i) {
            paymentInfos.add(getPaymentInfoFromStr(paymentInfoArray[i]));
        }
    }

    private PaymentInfo getPaymentInfoFromStr(String infoStr) {
        String [] parts = infoStr.split(AddFriends.DELIM);
        PaymentInfo ret = new PaymentInfo();

        ret.title = parts[0];
        ret.email = parts[1];
        ret.amount = Double.parseDouble(parts[2]);
        return ret;
    }

    private String generateSummaryStr() {
        StringBuilder builder = new StringBuilder();
        for (PaymentInfo info : paymentInfos) {
            String formattedAmount = String.format("%.2f", info.amount);
            builder.append(info.title).append("\t").
                    append(formattedAmount).append("\n");
        }
        return builder.toString();
    }

    private void populatePaymentSummary() {
        EditText nameDisplay = (EditText) findViewById(R.id.nameList);
        nameDisplay.setText(generateSummaryStr());
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
}
