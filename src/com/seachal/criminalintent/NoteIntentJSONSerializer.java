package com.seachal.criminalintent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;

import org.apache.http.message.BufferedHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.R.integer;
import android.content.Context;

public class NoteIntentJSONSerializer {
	private Context mContext;
	private String mFilename;
	public NoteIntentJSONSerializer(Context c, String f) {
	
		mContext = c;
		mFilename = f;
	}
	//从文件中加载note记录
	/*联合使用Java、 JSON类以及Context的openFileInput(...)方法，
	我们从文件中读取数据并转换为JSONObjects类型的string，然后再解析为JSONArray，接着再
	解析为ArrayList，最后返回获得的ArrayList。*/
	public ArrayList<Note> loadNotes() throws JSONException, Exception {
		ArrayList< Note> notes =new ArrayList<Note>();
		BufferedReader reader = null;
		try {
			InputStream in= mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			try {
				while ((line=reader.readLine()) !=null){
				jsonString.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			     .nextValue();
			for (int i = 0; i < array.length(); i++) {
			  notes.add(new Note(array.getJSONObject(i)));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (reader !=null) {
				reader.close();
				
			}
		}
		
		return notes;
		
	}
	public void saveNotes(ArrayList<Note> notes)
	            throws JSONException,IOException{
		//Build an array in JSON
		JSONArray array = new JSONArray();//创建一个JSONArray数组对象
		for (Note c : notes) 
			array.put(c.toJSON());//针对数组列表中的所有note记录调用toJSON()方法，
		/*当前，
		调用toJSON()方法会产生错误，因为稍后我们才会在Note类中实现该方法。*/
	  	//Write the file to disk
		Writer writer = null;
		
		try {
			OutputStream out = mContext
					.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally{
			if (writer !=null) 
			   writer.close();
		}
		
	}
   
}
