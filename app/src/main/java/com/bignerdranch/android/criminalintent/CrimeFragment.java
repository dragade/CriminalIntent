package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
  private static final String TAG = CrimeFragment.class.getSimpleName();

  private Crime mCrime;
  private EditText mTitleField;
  private Button mDateButton;
  private CheckBox mSolvedCheckBox;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mCrime = new Crime();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {
    Log.d(TAG,"onCreateView() called with state " + savedInstanceState);

    View v = inflater.inflate(R.layout.fragment_crime, parent, false);
    mTitleField = (EditText) v.findViewById(R.id.crime_title);
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
    mDateButton.setText(mCrime.getDate().toString());
    mDateButton.setEnabled(false);

    mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved_checkbox);
    mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mCrime.setSolved(isChecked);
      }
    });

    return v;
  }
}
