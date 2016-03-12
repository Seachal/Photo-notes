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
		//通知FragmentManager： NoteListFragment-
		//需接收选项菜单方法回调
		setHasOptionsMenu(true);
		/*getActivity()方法，该Fragment便利方法不仅可以返回托管activity，且允许
		fragment处理更多的activity相关事务。*/
		getActivity().setTitle(R.string.note_title);
		
		mNotes = NoteLab.get(getActivity()).getNotes();//先获取NoteLab单例，再获取其中的note列表，
	
      /* ArrayAdapter<Note> adapter =  new ArrayAdapter<Note>(getActivity(),
    		   android.R.layout.simple_list_item_1,mNotes);*/
		//注意参数已经由Note变为了mNotes，
		NoteAdapter adapter = new NoteAdapter(mNotes);
		/*setListAdapter(ListAdapter) 是一个 ListFragment类的便利方 法 ，使用它可为
        NoteListFragment管理的内置ListView设置adapter。*/    
        setListAdapter(adapter);
        //我们使用的是继承listFragment的类
        
        setRetainInstance(true);
        mSubtitleVisible=false;
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		/*getListAdapter()方法是ListFragment类的便利方法，该方法可返回设置在ListFragment列
		表 视 图 上 的 adapter。 然 后， 使 用onListItemClick(...) 方 法 的position 参 数 调 用 adapter的
		getItem(int)方法，最后把结果转换成Note对象。*/
		/*Note n = (Note) getListAdapter().getItem(position);*/
		
		Note n = ((NoteAdapter)getListAdapter()).getItem(position);
		
	     /*	Log.d(TAG,c.	() + "was clicked");*/
	    /*Intent  intent = new Intent(getActivity(), NoteActivity.class);*/
		Intent intent = new Intent(getActivity(),NotePagerActivity.class);
	     intent.putExtra(NoteFragment.EXTRA_NOTE_ID, n.getMid());
	     startActivity(intent);
	 }
	 /**ArrayAdapter的子类NoteAdapter作为NoteListFragmen类的内部类。
	   * 内部类拥有外围类的所有元素的访问权限
	   * @author zhangxichao
	   *
	   */
   private class NoteAdapter extends ArrayAdapter<Note>{
		public NoteAdapter(ArrayList<Note> notes) {
//			这里需调用超类的构造方法来绑定Crime对象的数组列表。由于不打算使用预定义布局，我
//			们传入0作为布局ID参数。
			super(getActivity(), 0, notes);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		//Listview 重用
			if (convertView==null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item_note, null);
			}
			Note n = getItem(position);//获取当前项的Note实例。
			TextView titleTextView =
				(TextView) convertView.findViewById(R.id.note_list_item_titletextView);
			
			titleTextView.setText(n.getTitle());
			TextView  dateTextView =
					(TextView) convertView.findViewById(R.id.note_list_item_datetextView);
			//时间格式化
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
	//调用的Fragment的方法而不是ListFragment的方法
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
	//使用@TargetApi(11)注解阻止Android Lint报告兼容性问题。
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
         //一旦完成菜单项事件处理，应返回true值以表明已完成菜单项选择需要处理的全部任务。
			return true;
			
			/*如果操作栏上没有显示子标题，则应设置显示子标题，同时切换菜单项标题，使其显示为Hide
			Subtitle。如果子标题已经显示，则应设置其为null值，同时将菜单项标题切换回Show Subtitle。*/
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
			//如果菜单项ID不存在，默认的超类版本方法会被调用。
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
		
		
		//要触发菜单的创建，必须调用registerForContextMenu
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(listView);
		}else {
			listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			//使用的是MultiChoiceModeListener接口， ActionMode实例是自动创建的
				
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
		//我们是从操作模式，而非activity中获取MenuInflater的。
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.note_list_item_context, menu);
		//记得将其改为返回true值，因为返回false值会导致操作模式创建失败。
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
				//从NoteLab中删除一个或多个Note对象
								noteLab.deleteNote(adapter.getItem(i));
								
							}
						}
				//调用ActionMode.finish()方法准备销毁操作模式。
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
		/*//不像onCreateOptionsMenu(...)方法，以上菜单回调方法不接受MenuInflater实例参数
		inflater.inflate(R.menu.fragment_note_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);*/
		
		getActivity().getMenuInflater().inflate(R.menu.note_list_item_context,
				menu);
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//因为ListView是AdapterView的子类,所以getMenuInfo()方法返回了一个
		//AdapterView.AdapterContextMenuInfo实例。
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		//获取选中列表项在数据集中的位置信息。
		int position = info.position;
		NoteAdapter adapter = (NoteAdapter) getListAdapter();
		//最后，使用列表项的位置，获取要删除的
		//Note对象。
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
