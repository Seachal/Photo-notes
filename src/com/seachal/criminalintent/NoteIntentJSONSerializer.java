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
	//���ļ��м���note��¼
	/*����ʹ��Java�� JSON���Լ�Context��openFileInput(...)������
	���Ǵ��ļ��ж�ȡ���ݲ�ת��ΪJSONObjects���͵�string��Ȼ���ٽ���ΪJSONArray��������
	����ΪArrayList����󷵻ػ�õ�ArrayList��*/
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
		JSONArray array = new JSONArray();//����һ��JSONArray�������
		for (Note c : notes) 
			array.put(c.toJSON());//��������б��е�����note��¼����toJSON()������
		/*��ǰ��
		����toJSON()���������������Ϊ�Ժ����ǲŻ���Note����ʵ�ָ÷�����*/
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
