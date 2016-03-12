package com.seachal.criminalintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;
//，编码实现NoteLab类为带有私有构造方法和get(Context)
//方法的单例，
public class NoteLab {
	private static final String TAG="NoteLab";
	private static final String FILENAME="notes.json";
	
	private ArrayList<Note> mNotes;//一个空的用来保存Note对象的ArrayList
	private NoteIntentJSONSerializer mSerializer;
	
	
	private static NoteLab sNoteLab;
	private Context mAppContext;
	
	public void addNote(Note n){
		mNotes.add(n);
	}
	public void deleteNote(Note n){
		mNotes.remove(n);
		
	}
	//NoteLab类的构造方法需要一个Context参数
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
	public static NoteLab get(Context c){//static确保不用对象，直接用单例累调用get方法
		if (sNoteLab ==null) {
			/*在get(Context)方法里，我们并没有直接将Context参数传给构造方法。该Context
			可能是一个Activity，也可能是另一个Context对象，如Service。在应用的整个生命周期里，
			我们无法保证只要CrimeLab需要用到Context， Context就一定会存在。*/
			sNoteLab= new NoteLab(c.getApplicationContext());//通过私有构造方法保证单一
		//为保证单例总是有Context可以使用，可调用getApplicationContext()方法	
		}
		return sNoteLab;
	}
	//数组列表
	public ArrayList<Note>getNotes(){
		return mNotes;
		
	}
    //返回带有指定ID的Note对象
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
