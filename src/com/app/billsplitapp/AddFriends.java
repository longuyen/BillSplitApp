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

    // pre-stored list
    static Map<String, String> preStored = new HashMap<String, String>();
    static {
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
        int peopleCount = infos.size();
        double perPerson  = 0;
        if (peopleCount > 0) {
            perPerson = total / peopleCount;
        }
        String perPersonStr = String.format("%.2f", perPerson);

        for (PaymentInfo info : infos) {
            info.amount = perPerson;
            friendsStr.append(info.title).append("  ").append(perPersonStr).append("\n");
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

        EditText nameDisplay = (EditText) findViewById(R.id.nameList);
        nameDisplay.setText(getFriendsStr(paymentInfo));
    }

    // Spinner related
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedName = adapterView.getItemAtPosition(i).toString();
        storeInfoAndUpdate(selectedName, preStored.get(selectedName));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
