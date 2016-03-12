package com.seachal.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Note {
	private UUID  mid;
	private  String mTitle;
	private  Date mDate;
	private  boolean mSolved;
	
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "soved";
	private static final String JSON_DATE = "date";
	
	public Note(){
		mid= UUID.randomUUID();
		mDate = new Date() ;
	}

	public UUID getMid() {
		return mid;
	}

	public void setMid(UUID mid) {
		this.mid = mid;
	}


	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
		
	}
/**toString(),因为它是Object里面已经有了的方法，而所有类都是继承Object，
 * 所以“所有对象都有这个方法”。它通常只是为了方便输出，比如System.out.println(xx)，
 * 括号里面的“xx”如果不是String、数字类型的话，就自动调用xx的toString()方法，
 * 总而言之，它只是sun公司开发java的时候为了方便所有类的字符串操作而特意加入的一个方法*/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}


//实现toJSON()方法
	public JSONObject toJSON() throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		
		json.put(JSON_ID, mid.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		
		return json;
	/*	以上代码中，使用JSONObject类中的方法，我们将Note对象数据转换为可写入JSON文件的
		JSONObject对象数据。*/
	}


  //添加一个接受JSONObject对象的构造方法
	public Note(JSONObject json) throws JSONException {
		//super();
		mid =UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_TITLE)) {
			mTitle = json.getString(JSON_TITLE);
			
		}
		
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate  = new Date(json.getLong(JSON_DATE));
	}
   
	

	
	
	
}