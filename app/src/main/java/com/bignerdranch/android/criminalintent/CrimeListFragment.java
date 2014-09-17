package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    setHasOptionsMenu(true);
    mCrimes = CrimeLab.get(getActivity()).getCrimes();
    mCrimeAdapter = new CrimeAdapter(mCrimes);
    setListAdapter(mCrimeAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    mCrimes = CrimeLab.get(getActivity()).getCrimes();
    ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
  }

  public void onListItemClick(ListView l, View v, int position, long id) { // Get the Crime from the adapter
    Crime c = mCrimeAdapter.getItem(position);
    Intent i = new Intent(getActivity(), CrimePagerActivity.class);
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

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_crime_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_new_crime:
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        Crime crime = new Crime();
        crimeLab.addCrime(crime);
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(i, 0);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    } }
}
