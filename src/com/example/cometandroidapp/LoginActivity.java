package com.example.cometandroidapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	EditText et1, et2;
	TextView tv1;
	Button login;
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
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
		    showAlertDialog(LoginActivity.this, "No Internet Connection!",
                    "You don't have internet connection. Please switch on the internet connection and try again.", false);
		} else {
			
		setContentView(R.layout.activity_login);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.button1);
		login.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = et1.getText().toString();
				String pass = et2.getText().toString();
				new Comet().execute(name,pass);
			}
		});
		}
	}
	
	public void fetch(View v) {
		new Comet().execute("pittcomet@gmail.com","anonymoususer");
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}	
}

class Comet extends AsyncTask<String, Void, String> {

	protected String doInBackground(String... params) {
		String userEmail = params[0];
		String password = params[1];
		Log.e("email",userEmail);
		try {
			HttpClient http = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://halley.exp.sis.pitt.edu/comet/authentication/loginXML.jsp");
			List<NameValuePair> ref = new ArrayList<NameValuePair>();
			ref.add(new BasicNameValuePair("userEmail", userEmail));
			ref.add(new BasicNameValuePair("password", password));
			post.setEntity(new UrlEncodedFormEntity(ref,"UTF-8"));
			HttpResponse response = http.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream ios = null;
			ios = entity.getContent();
			String s = "";
			BufferedReader buffer = new BufferedReader(new InputStreamReader(ios));
			StringBuilder stb = new StringBuilder();
			while((s = buffer.readLine())!=null) {
				stb.append(s);
			}
			ios.close();
			Log.e("Returned",stb.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		} 	
}
