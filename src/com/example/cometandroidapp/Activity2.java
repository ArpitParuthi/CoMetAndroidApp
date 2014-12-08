package com.example.cometandroidapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Activity2 extends Activity {
	Bitmap bm;
	ImageView iv1;
	MultiSelectionSpinner spinner;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		Intent intent = getIntent();
		bm = intent.getParcelableExtra("image");
		iv1 = (ImageView)findViewById(R.id.iv1);
		iv1.setImageBitmap(bm);
		Button b2 = (Button) findViewById(R.id.next);
		b2.setOnClickListener (new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Activity2.this,Activity3.class);
				intent.putExtra("image",bm);
				startActivity(intent);	
			}
		});
		spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1); 
		Resources res = getResources();
		spinner.setItems(res.getStringArray(R.array.categories));  
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity2, menu);
		return true;
	}
}
