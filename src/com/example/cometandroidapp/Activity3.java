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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

public class Activity3 extends Activity{
	
	Bitmap bm;
	ImageView iv1;
	TimePickerFragment tpf;
	EditText et1, et2, et3, et4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity3);
		
		Intent intent = getIntent();
		bm = intent.getParcelableExtra("image");
		iv1 = (ImageView)findViewById(R.id.iv1);
		iv1.setImageBitmap(bm);
		et1 = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.editText2);
		et3 = (EditText)findViewById(R.id.editText3);
		et4 = (EditText)findViewById(R.id.editText4);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if(x=="1")
					et3.setText(hourOfDay+": "+minute);
				else if (x=="2")
					et4.setText(hourOfDay+": "+minute);
				
			}
			
		}
	class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			et2.setText((month+1)+"/"+day+"/"+year);
		}
	}
}


