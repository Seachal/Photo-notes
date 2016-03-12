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
//ѡ��֧�ֿ���Fragment���µĵ�android.support.v4.app.Fragment��

public class NoteFragment extends Fragment {
	public static final String EXTRA_NOTE_ID=
    		"com.seachal.criminalintent.note_id";
    private static final String TAG = "photo";
//	�������ͷ����ֻ������ͺ��������ͣ�������������
	private Note  mNote;//java����  �����������͵Ĺ�ϵ����������������Ա������
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
    	  imageView.setImageBitmap(bitmap); //����Bitmap 
  
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	showPhoto();
    }
    
	@Override//����һ��Noteʵ����Ա����
	/*��û������fragment����ͼ,onCreateView,������������fragment��ͼ��ͨ����һ��
	fragment�������ڷ���onCreateView����ɵ�,*/
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		UUID noteId = (UUID) getArguments().getSerializable(EXTRA_NOTE_ID);
		/*mNote = new Note();*/
		mNote = NoteLab.get(getActivity()).getNote(noteId);
		/*ȡ��Note��ID�����ø�ID��NoteLab�����е�ȡNote����ʹ��NoteLab.get(...)
		������ҪContext�������NoteFragment������NoteActivity��*/
		setHasOptionsMenu(true);
	}
	
	@Override
	
    //ͨ���÷�������fragment��ͼ�Ĳ��� �� Ȼ�����ɵ�View���ظ��й� activity ��
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/*fragment �� �� ͼ �� ֱ �� ͨ �� �� �� LayoutInflater.
		inflate(...)���������벼�ֵ���ԴID���ɵ�*/
		View view = inflater.inflate(R.layout.fragment_note,
				container, false);/*false������fragment��ͼ��Ӹ�����ͼ�����︸��ͼ
		container������ʵ���ڣ���Ϊtrue������������������ͨ�������fragment��Ӹ�Activity��
		�����ǰ�fragment��ӵ�activity�����С�*/
		
		mTitleField = (EditText) view.findViewById(R.id.note_title);
		mTitleField.setText(mNote.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			@Override
			/*���ǵ�����fragment��ͼ��View.findViewById(int)��������ǰʹ
			�õ�Activity.findViewById(int)����ʮ�ֱ������ܹ��ں�̨�Զ�����View.findViewById(int)������
			��Fragment��û�ж�Ӧ�ı���������������Ǳ����Լ���ɵ��á�*/
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
		
		 //����ʽIntent����
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
        	//Ҫ��ͼ��洢��sd��֮ǰ����ȼ��һ��sd���Ƿ����
        			String sdStatus = Environment.getExternalStorageState();
        			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
        				Log.v("TestFile",
        						"SD card is not avaiable/writeable right now.");
        				return;
        				  }

        		Bundle bundle = data.getExtras();
        		Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
        		FileOutputStream os = null;
        //���´������ʵ�ֽ�ͼ���ļ��浽��sdcard/myImage/���ļ����£�
        		File file = new File("/sdcard/myImage/");
        		file.mkdirs();// �����ļ���
        	
        		String fileName = "/sdcard/myImage/"+mNote.getMid()+".jpg";
        		if (fileName !=null) {
//////////////////////////////////////////////////////////// 
					showPhoto();
					//Log.i(TAG, "note:"+mNote.getTitle()+"has a photo");
				}
        	try {
        		os = new FileOutputStream(fileName);
        		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);// ������д���ļ�
        	
        			
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
        String report = getString(R.string.note_report, mNote.getTitle(), dateString, solvedString,"��");

        return report;
    }
    
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();//���ø���ķ���
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
              return BitmapFactory.decodeStream(fis);  ///����ת��ΪBitmapͼƬ        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
   }
	
}

