package com.seachal.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import com.seachal.criminalintent.R;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class NotePagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
    private ArrayList<Note> mNotes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mViewPager = new ViewPager(this);//实例化ViewPager类，
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mNotes = NoteLab.get(this).getNotes();//我们从NoteLab中（note的ArrayList）获取数据集
		android.support.v4.app.FragmentManager 
		fm=getSupportFragmentManager();//然后获取activity的FragmentManager实例。
		
		
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mNotes.size();
		//getCount()方法用来返回数组列表中包含的列表项数目
			}
			
			@Override
			public Fragment getItem(int pos) {
				// TODO Auto-generated method stub
				Note note = mNotes.get(pos);
				return NoteFragment.newInstance(note.getMid());
		  //将getItem(int)方法返回的fragment添加给activity，
		  //首先获取了数据集中指定位置的Note实例，然后利用该Note实例的ID创建并返回一个有效配置的CrimeFragment。
			}
		});
		
		
	//显示明细的标题。
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
		
			@Override
			public void onPageSelected(int pos) {
				// TODO Auto-generated method stub
              Note note = mNotes.get(pos);
              if (note.getTitle() !=null) {
				setTitle(note.getTitle());
			}
		}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//接收NoteActivity传过来的id;
		
		UUID noteId = (UUID) getIntent()
				.getSerializableExtra(NoteFragment.EXTRA_NOTE_ID);
		
		//如Note实例的mId与intent extra的noteId相匹配，则将当前要显示的列
		//表项设置为Note在数组中的索引位置
		for (int i = 0; i < mNotes.size(); i++) {
			if (mNotes.get(i).getMid().equals(noteId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
	}
	 

}
