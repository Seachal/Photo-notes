package com.seachal.criminalintent;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.seachal.criminalintent.R;

import android.R.array;
import android.R.integer;
import android.R.raw;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NoteListFragment extends ListFragment {
   private  ArrayList<Note> mNotes;
   private boolean mSubtitleVisible;
   private static final String TAG = "NoteListFragment";
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//֪ͨFragmentManager�� NoteListFragment-
		//�����ѡ��˵������ص�
		setHasOptionsMenu(true);
		/*getActivity()��������Fragment���������������Է����й�activity��������
		fragment��������activity�������*/
		getActivity().setTitle(R.string.note_title);
		
		mNotes = NoteLab.get(getActivity()).getNotes();//�Ȼ�ȡNoteLab�������ٻ�ȡ���е�note�б�
	
      /* ArrayAdapter<Note> adapter =  new ArrayAdapter<Note>(getActivity(),
    		   android.R.layout.simple_list_item_1,mNotes);*/
		//ע������Ѿ���Note��Ϊ��mNotes��
		NoteAdapter adapter = new NoteAdapter(mNotes);
		/*setListAdapter(ListAdapter) ��һ�� ListFragment��ı����� �� ��ʹ������Ϊ
        NoteListFragment���������ListView����adapter��*/    
        setListAdapter(adapter);
        //����ʹ�õ��Ǽ̳�listFragment����
        
        setRetainInstance(true);
        mSubtitleVisible=false;
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		/*getListAdapter()������ListFragment��ı����������÷����ɷ���������ListFragment��
		�� �� ͼ �� �� adapter�� Ȼ �� ʹ ��onListItemClick(...) �� �� ��position �� �� �� �� adapter��
		getItem(int)���������ѽ��ת����Note����*/
		/*Note n = (Note) getListAdapter().getItem(position);*/
		
		Note n = ((NoteAdapter)getListAdapter()).getItem(position);
		
	     /*	Log.d(TAG,c.	() + "was clicked");*/
	    /*Intent  intent = new Intent(getActivity(), NoteActivity.class);*/
		Intent intent = new Intent(getActivity(),NotePagerActivity.class);
	     intent.putExtra(NoteFragment.EXTRA_NOTE_ID, n.getMid());
	     startActivity(intent);
	 }
	 /**ArrayAdapter������NoteAdapter��ΪNoteListFragmen����ڲ��ࡣ
	   * �ڲ���ӵ����Χ�������Ԫ�صķ���Ȩ��
	   * @author zhangxichao
	   *
	   */
   private class NoteAdapter extends ArrayAdapter<Note>{
		public NoteAdapter(ArrayList<Note> notes) {
//			��������ó���Ĺ��췽������Crime����������б����ڲ�����ʹ��Ԥ���岼�֣���
//			�Ǵ���0��Ϊ����ID������
			super(getActivity(), 0, notes);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		//Listview ����
			if (convertView==null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_note, null);
			}
			Note n = getItem(position);//��ȡ��ǰ���Noteʵ����
			TextView titleTextView =
				(TextView) convertView.findViewById(R.id.note_list_item_titletextView);
			
			titleTextView.setText(n.getTitle());
			TextView  dateTextView =
					(TextView) convertView.findViewById(R.id.note_list_item_datetextView);
			//ʱ���ʽ��
			SimpleDateFormat sdf = new SimpleDateFormat("EEE,yyyy-MM-dd HH:mm:ss");
	        String formattedDate = sdf.format(n.getDate());
	        dateTextView.setText(formattedDate);
			
			CheckBox solvdeCheckBox = 
					(CheckBox) convertView.findViewById(R.id.note_list_item_solvedCheckBox);
			solvdeCheckBox.setChecked(n.isSolved());
			
			return convertView;
		}
		
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		( (NoteAdapter)getListAdapter()).notifyDataSetChanged();
		
	}

	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
    }
	//���õ�Fragment�ķ���������ListFragment�ķ���
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_note_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle!=null) {
			showSubtitle.setTitle(R.string.hide_subtitle);
			
		}
	}
	//ʹ��@TargetApi(11)ע����ֹAndroid Lint������������⡣
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_note:
			Note note = new Note();
			NoteLab.get(getActivity()).addNote(note);
			Intent i = new Intent(getActivity(), NotePagerActivity.class);
			i.putExtra(NoteFragment.EXTRA_NOTE_ID, note.getMid());
			startActivityForResult(i, 0);
         //һ����ɲ˵����¼�����Ӧ����trueֵ�Ա�������ɲ˵���ѡ����Ҫ�����ȫ������
			return true;
			
			/*�����������û����ʾ�ӱ��⣬��Ӧ������ʾ�ӱ��⣬ͬʱ�л��˵�����⣬ʹ����ʾΪHide
			Subtitle������ӱ����Ѿ���ʾ����Ӧ������Ϊnullֵ��ͬʱ���˵�������л���Show Subtitle��*/
		  case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle()==null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				mSubtitleVisible = true;
				item.setTitle(R.string.hide_subtitle);
			}else{
				getActivity().getActionBar().setSubtitle(null);
				mSubtitleVisible = false;
				item.setTitle(R.string.show_subtitle);
			}
			
			return true;

		default:
			//����˵���ID�����ڣ�Ĭ�ϵĳ���汾�����ᱻ���á�
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			if (mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				
			}
		}
		
		
		//Ҫ�����˵��Ĵ������������registerForContextMenu
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(listView);
		}else {
			listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			//ʹ�õ���MultiChoiceModeListener�ӿڣ� ActionModeʵ�����Զ�������
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
		//�����ǴӲ���ģʽ������activity�л�ȡMenuInflater�ġ�
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.note_list_item_context, menu);
		//�ǵý����Ϊ����trueֵ����Ϊ����falseֵ�ᵼ�²���ģʽ����ʧ�ܡ�
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// TODO Auto-generated method stub
					switch (item.getItemId()) {
					case R.id.menu_item_delete_note :
						NoteAdapter adapter = (NoteAdapter) getListAdapter();
						NoteLab noteLab = NoteLab.get(getActivity());
						for (int i = adapter.getCount()-1; i >=0; i--) {
							if (getListView().isItemChecked(i)) {
				//��NoteLab��ɾ��һ������Note����
								noteLab.deleteNote(adapter.getItem(i));
								
							}
						}
				//����ActionMode.finish()����׼�����ٲ���ģʽ��
							mode.finish();
							adapter.notifyDataSetChanged();
							return true;
						
					default:
						return false;
					}
					
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		return v;
		 
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		/*//����onCreateOptionsMenu(...)���������ϲ˵��ص�����������MenuInflaterʵ������
		inflater.inflate(R.menu.fragment_note_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);*/
		
		getActivity().getMenuInflater().inflate(R.menu.note_list_item_context,
				menu);
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//��ΪListView��AdapterView������,����getMenuInfo()����������һ��
		//AdapterView.AdapterContextMenuInfoʵ����
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//��ȡѡ���б��������ݼ��е�λ����Ϣ��
		int position = info.position;
		NoteAdapter adapter = (NoteAdapter) getListAdapter();
		//���ʹ���б����λ�ã���ȡҪɾ����
		//Note����
		Note note = adapter.getItem(position);
		switch (item.getItemId()) {
		case R.id.menu_item_delete_note:
			NoteLab.get(getActivity()).deleteNote(note);
	        adapter.notifyDataSetChanged();
	        return true;
	        
		}
		
		return super.onContextItemSelected(item);
	}
	
	
	
	
}
