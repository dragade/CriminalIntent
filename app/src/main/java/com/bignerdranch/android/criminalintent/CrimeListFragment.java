package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {

  private static final String TAG = CrimeListFragment.class.getSimpleName();

  private ArrayList<Crime> mCrimes;
  private CrimeAdapter mCrimeAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivity().setTitle(R.string.crimes_title);
    mCrimes = CrimeLab.get(getActivity()).getCrimes();

    mCrimeAdapter = new CrimeAdapter(mCrimes);
    setListAdapter(mCrimeAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
  }

  public void onListItemClick(ListView l, View v, int position, long id) { // Get the Crime from the adapter
    Crime c = mCrimeAdapter.getItem(position);
    Intent i = new Intent(getActivity(), CrimeActivity.class);
    i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
    startActivity(i);
  }

  private class CrimeAdapter extends ArrayAdapter<Crime> {
    public CrimeAdapter(ArrayList<Crime> crimes) {
      super(getActivity(), 0, crimes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // If we weren't given a view, inflate one
      if (convertView == null) {
        convertView = getActivity().getLayoutInflater()
            .inflate(R.layout.list_item_crime, null);
      }
      // Configure the view for this Crime
      Crime c = getItem(position);
      TextView titleTextView =
          (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
      titleTextView.setText(c.getTitle());

      TextView dateTextView =
          (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
      CharSequence format = DateFormat.format("EEE MMM d HH:mm:ss yyyy", c.getDate());
      dateTextView.setText(format.toString());

      CheckBox solvedCheckBox =
          (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
      solvedCheckBox.setChecked(c.isSolved());
      return convertView;
    }
  }
}
