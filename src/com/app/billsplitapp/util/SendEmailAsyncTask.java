package com.app.billsplitapp.util;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import android.os.AsyncTask;
import android.util.Log;

import com.app.billsplitapp.BuildConfig;
import com.app.billsplitapp.Mail;


public class SendEmailAsyncTask extends AsyncTask <Void, Void, Boolean> {
	String _fromEmailAccount = "amazonpaymentsfake@gmail.com";
	String _accountPassword = "4yourionly";
	
	Mail m = new Mail(_fromEmailAccount, _accountPassword);

	public SendEmailAsyncTask(String[] toAddress, String emailBody) {
		if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");
		m.setTo(toAddress);
		m.setFrom(_fromEmailAccount);
		m.setSubject("You have received a payment request!");
		m.setBody(emailBody);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
		try {
			m.send();
			return true;
		} catch (AuthenticationFailedException e) {
			Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			Log.e(SendEmailAsyncTask.class.getName(), m.getTo() + "failed");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}