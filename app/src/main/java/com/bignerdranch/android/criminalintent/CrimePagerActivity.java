package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
  private ViewPager mViewPager;
  private ArrayList<Crime> mCrimes;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewPager = new ViewPager(this);
    mViewPager.setId(R.id.viewPager);
    setContentView(mViewPager);

    CrimeLab crimeLab = CrimeLab.get(this);
    mCrimes = crimeLab.getCrimes();

    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int position) {
        Crime crime = mCrimes.get(position);
        return CrimeFragment.newInstance(crime.getId());
      }

      @Override
      public int getCount() {
        return mCrimes.size();
      }
    });

    UUID crimeId = (UUID)getIntent()
        .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
    Crime currentCrime = crimeLab.getCrime(crimeId);
    mViewPager.setCurrentItem(currentCrime.getPosition());

    mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int i, float v, int i2) {

      }

      @Override
      public void onPageSelected(int pos) {
        Crime crime = mCrimes.get(pos);
        if (crime.getTitle() != null) {
          setTitle(crime.getTitle());
        }
      }

      @Override
      public void onPageScrollStateChanged(int i) {

      }
    });

  }
}