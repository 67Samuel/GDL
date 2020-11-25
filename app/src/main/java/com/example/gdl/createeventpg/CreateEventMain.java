package com.example.gdl.createeventpg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.gdl.ActivityWithMenu;
import com.example.gdl.R;
import com.example.gdl.models.Member;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateEventMain extends ActivityWithMenu implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CreateEventMain";

    //vars
    private final String sharedPrefFile = "com.example.gdl.createActivityMainSP";
    public static final String EVENT_NAME_KEY = "event name key";
    public static final String EVENT_DATE_KEY = "event date key";
    private ArrayList<Member> mSelectedMembersList = new ArrayList<>();
    SharedPreferences mPreferences;

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
        setContentView(R.layout.activity_create_event_main);

        //set actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Event");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //find all views
        //TODO: make edit texts not able to press enter, make date a selection
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

        //get intent from CreateEventSelectedMembers
        Intent selectedMembersIntent = getIntent();
        mSelectedMembersList = selectedMembersIntent.getParcelableArrayListExtra(CreateEventSelectMembers.SELECTED_MEMBERS_KEY);
        //sets number for mNumberMembersSelected, try catch block for case where mSelectedMembersList == 0
        try {
            String formattedNumberSelected = getString(R.string.numberSelected, mSelectedMembersList.size());
            mNumberMembersSelected.setText(formattedNumberSelected);
            Log.d(TAG, "onCreate: mSelectedMembersList=" + mSelectedMembersList);
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
                //TODO: save shared preferences before going to SelectMembers
                saveSharedPreferences();
                //TODO: go to CreateEventSelectMembers
                Intent selectMembersIntent = new Intent(this, CreateEventSelectMembers.class);
                Log.d(TAG, "onClick: triggered");
                startActivity(selectMembersIntent);
                break;
            case R.id.add_photo_text_view:
                Toast.makeText(this, "implicit intent to photo gallery", Toast.LENGTH_SHORT).show();
                break;
                //TODO: save shared preferences before going to photo gallery
                //TODO: implicit intent to photo gallery to get photo
            case R.id.create_event_button:
                Toast.makeText(this, "go to MyEvents", Toast.LENGTH_SHORT).show();
                break;
                //TODO: check if there are any members in the event, if not then toast and return
                //TODO: if there are, create the specified Event object
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

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }
}