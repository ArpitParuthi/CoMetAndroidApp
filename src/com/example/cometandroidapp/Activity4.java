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
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Activity4 extends Activity {
	Talk TALK_OBJECT;
	public final static String OBJECT = "com.example.cometandroidapp.object4";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity4);
		TALK_OBJECT = getIntent().getParcelableExtra(Activity3.OBJECT);
		new NewTalk().execute(TALK_OBJECT.getUrl(),TALK_OBJECT.getEmail(),TALK_OBJECT.getTalkTitle(),TALK_OBJECT.getSpeaker(),TALK_OBJECT.getCategory(),
				TALK_OBJECT.getLocation(),TALK_OBJECT.getDate(),TALK_OBJECT.getTimeFrom(),TALK_OBJECT.getTimeTo());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity4, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class NewTalk extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			String url = params[0];
			String email = params[1];
			String talkTitle = params[2];
			String speaker = params[3];
			String category = params[4];
			String location = params[5];
			String date = params[6];
			String timeFrom = params[7];
			String timeTo = params[8];
			
			StringBuilder stb= new StringBuilder();
			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://halley.exp.sis.pitt.edu/comet/colloquia/postNewTalkXML.jsp");
				List<NameValuePair> ref = new ArrayList<NameValuePair>();
				ref.add(new BasicNameValuePair("title", talkTitle));
				ref.add(new BasicNameValuePair("speaker", speaker));
				ref.add(new BasicNameValuePair("affiliation", "NA"));
				ref.add(new BasicNameValuePair("picURL", url));
				ref.add(new BasicNameValuePair("talkDate", date));
				ref.add(new BasicNameValuePair("beginTime", timeFrom));
				ref.add(new BasicNameValuePair("endTime", timeTo));
				ref.add(new BasicNameValuePair("location", location));
				ref.add(new BasicNameValuePair("detail", "Posted By: " + email +"\n" + "Category: " + category));
				post.setEntity(new UrlEncodedFormEntity(ref,"UTF-8"));
				/*
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
				*/
			} catch(Exception e) {
				e.printStackTrace();
			}
			return stb.toString();
			} 	
		
		@Override
	    	protected void onPostExecute(String output) {
				Log.e("Result",output);
			}
	    }
	}

