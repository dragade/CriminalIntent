package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
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
  private static final int REQUEST_DATE = 0;

  public static final String EXTRA_CRIME_ID =
      "com.bignerdranch.android.criminalintent.crime_id";

  private Crime mCrime;
  private EditText mTitleField;
  private Button mDateButton;
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
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    Log.d(TAG,"onCreateView() called with state " + savedInstanceState);

    View v = inflater.inflate(R.layout.fragment_crime, parent, false);
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
    mDateButton.setText(formatDate(mCrime.getDate()));
    mDateButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
        dialog.show(fm, DIALOG_DATE);
      } });

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

  private String formatDate(Date d) {
    CharSequence format = DateFormat.format("EEEE, MMM d, yyyy", d);
    return format.toString();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) return;
    if (requestCode == REQUEST_DATE) {
      Long date = (Long)data
          .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
      mCrime.setDate(new Date(date));
      mDateButton.setText(mCrime.getDate().toString());
    }
  }

}
