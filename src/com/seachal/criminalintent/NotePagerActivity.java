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
		
		mViewPager = new ViewPager(this);//ʵ����ViewPager�࣬
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mNotes = NoteLab.get(this).getNotes();//���Ǵ�NoteLab�У�note��ArrayList����ȡ���ݼ�
		android.support.v4.app.FragmentManager 
		fm=getSupportFragmentManager();//Ȼ���ȡactivity��FragmentManagerʵ����
		
		
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mNotes.size();
		//getCount()�����������������б��а������б�����Ŀ
			}
			
			@Override
			public Fragment getItem(int pos) {
				// TODO Auto-generated method stub
				Note note = mNotes.get(pos);
				return NoteFragment.newInstance(note.getMid());
		  //��getItem(int)�������ص�fragment��Ӹ�activity��
		  //���Ȼ�ȡ�����ݼ���ָ��λ�õ�Noteʵ����Ȼ�����ø�Noteʵ����ID����������һ����Ч���õ�CrimeFragment��
			}
		});
		
		
	//��ʾ��ϸ�ı��⡣
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
		
		//����NoteActivity��������id;
		
		UUID noteId = (UUID) getIntent()
				.getSerializableExtra(NoteFragment.EXTRA_NOTE_ID);
		
		//��Noteʵ����mId��intent extra��noteId��ƥ�䣬�򽫵�ǰҪ��ʾ����
		//��������ΪNote�������е�����λ��
		for (int i = 0; i < mNotes.size(); i++) {
			if (mNotes.get(i).getMid().equals(noteId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
	}
	 

}
