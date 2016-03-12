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
/**toString(),��Ϊ����Object�����Ѿ����˵ķ������������඼�Ǽ̳�Object��
 * ���ԡ����ж������������������ͨ��ֻ��Ϊ�˷������������System.out.println(xx)��
 * ��������ġ�xx���������String���������͵Ļ������Զ�����xx��toString()������
 * �ܶ���֮����ֻ��sun��˾����java��ʱ��Ϊ�˷�����������ַ�����������������һ������*/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}


//ʵ��toJSON()����
	public JSONObject toJSON() throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		
		json.put(JSON_ID, mid.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		
		return json;
	/*	���ϴ����У�ʹ��JSONObject���еķ��������ǽ�Note��������ת��Ϊ��д��JSON�ļ���
		JSONObject�������ݡ�*/
	}


  //���һ������JSONObject����Ĺ��췽��
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