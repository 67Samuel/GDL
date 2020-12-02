package com.example.gdl.createeventpg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

import com.example.gdl.GDLActivity;
import com.example.gdl.R;
import com.example.gdl.SignInActivity;
import com.example.gdl.models.Event;
import com.example.gdl.models.Member;
import com.google.firebase.firestore.DocumentReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class CreateEventMain extends GDLActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CreateEventMain";

    //vars
    private static final String sharedPrefFile = "com.example.gdl.createActivityMainSP";
    public static final String EVENT_NAME_KEY = "event name key";
    public static final String EVENT_DATE_KEY = "event date key";
    private ArrayList<String> mSelectedMembersIdList = new ArrayList<>();
    SharedPreferences mPreferences;
    Uri event_pic_uri;
    Map<String, Object> eventInfo;


    //UI components
    EditText mEventNameEditText;
    TextView mEventDateTextView;
    TextView mSelectMembersTextView;
    TextView mNumberMembersSelected;
    TextView mAddPhotoTextView;
    Button mCreateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.create_event_main);


        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Event");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //find all views
        mEventNameEditText = findViewById(R.id.eventNameEditText);
        mEventDateTextView = findViewById(R.id.event_date_text_view);
        mSelectMembersTextView = findViewById(R.id.select_members_text_view);
        mNumberMembersSelected = findViewById(R.id.number_members_selected);
        mAddPhotoTextView = findViewById(R.id.add_photo_text_view);
        mCreateButton = findViewById(R.id.create_event_button);

        setListeners();

        //retrieve shared preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEventNameEditText.setText(mPreferences.getString(EVENT_NAME_KEY, ""));
        mEventDateTextView.setText(mPreferences.getString(EVENT_DATE_KEY, ""));

        //get intent from CreateEventSelectedMembers and update selected members list to be sent to db
        Intent selectedMembersIntent = getIntent();
        mSelectedMembersIdList = selectedMembersIntent.getStringArrayListExtra(CreateEventSelectMembers.SELECTED_MEMBERS_ID_KEY);
        eventInfo.put("selected members list", mSelectedMembersIdList);
        //set number for mNumberMembersSelected, try catch block for case where mSelectedMembersList == 0
        try {
            String formattedNumberSelected = getString(R.string.numberSelected, mSelectedMembersIdList.size());
            mNumberMembersSelected.setText(formattedNumberSelected);
            Log.d(TAG, "onCreate: mSelectedMembersList=" + mSelectedMembersIdList);
        } catch (Exception e) {
            String formattedNumberSelected = getString(R.string.numberSelected, 0);
            mNumberMembersSelected.setText(formattedNumberSelected);
            Log.d(TAG, "onCreate: "+e);
        }

    }

    private void setListeners() {
        mSelectMembersTextView.setOnClickListener(this);
        mAddPhotoTextView.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);
        mEventDateTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_members_text_view:
                Intent selectMembersIntent = new Intent(this, CreateEventSelectMembers.class);
                Log.d(TAG, "onClick: triggered");
                startActivity(selectMembersIntent);
                break;
            case R.id.add_photo_text_view:
                Log.d(TAG, "onClick: getting event pic from crop activity");
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(CreateEventMain.this);
                break;
                //TODO: implicit intent to photo gallery to get photo
            case R.id.create_event_button:
                if (mSelectedMembersIdList.isEmpty()) {
                    Toast.makeText(this, "An event must have members!", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: store all info in db
                    DocumentReference eventRef = db.collection("Events").document(); //creates a doc with unique ID
                    String eventId = eventRef.getId();
                    Event event = new Event(eventId, mEventNameEditText.getText().toString(), mSelectedMembersIdList, mEventDateTextView.getText().toString());
                    
                    //TODO: go to add bills page
                }
                break;
                //TODO: if there are, create the specified Event object with name, date, members id and photo
                //TODO: go to MyEvents (should auto update the list within MyEvents)
            case R.id.event_date_text_view:
                DialogFragment datePicker = new com.example.gdl.createeventpg.DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
        }
    }

    private void saveSharedPreferences() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(EVENT_NAME_KEY, mEventNameEditText.getText().toString());
        preferencesEditor.putString(EVENT_DATE_KEY, mEventDateTextView.getText().toString());
        preferencesEditor.apply();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Log.d(TAG, "onDateSet: year"+year);
        Log.d(TAG, "onDateSet: month"+month);
        Log.d(TAG, "onDateSet: day"+dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        mEventDateTextView.setText(currentDateString);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                event_pic_uri = result.getUri();
                Log.d(TAG, "onActivityResult: photo uri obtained");
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPreferences();
    }
}