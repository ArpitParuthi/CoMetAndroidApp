package com.example.cometandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Activity1 extends Activity {
	
	ImageView iv;
	Bitmap bm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	    
	    Button b2 = (Button) findViewById(R.id.next);
		b2.setVisibility(View.VISIBLE);
	}
	}
}
