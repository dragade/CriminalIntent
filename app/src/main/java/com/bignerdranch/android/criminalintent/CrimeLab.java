package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CrimeLab {
  private ArrayList<Crime> mCrimes;
  private static CrimeLab sCrimeLab;
  private Context mAppContext;

  private CrimeLab(Context appContext) {
    mAppContext = appContext;
    mCrimes = new ArrayList<Crime>();
    for (int i=0; i < 10; i++) {
      Crime c = new Crime();
      c.setSolved(i%2==0);
      c.setTitle("Crime-" + i);
      mCrimes.add(c);
    }
  }

  public static CrimeLab get(Context c) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(c.getApplicationContext());
    }
    return sCrimeLab;
  }

  public void addCrime(Crime c) {
    mCrimes.add(c);
  }

  public Crime getCrime(UUID id) {
    for (Crime c : mCrimes) {
      if (c.getId().equals(id)) {
        return c;
      }
    }
    return null;
  }

  public int getPosition(UUID id) {
    for (int i=0; i < mCrimes.size(); i++) {
      Crime c = mCrimes.get(i);
      if (c.getId().equals(id)) {
        return i;
      }
    }
    return -1;
  }

  public ArrayList<Crime> getCrimes() {
    return mCrimes;
  }

  public void deleteCrime(Crime c) {
    mCrimes.remove(c);
  }
}