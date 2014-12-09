package com.example.cometandroidapp;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

public class Activity3 extends Activity{
	
	Bitmap bm;
	ImageView iv1;
	TimePickerFragment tpf;
	EditText et1, et2, et3, et4;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity3);
		Intent intent = getIntent();
		bm = intent.getParcelableExtra("image");
		iv1 = (ImageView)findViewById(R.id.iv1);
		iv1.setImageBitmap(bm);
		et2 = (EditText)findViewById(R.id.editText2);
		et3 = (EditText)findViewById(R.id.editText3);
		et4 = (EditText)findViewById(R.id.editText4);
		
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_buildings);
		String[] buildings = getResources().getStringArray(R.array.buildings_array);
		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildings);
		textView.setAdapter(adapter);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void showTimePickerDialog1(View v) {
		Bundle bundle = new Bundle();
	    DialogFragment newFragment = new TimePickerFragment();
	    bundle.putString("id", "1");
	    newFragment.setArguments(bundle);
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showTimePickerDialog2(View v) {
		Bundle bundle = new Bundle();
	    DialogFragment newFragment = new TimePickerFragment();
	    bundle.putString("id", "2");
	    newFragment.setArguments(bundle);
	    newFragment.show(getFragmentManager(), "timePicker");
	} 

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity3, menu);
		return true;
	}
	
	class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		
		String x;
		
		public Dialog onCreateDialog(Bundle savedInstanceState) {
					Bundle bundle = this.getArguments();
					final Calendar c = Calendar.getInstance();
					int hour = c.get(Calendar.HOUR_OF_DAY);
					int minute = c.get(Calendar.MINUTE);
					 x= bundle.getString("id");
					return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));		
		}
	
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if(x=="1")
					et3.setText(hourOfDay+": "+minute);
				else if (x=="2")
					et4.setText(hourOfDay+": "+minute);	
			}
	}
	
	class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			et2.setText((month+1)+"/"+day+"/"+year);
		}
	}
}


