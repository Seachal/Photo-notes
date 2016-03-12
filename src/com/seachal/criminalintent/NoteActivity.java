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

//我们创建了能够托管fragment且兼容Froyo及GingerBread的activity。
public class CrimeActivity extends  SingleFragmentActivity{
	
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
      //		return new CrimeFragment();
	 UUID crimeId = (UUID) getIntent()
			.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);//大转变，
	 //CrimeFragment..EXTRA_CRIME_ID
	  
		  
	     return CrimeFragment.newInstance(crimeId);
		}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment  fragment = fm.findFragmentById(R.id.fragmentContainer);从
        FragmentManager中获取CrimeFragment，即可使用容器视图资源ID，
        
        if (fragment == null) {
        	fragment = new CrimeFragment();
        	fm.beginTransaction()
        	.add(R.id.fragmentContainer, fragment)
        	.commit(); 
			//创建一个新的fragment事务，加入一
        	//个添加操作，然后提交该事物。
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