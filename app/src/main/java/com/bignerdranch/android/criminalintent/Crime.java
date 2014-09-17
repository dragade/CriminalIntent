package com.bignerdranch.android.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

public class Crime {
  private final int mPosition;
  private UUID mId;
  private String mTitle;
  private Date mDate;
  private boolean mSolved;

  public Crime(int position) {
    mPosition = position;
    mId = UUID.randomUUID();
    mDate = new Date();

  }

  public int getPosition() {
    return mPosition;
  }

  public UUID getId() {
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }

  public boolean isSolved() {
    return mSolved;
  }

  public void setSolved(boolean solved) {
    mSolved = solved;
  }

  @Override
  public String toString() {
    return mTitle;
  }
}