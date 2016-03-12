 package com.seachal.criminalintent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
//选择支持库中Fragment类下的的android.support.v4.app.Fragment类

public class NoteFragment extends Fragment {
	public static final String EXTRA_NOTE_ID=
    		"com.seachal.criminalintent.note_id";
    private static final String TAG = "photo";
//	数据类型分两种基本类型和引用类型，类是引用类型
	private Note  mNote;//java语言  类与数据类型的关系，可以用类声明成员变量；
    private EditText mTitleField;
    private CheckBox mSolvedCheckBox;
    private ImageView imageView;
    
    
    public static NoteFragment newInstance(UUID noteId){
    	Bundle args =  new Bundle();
    	args.putSerializable(EXTRA_NOTE_ID, noteId);
    	NoteFragment fragment = new NoteFragment();
    	fragment.setArguments(args);
		return fragment;
    	
    }
    
    
    private void showPhoto(){
         Bitmap bitmap = getLoacalBitmap("/sdcard/myImage/"+mNote.getMid()+".jpg"); 
    	  imageView.setImageBitmap(bitmap); //设置Bitmap 
  
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	showPhoto();
    }
    
	@Override//新增一个Note实例成员变量
	/*并没有生成fragment的视图,onCreateView,但创建和配置fragment视图是通过另一个
	fragment生命周期方法onCreateView来完成的,*/
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		UUID noteId = (UUID) getArguments().getSerializable(EXTRA_NOTE_ID);
		/*mNote = new Note();*/
		mNote = NoteLab.get(getActivity()).getNote(noteId);
		/*取得Note的ID后，利用该ID从NoteLab单例中调取Note对象。使用NoteLab.get(...)
		方法需要Context对象，因此NoteFragment传入了NoteActivity。*/
		setHasOptionsMenu(true);
	}
	
	@Override
	
    //通过该方法生成fragment视图的布局 ， 然后将生成的View返回给托管 activity 。
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/*fragment 的 视 图 是 直 接 通 过 调 用 LayoutInflater.
		inflate(...)方法并传入布局的资源ID生成的*/
		View view = inflater.inflate(R.layout.fragment_note,
				container, false);/*false，不将fragment视图添加给父视图，这里父视图
		container并不真实存在，设为true程序会出错，而且我们是通过代码把fragment添加给Activity，
		而不是把fragment添加到activity布局中。*/
		
		mTitleField = (EditText) view.findViewById(R.id.note_title);
		mTitleField.setText(mNote.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			@Override
			/*我们调用了fragment视图的View.findViewById(int)方法。以前使
			用的Activity.findViewById(int)方法十分便利，能够在后台自动调用View.findViewById(int)方法。
			而Fragment类没有对应的便利方法，因此我们必须自己完成调用。*/
			public void onTextChanged(CharSequence c, int start, int before, 
					int count) {
				// TODO Auto-generated method stub
				mNote.setTitle(c.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence c, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable c) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		mSolvedCheckBox = (CheckBox) view.findViewById(R.id.note_solved);
		mSolvedCheckBox.setChecked(mNote.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			mNote.setSolved(isChecked);
			}
		});
		
		Button tackButton = (Button) view.findViewById(R.id.note_tackphoto);
		tackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				
						
			}
		});
		
		 //用隐式Intent启动
		 Button reportButton = (Button)view.findViewById(R.id.note_reportButton);
	        reportButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                Intent i = new Intent(Intent.ACTION_SEND);
	                i.setType("text/plain");
	                i.putExtra(Intent.EXTRA_TEXT, getNoteReport());
	                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.note_report_subject));
	                i = Intent.createChooser(i, getString(R.string.send_report));
	                startActivity(i);
	            } 
	        });
	        
	        imageView = (ImageView) view.findViewById(R.id.note_imageview);  
		 
		return view;
		
	}
	
	
	

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (resultCode != Activity.RESULT_OK) return;
    	if (resultCode == Activity.RESULT_OK) {
        	//要将图像存储到sd卡之前最好先检查一下sd卡是否可用
        			String sdStatus = Environment.getExternalStorageState();
        			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
        				Log.v("TestFile",
        						"SD card is not avaiable/writeable right now.");
        				return;
        				  }

        		Bundle bundle = data.getExtras();
        		Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        		FileOutputStream os = null;
        //以下代码可以实现将图像文件存到“sdcard/myImage/”文件夹下，
        		File file = new File("/sdcard/myImage/");
        		file.mkdirs();// 创建文件夹
        	
        		String fileName = "/sdcard/myImage/"+mNote.getMid()+".jpg";
        		if (fileName !=null) {
//////////////////////////////////////////////////////////// 
					showPhoto();
					//Log.i(TAG, "note:"+mNote.getTitle()+"has a photo");
				}
        	try {
        		os = new FileOutputStream(fileName);
        		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);// 把数据写入文件
        	
        			
        			} catch (FileNotFoundException e) {
        					e.printStackTrace();
        			} finally {
        				try {
        					os.flush();
        					os.close();
        				  } catch (IOException e) {
        					e.printStackTrace();
        				    }
        				}
        	
        
        			}
    	
    }
    
    private String getNoteReport() {
        String solvedString = null;
        if (mNote.isSolved()) {
            solvedString = getString(R.string.note_report_solved);
        } else {
            solvedString = getString(R.string.note_report_unsolved);
        }

        String dateFormat = "EEE, yyyy-MM-dd HH:mm:ss";
        String dateString = DateFormat.format(dateFormat, mNote.getDate()).toString();
        String report = getString(R.string.note_report, mNote.getTitle(), dateString, solvedString,"。");

        return report;
    }
    
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();//调用父类的方法
		NoteLab.get(getActivity()).saveNotes();
	}
	
    
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	
	}
	/////////////////////////////////
	public static Bitmap getLoacalBitmap(String url) {
        try {
             FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
   }
	
}

