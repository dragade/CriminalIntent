package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

  public static final String EXTRA_DATE =
      "com.bignerdranch.android.criminalintent.date";

  private Date mDate;

  private void sendResult(int resultCode) {
    if (getTargetFragment() == null)
      return;
    Intent i = new Intent();
    i.putExtra(EXTRA_DATE, mDate.getTime());
    getTargetFragment()
        .onActivityResult(getTargetRequestCode(), resultCode, i);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    long date = getArguments().getLong(EXTRA_DATE);
    mDate = new Date(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(mDate);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    final int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    final int min = calendar.get(Calendar.MINUTE);

    View v = getActivity().getLayoutInflater()
        .inflate(R.layout.dialog_date, null);

    DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
    datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
      public void onDateChanged(DatePicker view, int year, int month, int day) {
        // Translate year, month, day into a Date object using a calendar
        mDate = new GregorianCalendar(year, month, day, hourOfDay, min).getTime();
        // Update argument to preserve selected value on rotation
        getArguments().putLong(EXTRA_DATE, mDate.getTime());
      }
    });

    return new AlertDialog.Builder(getActivity())
        .setView(v)
        .setTitle(R.string.date_picker_title)
        .setPositiveButton(
            android.R.string.ok,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
              }
            })
        .create();
  }

  public static DatePickerFragment newInstance(Date d) {
    Bundle b = new Bundle();
    b.putLong(EXTRA_DATE, d.getTime());
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    datePickerFragment.setArguments(b);
    return datePickerFragment;
  }
}
