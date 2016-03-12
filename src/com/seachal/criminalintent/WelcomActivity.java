package com.seachal.criminalintent;

import com.seachal.criminalintent.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WelcomActivity extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
//		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.welcome_layout);
//		relativeLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				switch (v.getId()) {
//				case R.id.welcome_layout:
//					Intent intent = new Intent(WelcomActivity.this, NoteListActivity.class);
//					startActivity(intent);
//					b=true;
//					WelcomActivity.this.finish();
//				
//					break;
//				}
//				
//			}
//		});
		
	
	
      new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Intent intent = new Intent(WelcomActivity.this, NoteListActivity.class);
				startActivity(intent);
				WelcomActivity.this.finish();
			}
		   }).start();
	    }
	
}
	
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		count.setcount(1);
//	}
//}
//	
//  
//  
//
//
//   class Count{
//	  private   int i=0;
//	  public   void setcount(int i){
//		this.i=i;
//	  }
//	  public   int getcount(){
//		return i;
//	 }
//  }

