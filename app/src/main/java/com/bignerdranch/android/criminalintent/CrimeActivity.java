package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

  private static final String TAG = CrimeActivity.class.getSimpleName();

  @Override
  protected Fragment createFragment() {
    UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
    return CrimeFragment.newInstance(crimeId);  }
}