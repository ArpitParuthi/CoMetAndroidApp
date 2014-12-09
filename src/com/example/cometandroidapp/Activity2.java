package com.example.cometandroidapp;

import java.io.ByteArrayOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Activity2 extends Activity implements OnClickListener {
	Bitmap bm;
	ImageView iv1;
	String url = "";
	MultiSelectionSpinner spinner;
	EditText edit1, edit2;
	public static String par ="com.example.cometandroidapp.Details";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		Intent intent = getIntent();
		byte[] byteArray = getIntent().getByteArrayExtra("image");
		bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		url = intent.getStringExtra("url");
		Log.e("url",url);
		iv1 = (ImageView)findViewById(R.id.iv1);
		iv1.setImageBitmap(bm);
		edit1=(EditText)findViewById(R.id.editText1);
		edit2=(EditText)findViewById(R.id.editText2);
		Button b2 = (Button) findViewById(R.id.next);
		b2.setOnClickListener(this); 
		spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1); 
		Resources res = getResources();
		spinner.setItems(res.getStringArray(R.array.categories));  
	}
	
	public void PacelableMethod(){  
	        Details detail = new Details();
	        detail.setUrl(url);
	        detail.setSpeaker(edit1.getText().toString());
	        detail.setCategory(spinner.getSelectedItemsAsString());
	        Intent mIntent = new Intent(this,Activity3.class);  
	        Bundle mBundle = new Bundle();  
	        mBundle.putParcelable(par, detail);
	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
	        byte[] bytes = stream.toByteArray();
	        mIntent.putExtra("image",bytes);
	        mIntent.putExtras(mBundle);  
	        startActivity(mIntent);  
	}

	public void onClick(View v) {
			 PacelableMethod(); 
	}  
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity2, menu);
		return true;
	}
}
