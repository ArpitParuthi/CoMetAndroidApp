package com.example.cometandroidapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.cometandroidapp.Activity3.TimePickerFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Activity4 extends Activity {
	TextView tv1;
	Button b1;
	Talk TALK_OBJECT;
	public final static String OBJECT = "com.example.cometandroidapp.object4";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TALK_OBJECT = getIntent().getParcelableExtra(Activity3.OBJECT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity4);
		tv1 = (TextView)findViewById(R.id.textView1);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Activity4.this,Activity1.class);
				intent.putExtra(LoginActivity.OBJECT, TALK_OBJECT);
				startActivity(intent);	
			}
		});
		
		new NewTalk().execute(TALK_OBJECT.getUrl(),TALK_OBJECT.getEmail(),TALK_OBJECT.getPassword(), TALK_OBJECT.getTalkTitle(),TALK_OBJECT.getSpeaker(),TALK_OBJECT.getCategory(),
				TALK_OBJECT.getLocation(),TALK_OBJECT.getDate(),TALK_OBJECT.getTimeFrom(),TALK_OBJECT.getTimeTo());
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
	    MenuItem item = menu.findItem(R.id.about);
	    item.setVisible(false);
	    MenuItem item2 = menu.findItem(R.id.action_settings);
	    item2.setVisible(false);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
	     super.onOptionsItemSelected(item);
	    	 logout();  
	    	 return true;
	}
	
	private void logout(){	
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return;	
	}
	
		
	class NewTalk extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			String url = params[0];
			String email = params[1];
			String password = params[2];
			String talkTitle = params[3];
			String speaker = params[4];
			String category = params[5];
			String location = params[6];
			String date = params[7];
			String timeFrom = params[8];
			String timeTo = params[9];
			StringBuilder stb= null;
			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://halley.exp.sis.pitt.edu/comet/colloquia/postNewTalkXML.jsp");
				List<NameValuePair> ref = new ArrayList<NameValuePair>();
				ref.add(new BasicNameValuePair("userEmail", email));
				ref.add(new BasicNameValuePair("password", password));
				ref.add(new BasicNameValuePair("title", talkTitle));
				ref.add(new BasicNameValuePair("speaker", speaker));
				ref.add(new BasicNameValuePair("affiliation", "NA"));
				ref.add(new BasicNameValuePair("picURL", url));
				ref.add(new BasicNameValuePair("talkDate", date));
				ref.add(new BasicNameValuePair("beginTime", timeFrom));
				ref.add(new BasicNameValuePair("endTime", timeTo));
				ref.add(new BasicNameValuePair("location", location));
				ref.add(new BasicNameValuePair("detail", "Category: " + category));
				post.setEntity(new UrlEncodedFormEntity(ref,"UTF-8"));

				HttpResponse response = http.execute(post);
				HttpEntity entity = response.getEntity();
				InputStream ios = null;
				ios = entity.getContent();
				String s = "";
				BufferedReader buffer = new BufferedReader(new InputStreamReader(ios));
				 stb = new StringBuilder();
				while((s = buffer.readLine())!=null) {
					stb.append(s);
				}
				ios.close();
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			return stb.toString();
			} 	
			
		@Override
	    	protected void onPostExecute(String output) {
				Log.e("Output: ", output);
				if(output.substring(75,output.length()-23).substring(0,7).equals("SUCCESS")) {
					tv1.setText("Your talk has been posted successfully!");
				}
				else
					tv1.setText("There was a problem posting your talk, please try again!");
				
			}
	    }
	}

