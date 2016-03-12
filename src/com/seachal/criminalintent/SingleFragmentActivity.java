package com.seachal.criminalintent;

import com.seachal.criminalintent.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract Fragment createFragment(); 
		
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
//��Ϊʹ����֧�ֿ⼰FragmentActivity�࣬���������õķ�����getSupportFragmentManager()��
       // �� ȡ һ �� fragment �� ��FragmentManager����
        FragmentManager fm = getSupportFragmentManager();
        Fragment  fragment = fm.findFragmentById(R.id.fragmentContainer);/*��
        FragmentManager�л�ȡNoteFragment������ʹ��������ͼ��ԴID��*/
        /*��� FragmentManager��û��fragment���򴴽�һ��fragment�����ύ����*/
        if (fragment == null) {
        	fragment =  createFragment();//����createFragment����������һ��fragment
        	fm.beginTransaction()//����������FragmentTransactionʵ��
        	.add(R.id.fragmentContainer, fragment)//һ����Ӳ���������Ϊ����id���½���fragment
        	.commit(); 
			/*����һ���µ�fragment���񣬼���һ
        	����Ӳ�����Ȼ���ύ������*/
		}
    }


	
      
}
