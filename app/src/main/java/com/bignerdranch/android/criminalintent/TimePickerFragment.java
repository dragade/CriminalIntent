package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

  public static final String EXTRA_TIME =
      "com.bignerdranch.android.criminalintent.time";

  private Date mDate;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    long date = getArguments().getLong(EXTRA_TIME);
    mDate = new Date(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(mDate);
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);
    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);

    View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

    TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
    timePicker.setIs24HourView(true);
    timePicker.setCurrentHour(hourOfDay);
    timePicker.setCurrentMinute(min);
    timePicker.setOnTimeChangedListener(
        new TimePicker.OnTimeChangedListener() {
          @Override
          public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
            mDate = new GregorianCalendar(year, month, day, hourOfDay, minute).getTime();
            getArguments().putLong(EXTRA_TIME, mDate.getTime());
          }
        }
    );

    return new AlertDialog.Builder(getActivity())
        .setView(v)
        .setTitle(R.string.time_picker_title)
        .setPositiveButton(
            android.R.string.ok,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
              }
            })
        .create();
  }

  private void sendResult(int resultCode) {
    if (getTargetFragment() == null)
      return;
    Intent i = new Intent();
    i.putExtra(EXTRA_TIME, mDate.getTime());
    getTargetFragment()
        .onActivityResult(getTargetRequestCode(), resultCode, i);
  }


  public static TimePickerFragment newInstance(Date d) {
    Bundle b = new Bundle();
    b.putLong(EXTRA_TIME, d.getTime());
    TimePickerFragment timePickerFragment = new TimePickerFragment();
    timePickerFragment.setArguments(b);
    return timePickerFragment;
  }
}
