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
//因为使用了支持库及FragmentActivity类，因此这里调用的方法是getSupportFragmentManager()。
       // 获 取 一 个 fragment 交 由FragmentManager管理
        FragmentManager fm = getSupportFragmentManager();
        Fragment  fragment = fm.findFragmentById(R.id.fragmentContainer);/*从
        FragmentManager中获取NoteFragment，即可使用容器视图资源ID，*/
        /*如果 FragmentManager中没有fragment，则创建一个fragment，并提交事物*/
        if (fragment == null) {
        	fragment =  createFragment();//调用createFragment方法，创建一个fragment
        	fm.beginTransaction()//创建并返回FragmentTransaction实例
        	.add(R.id.fragmentContainer, fragment)//一个添加操作，参数为容器id和新建的fragment
        	.commit(); 
			/*创建一个新的fragment事务，加入一
        	个添加操作，然后提交该事务。*/
		}
    }


	
      
}
