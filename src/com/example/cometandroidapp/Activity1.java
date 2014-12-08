package com.example.cometandroidapp;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class Activity1 extends Activity  {
	ImageView iv;
	Bitmap bm;
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
             finish();
            }
        });
        alertDialog.show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isNetworkAvailable()){
		    showAlertDialog(Activity1.this, "No Internet Connection!",
                    "You don't have internet connection. Please switch on your internet connection and try again.", false);
		} else {
		setContentView(R.layout.activity1);
        iv = (ImageView)findViewById(R.id.imageView1);
		Button b = (Button) findViewById(R.id.take);
		b.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent,0);	
			}
		});
		Button b2 = (Button) findViewById(R.id.next);
        b2.setVisibility(View.GONE);
		b2.setOnClickListener (new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Activity1.this,Activity2.class);
				intent.putExtra("image",bm);
				startActivity(intent);	
			}
		});
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(resultCode == RESULT_OK) {
			super.onActivityResult(requestCode, resultCode, data);
			bm = (Bitmap) data.getExtras().get("data");
			iv.setImageBitmap(bm);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bm.compress(Bitmap.CompressFormat.PNG,100, baos);
			byte[] b = baos.toByteArray();
			String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
			new Imgur().execute(imageEncoded);
			Button b2 = (Button) findViewById(R.id.next);
			b2.setVisibility(View.VISIBLE);
		}
	}
}

class Imgur extends AsyncTask<String, Void, String> {
	
	@Override
	protected String doInBackground(String... params) {
		String Image = params[0];
		String key = "8ca9d84ac6bcf3a";
		try {
			URL url = new URL("https://api.imgur.com/3/image");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String da = URLEncoder.encode("image", "UTF-8") + "="
	            + URLEncoder.encode(Image, "UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Client-ID " + key);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
	            "application/x-www-form-urlencoded");
			conn.connect();
			StringBuilder stb = new StringBuilder();
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(da);
			wr.flush();
			BufferedReader rd = new BufferedReader(
	            new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				stb.append(line).append("\n");
			}
			wr.close();
			rd.close();
			JSONObject finalResult = new JSONObject(stb.toString());
			String link = finalResult.getJSONObject("data").getString("link");
			Log.e("URL:", link);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
}
	
