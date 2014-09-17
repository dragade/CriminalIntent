package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CrimeLab {
  private LinkedHashMap<UUID, Crime> mCrimesMap;
  private static CrimeLab sCrimeLab;
  private Context mAppContext;

  private CrimeLab(Context appContext) {
    mAppContext = appContext;
    mCrimesMap = new LinkedHashMap<UUID, Crime>();
    for (int i = 0; i < 100; i++) {
      Crime c = new Crime(i);
      c.setTitle("Crime #" + i);
      c.setSolved(i % 2 == 0); // Every other one
      mCrimesMap.put(c.getId(), c);
    }
  }

  public static CrimeLab get(Context c) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(c.getApplicationContext());
    }
    return sCrimeLab;
  }

  public Crime getCrime(UUID id) {
    return mCrimesMap.get(id);
  }

  public ArrayList<Crime> getCrimes() {
    return new ArrayList<Crime>(mCrimesMap.values());
  }
}