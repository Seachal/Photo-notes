/*package com.seachal.criminalintent;
package com.seachal.criminalintent;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

//���Ǵ������ܹ��й�fragment�Ҽ���Froyo��GingerBread��activity��
public class CrimeActivity extends  SingleFragmentActivity{
	
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
      //		return new CrimeFragment();
	 UUID crimeId = (UUID) getIntent()
			.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);//��ת�䣬
	 //CrimeFragment..EXTRA_CRIME_ID
	  
		  
	     return CrimeFragment.newInstance(crimeId);
		}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment  fragment = fm.findFragmentById(R.id.fragmentContainer);��
        FragmentManager�л�ȡCrimeFragment������ʹ��������ͼ��ԴID��
        
        if (fragment == null) {
        	fragment = new CrimeFragment();
        	fm.beginTransaction()
        	.add(R.id.fragmentContainer, fragment)
        	.commit(); 
			//����һ���µ�fragment���񣬼���һ
        	//����Ӳ�����Ȼ���ύ�����
		}
    }

      
  

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	
}
*/