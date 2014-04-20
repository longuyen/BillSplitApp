package com.app.billsplitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddFriends extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    List<PaymentInfo> paymentInfo = new ArrayList<PaymentInfo>();
    double total = 100;
    public static String PAYMENT_LIST = "payment_list";
    public static String DELIM = "##";

    // pre-stored list
    static Map<String, String> preStored = new HashMap<String, String>();
    static {
        preStored.put("", "");
        preStored.put("Long", "lnnguyen@amazon.com");
        preStored.put("Chen", "huangche@amazon.com");
        preStored.put("Tony", "tonyhu@amazon.com");
        preStored.put("Yash", "yashcp@amazon.com");
        preStored.put("Min", "heeyoo@amazon.com");
        preStored.put("Rich", "rfp@amazon.com");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        initList();

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            total = b.getDouble(BillSplitCalculatorActivity.EXTRA_BILL_TOTAL);
        }
    }

    public void initList() {
        Spinner dropdown = (Spinner) findViewById(R.id.list);
        List<String> items = new ArrayList<String>();
        items.addAll(preStored.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_split_calculator, menu);
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

    public void calculateBillSplit(View view) {
        Intent intent = new Intent(this, BillSplitCalculatorActivity.class);
        startActivity(intent);
    }

    private String getFriendsStr(List<PaymentInfo> infos) {
        StringBuilder friendsStr = new StringBuilder();
        for (PaymentInfo info : infos) {
            friendsStr.append(info.title).append("  ").append("\n");
        }
        return friendsStr.toString();
    }

    public void storeFriend(View view) {
        EditText nameEdit = (EditText) findViewById(R.id.name);
        String currentName = nameEdit.getText().toString();
        nameEdit.setText("");

        EditText emailEdit = (EditText) findViewById(R.id.email);
        String currentEmail = emailEdit.getText().toString();
        emailEdit.setText("");

        storeInfoAndUpdate(currentName, currentEmail);
    }

    private void storeInfoAndUpdate(String name, String email) {
        PaymentInfo currentInfo = new PaymentInfo();
        currentInfo.title = name;
        currentInfo.email = email;
        paymentInfo.add(currentInfo);

        // reset total
        double perPerson = total / paymentInfo.size();
        for (PaymentInfo info : paymentInfo) {
            info.amount = perPerson;
        }

        EditText nameDisplay = (EditText) findViewById(R.id.nameList);
        nameDisplay.setText(getFriendsStr(paymentInfo));
    }

    // Spinner related
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedName = adapterView.getItemAtPosition(i).toString();
        if (selectedName.equals("")) {
            return;
        }
        storeInfoAndUpdate(selectedName, preStored.get(selectedName));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public String [] generateStrArrayFromPaymentInfo(List<PaymentInfo> paymentInfo) {
        String [] ret = new String[paymentInfo.size()];

        for (int i = 0; i < paymentInfo.size(); ++i) {
            StringBuilder builder = new StringBuilder();
            PaymentInfo info = paymentInfo.get(i);
            builder.append(info.title).append(DELIM).
                    append(info.email).append(DELIM).
                    append(info.amount);
            ret[i] = builder.toString();
        }
        return ret;
    }

    public void sendEmails(View view){
        Intent intent = new Intent(this, SendActivity.class);
        intent.putExtra(PAYMENT_LIST, generateStrArrayFromPaymentInfo(
                getDedupedPaymentInfo(paymentInfo)));
        startActivity(intent);
    }

    private List<PaymentInfo> getDedupedPaymentInfo(List<PaymentInfo> infos) {
        List<PaymentInfo> ret = new ArrayList<PaymentInfo>();
        for (PaymentInfo info : infos) {
            boolean found = false;
            for (PaymentInfo exsitInfo : ret) {
                if (info.title.equals(exsitInfo.title)) {
                    exsitInfo.amount += info.amount;
                    found = true;
                    break;
                }
            }
            if (!found) {
                ret.add(info);
            }
        }
        return ret;
    }
}
