package com.seachal.criminalintent;

import android.support.v4.app.Fragment;

public class NoteListActivity extends SingleFragmentActivity {

	@Override//ʵ�ָ÷���ʹ���ܹ�����һ���µ�NoteListFragmentʵ��
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new NoteListFragment();
	}
 
}
