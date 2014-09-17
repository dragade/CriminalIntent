package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
  private static final String TAG = CrimeFragment.class.getSimpleName();
  private static final String DIALOG_DATE = "date";
  private static final String DIALOG_TIME = "time";
  private static final int REQUEST_DATE = 0;
  private static final int REQUEST_TIME = 1;

  public static final String EXTRA_CRIME_ID =
      "com.bignerdranch.android.criminalintent.crime_id";

  private static final String DATE_FORMAT_STRING = "EEEE, MMM d, yyyy";
  private static final String TIME_FORMAT_STRING = "HH:mm";

  private Crime mCrime;
  private EditText mTitleField;
  private Button mDateButton;
  private Button mTimeButton;
  private CheckBox mSolvedCheckBox;

  public static CrimeFragment newInstance(UUID crimeId) {
    Bundle args = new Bundle();
    args.putSerializable(EXTRA_CRIME_ID, crimeId);
    CrimeFragment fragment = new CrimeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
    mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    setHasOptionsMenu(true);
  }

  @TargetApi(11)
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    Log.d(TAG,"onCreateView() called with state " + savedInstanceState);

    View v = inflater.inflate(R.layout.fragment_crime, parent, false);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      if (NavUtils.getParentActivityName(getActivity()) != null) {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
      }
    }

    mTitleField = (EditText) v.findViewById(R.id.crime_title);
    mTitleField.setText(mCrime.getTitle());
    mTitleField.addTextChangedListener(new TextWatcher() {
      public void onTextChanged(
          CharSequence c, int start, int before, int count) {
        mCrime.setTitle(c.toString());
      }

      public void beforeTextChanged(
          CharSequence c, int start, int count, int after) {
        // This space intentionally left blank
      }

      public void afterTextChanged(Editable c) {
        // This one too
      }
    });

    mDateButton = (Button) v.findViewById(R.id.crime_date_button);
    updateDate();

    mTimeButton = (Button) v.findViewById(R.id.crime_time_button);
    updateTime();

    mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved_checkbox);
    mSolvedCheckBox.setChecked(mCrime.isSolved());
    mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mCrime.setSolved(isChecked);
      }
    });

    return v;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) return;

    if (requestCode == REQUEST_DATE || requestCode == REQUEST_TIME) {
      String extra = (requestCode == REQUEST_DATE) ? DatePickerFragment.EXTRA_DATE : TimePickerFragment.EXTRA_TIME;
      long date = (Long)data.getSerializableExtra(extra);
      mCrime.setDate(new Date(date));
      updateDate();
      updateTime();
    }
  }

  private void updateDate() {
    mDateButton.setText(DateFormat.format(DATE_FORMAT_STRING, mCrime.getDate()));
    mDateButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
        dialog.show(fm, DIALOG_DATE);
      }
    });
  }

  private void updateTime() {
    mTimeButton.setText(DateFormat.format(TIME_FORMAT_STRING, mCrime.getDate()));
    mTimeButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
        dialog.show(fm, DIALOG_TIME);
      }
    });
  }

  @TargetApi(11)
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        if (NavUtils.getParentActivityName(getActivity()) != null) {
          NavUtils.navigateUpFromSameTask(getActivity());
        }
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

}
