package com.seachal.criminalintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
//������ʵ��NoteLab��Ϊ����˽�й��췽����get(Context)
//�����ĵ�����
public class NoteLab {
	private static final String TAG="NoteLab";
	private static final String FILENAME="notes.json";
	
	private ArrayList<Note> mNotes;//һ���յ���������Note�����ArrayList
	private NoteIntentJSONSerializer mSerializer;
	
	
	private static NoteLab sNoteLab;
	private Context mAppContext;
	
	public void addNote(Note n){
		mNotes.add(n);
	}
	public void deleteNote(Note n){
		mNotes.remove(n);
		
	}
	//NoteLab��Ĺ��췽����Ҫһ��Context����
	private NoteLab(Context appContext){
		mAppContext = appContext;
		//mNotes = new ArrayList<Note>();
		
		mSerializer = new NoteIntentJSONSerializer(mAppContext, FILENAME);
		try {
			mNotes = mSerializer.loadNotes();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mNotes = new ArrayList<Note>();
		}
		
	}
	public static NoteLab get(Context c){//staticȷ�����ö���ֱ���õ����۵���get����
		if (sNoteLab ==null) {
			/*��get(Context)��������ǲ�û��ֱ�ӽ�Context�����������췽������Context
			������һ��Activity��Ҳ��������һ��Context������Service����Ӧ�õ��������������
			�����޷���ֻ֤ҪCrimeLab��Ҫ�õ�Context�� Context��һ������ڡ�*/
			sNoteLab= new NoteLab(c.getApplicationContext());//ͨ��˽�й��췽����֤��һ
		//Ϊ��֤����������Context����ʹ�ã��ɵ���getApplicationContext()����	
		}
		return sNoteLab;
	}
	//�����б�
	public ArrayList<Note>getNotes(){
		return mNotes;
		
	}
    //���ش���ָ��ID��Note����
	public Note getNote(UUID id){
		for (Note n :mNotes) {
		if (n.getMid().equals(id))
			return n;
		}
		return null;
		
	}
	public boolean saveNotes(){
		
			try {
				mSerializer.saveNotes(mNotes);
				Log.d(TAG, " saving notes:");
				return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "Error saving notes:",e);
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "Error saving notes:",e);
				return false;
			}
	}
	
	
}
