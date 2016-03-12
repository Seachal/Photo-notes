package com.seachal.criminalintent;

import android.support.v4.app.Fragment;

public class NoteListActivity extends SingleFragmentActivity {

	@Override//实现该方法使其能够返回一个新的NoteListFragment实例
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new NoteListFragment();
	}
 
}
