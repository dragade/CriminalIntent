package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

  private static final String TAG = CrimeActivity.class.getSimpleName();

  @Override
  protected Fragment createFragment() {
    return new CrimeFragment();
  }
}