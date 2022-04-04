package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Bundle;


public class ContactsManagerActivity extends AppCompatActivity {

    private TextView nameEditText;
    private TextView phoneEditText;
    private TextView emailEditText;
    private TextView addressEditText;
    private TextView jobTitleEditText;
    private TextView companyEditText;
    private TextView websiteEditText;
    private TextView imEditText;

    private Button showHideAdditionalFieldsButton;
    private Button saveButton;
    private Button cancelButton;
    private LinearLayout additionalFieldsContainer;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();


    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button2:
                    switch (additionalFieldsContainer.getVisibility()) {
                        case View.VISIBLE:
                            showHideAdditionalFieldsButton.setText("Show additional fields");
                            additionalFieldsContainer.setVisibility(View.INVISIBLE);
                            break;
                        case View.INVISIBLE:
                            showHideAdditionalFieldsButton.setText("Hide additional fields)");
                            additionalFieldsContainer.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case R.id.button3:
                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String jobTitle = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, 2017);
                    break;
                case R.id.button4:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = (TextView)findViewById(R.id.textView);
        phoneEditText = (TextView)findViewById(R.id.textView2);
        emailEditText = (TextView)findViewById(R.id.textView3);
        addressEditText = (TextView)findViewById(R.id.textView4);
        jobTitleEditText = (TextView)findViewById(R.id.textView5);
        companyEditText = (TextView)findViewById(R.id.textView6);
        websiteEditText = (TextView)findViewById(R.id.textView7);
        imEditText = (TextView)findViewById(R.id.textView8);

        showHideAdditionalFieldsButton = (Button)findViewById(R.id.button2);
        showHideAdditionalFieldsButton.setOnClickListener(buttonClickListener);
        saveButton = (Button)findViewById(R.id.button3);
        saveButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.button4);
        cancelButton.setOnClickListener(buttonClickListener);

        additionalFieldsContainer = (LinearLayout)findViewById(R.id.additional);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 2017) {
            setResult(resultCode, new Intent());
            finish();
        }
    }
}