package com.example.cometandroidapp;

import android.os.Parcel;  
import android.os.Parcelable;  

	public class Details implements Parcelable {  
	    private String speaker;
	    private String url;
	    private String category;
	    private String tt;
	    private String loc;
	    private String date;
	    private String tfrom;
	    private String tto;
	    
	    public String getUrl() {  
	    	return url;  
	    }  
	    
	    public void setUrl(String url) {  
	    	this.url = url;  
	    }
	    
	    public String getSpeaker() {  
	    	return speaker;  
	    }  
	    
	    public void setSpeaker(String speaker) {  
	    	this.speaker = speaker;  
	    }  
	    
	    public String getTalkTitle() {  
	    	return tt;  
	   	}  
	    
	   	public void setTalkTitle(String name) {  
	   		this.tt = name;  
	   	} 
	   	 
	    public String getCategory() {  
	    	return category;  
	    }  
	    
	    public void setCategory( String category) {  
	    	this.category = category;  
	    }
	    
	    public String getLocation() {  
	   	 	return loc;  
	   	}  
	   	    
	    public void setLocation( String loc) {  
	   	 	this.loc = loc;  
	   	}
	   	    
	   	 public String getDate() {  
	   		 return date;  
	   	 }  
	   	 
	     public void setDate( String date) {  
	   		 this.date= date;  
	     }
	   		    
	   	 public String getTimeFrom() {  
	   		 return tfrom;  
	   	 }  
	   	 
	   	 public void setTimeFrom( String tfrom) {  
	   		 this.tfrom = formatetime(tfrom); 
	   	 }
	   			    
		 public String getTimeTo() {  
	   		 return tto;  
	   	 }  
		 
	   	 public void setTimeTo( String tto) {  
	   		  this.tto = tto;  
	   	 }
	   	 
	     public static final Parcelable.Creator<Details> CREATOR = new Creator<Details>() {
	    	 
	    	 public Details createFromParcel(Parcel source) {  
	    	     Details md = new Details();  
	    	     md.speaker= source.readString();  
	    	     md.category = source.readString(); 
	    	     md.url = source.readString();
	    	     md.tt= source.readString();  
	    	     md.loc = source.readString(); 
	    	     md.date= source.readString();  
	    	     md.tfrom = source.readString(); 
	    	     md.tto= source.readString();  
	    	     return md;  
	    	 }  
	    	 
	    	 public Details[] newArray(int size) {  
	    	     return new Details[size];  
	    	 }  
	     };
	     
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			 parcel.writeString(speaker);  
			 parcel.writeString(category);
			 parcel.writeString(url);
			 parcel.writeString(tt);  
			 parcel.writeString(loc);
			 parcel.writeString(date);  
			 parcel.writeString(tfrom);
			 parcel.writeString(tto);  	
		} 

		 private String formatetime(String time) {
			 String ret = null;
			 String[] splitString = time.split(":");
			 int hour = Integer.parseInt(splitString[0]);
			 if(hour ==0) {
				 hour=hour+12;
				 ret = time+" AM";
			 } else if(hour < 12){
				 ret = time+" AM";
			 } else if(hour >12){
				 hour=hour-12;
				 ret= hour+":"+splitString[1]+" PM";
			 } else {
				 ret= hour+":"+splitString[1]+" PM";
			 }
			 return ret;	
		 }
	}
	       
	