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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText et1, et2;
	TextView tv1;
	Button login;
	static String name;
	
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
				name = et1.getText().toString();
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
	
	class Comet extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			String userEmail = params[0];
			String password = params[1];
			Log.e("email",userEmail);
			StringBuilder stb=null;
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
				 stb = new StringBuilder();
				while((s = buffer.readLine())!=null) {
					stb.append(s);
				}
				ios.close();
				Log.e("Returned",stb.toString());
			} catch(Exception e) {
				e.printStackTrace();
			}
			return stb.toString();
			} 	
		
		@Override
	    protected void onPostExecute(String output) {
	        
			if(output.substring(55,output.length()-17).substring(0,2).equals("OK")){

				Intent intent = new Intent(LoginActivity.this, Activity1.class);
				intent.putExtra("detail",name);
			    startActivity(intent);	
			}else{
				 Context context = getApplicationContext();
	    		 CharSequence text = "Please enter valid username or password";
	    		 int duration = Toast.LENGTH_SHORT;
	    		 DisplayMetrics metrics = new DisplayMetrics();
	    		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    		 int height = metrics.heightPixels;
	    		 //set the text and duration of alert message
	    		 Toast toast = Toast.makeText(context, text, duration);
	    		 toast.setGravity(Gravity.TOP|Gravity.LEFT , 100 , height-600);
	    		 toast.show();			
			}
	    }
	}

}

