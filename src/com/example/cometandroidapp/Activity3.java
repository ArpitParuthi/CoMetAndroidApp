package com.example.cometandroidapp;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Activity3 extends Activity implements OnClickListener{
	
	Bitmap bm;
	ImageView iv1;
	TimePickerFragment tpf;
	EditText et2, et3, et4;
	AutoCompleteTextView ac1;
	Button btn, prev;
	Talk TALK_OBJECT;
	public final static String OBJECT = "com.example.cometandroidapp.object3";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity3);
		byte[] byteArray = getIntent().getByteArrayExtra("image");
		bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		iv1 = (ImageView)findViewById(R.id.iv1);
		iv1.setImageBitmap(bm);
		et2 = (EditText)findViewById(R.id.editText2);
		et3 = (EditText)findViewById(R.id.editText3);
		et4 = (EditText)findViewById(R.id.editText4);
		ac1 = (AutoCompleteTextView) findViewById(R.id.autocomplete_buildings);
		String[] buildings = getResources().getStringArray(R.array.buildings_array);
		ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buildings);
		ac1.setAdapter(adapter);
		btn = (Button)findViewById(R.id.next);
		btn.setOnClickListener(this);
		prev = (Button)findViewById(R.id.previous);
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
	            return;
			}
		}); 
		TALK_OBJECT = (Talk)getIntent().getParcelableExtra(Activity2.OBJECT);
		
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
	     super.onOptionsItemSelected(item);
	     switch(item.getItemId()){
	     case R.id.about:
	    	 aboutMenuitem();
	    	 break;
	     case R.id.action_settings:
	    	 actionsettings();
	    	 break;
	     case R.id.logout:
	    	 logout();
	    	 break;
	     }   
		return true;
	}
	
	private void logout(){
		
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return;
		
		
	}
	
	private void actionsettings() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("Help")
		.setMessage("On this page, you will:\n"
				+ "1. Add location details! We will auto-complete buildings from Pitt and CMU for you\n"
				+ "2. Add the date of the talk. You can fill in the date in the required format, or click on the field to select from a calendar\n"
				+ "3. Add the time from and to of the talk. You can fill it in manually, or click on the field to select from a time picker\n"
				+ "4. Proceed onto the next page\n")
		.setNeutralButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).show(); 
		
	}

	private void aboutMenuitem() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("About")
		.setMessage("Develpers: Arpit, Jasmin, Vivek, Somi")
		.setNeutralButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).show(); 
		
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
					et3.setText(hourOfDay+":"+minute);
				else if (x=="2")
					et4.setText(hourOfDay+":"+minute);	
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
	
	public void PacelableMethod(){  	
		TALK_OBJECT.setLocation(ac1.getText().toString());
		TALK_OBJECT.setDate(et2.getText().toString());
		TALK_OBJECT.setTimeFrom(et3.getText().toString());
		TALK_OBJECT.setTimeTo(et4.getText().toString());
	    Intent intent = new Intent(Activity3.this,Activity4.class); 
	    intent.putExtra(OBJECT, TALK_OBJECT);
	    startActivity(intent);  
	    }
	
	@Override
	public void onClick(View v) {
		
		if(ac1.getText().toString().trim().equals("")){
			
		 Context context = getApplicationContext();
   		 CharSequence text = "Please enter valid location";
   		 int duration = Toast.LENGTH_SHORT;
   		 DisplayMetrics metrics = new DisplayMetrics();
   		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
   		 int height = metrics.heightPixels;
   		 //set the text and duration of alert message
   		 Toast toast = Toast.makeText(context, text, duration);
   		 toast.setGravity(Gravity.TOP|Gravity.LEFT , 200 , height-900);
   		 toast.show();
   		 return;
   		 
		}else if(et2.getText().toString().trim().equals("")){
			
		 Context context = getApplicationContext();
  		 CharSequence text = "Please select valid date";
  		 int duration = Toast.LENGTH_SHORT;
  		 DisplayMetrics metrics = new DisplayMetrics();
  		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
  		 int height = metrics.heightPixels;
  		 //set the text and duration of alert message
  		 Toast toast = Toast.makeText(context, text, duration);
  		 toast.setGravity(Gravity.TOP|Gravity.LEFT , 250 , height-900);
  		 toast.show();
  		 return;
			
			
		}else if(et3.getText().toString().trim().equals("")){
			
			 Context context = getApplicationContext();
	   		 CharSequence text = "Please enter start time of talk";
	   		 int duration = Toast.LENGTH_SHORT;
	   		 DisplayMetrics metrics = new DisplayMetrics();
	   		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
	   		 int height = metrics.heightPixels;
	   		 //set the text and duration of alert message
	   		 Toast toast = Toast.makeText(context, text, duration);
	   		 toast.setGravity(Gravity.TOP|Gravity.LEFT , 175 , height-900);
	   		 toast.show();
	   		 return;
			
			
		}else if(et4.getText().toString().trim().equals("")){
			
			 Context context = getApplicationContext();
	   		 CharSequence text = "Please enter end time of talk";
	   		 int duration = Toast.LENGTH_SHORT;
	   		 DisplayMetrics metrics = new DisplayMetrics();
	   		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
	   		 int height = metrics.heightPixels;
	   		 //set the text and duration of alert message
	   		 Toast toast = Toast.makeText(context, text, duration);
	   		 toast.setGravity(Gravity.TOP|Gravity.LEFT , 175 , height-900);
	   		 toast.show();
	   		 return;
			
			
		}else{
			
			PacelableMethod(); 
			
		}
	}
	
}


