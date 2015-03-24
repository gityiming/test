package com.example.demo.adapter;

import java.util.List;

import com.example.demo.R;
import com.example.demo.bean.Friend;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendAdapter extends BaseAdapter {

	private List<Friend> mFrinds;
	private Context mContext;

	public FriendAdapter(List<Friend> mFrinds, Context mContext) {
		super();
		this.mFrinds = mFrinds;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mFrinds.size();
	}

	@Override
	public Object getItem(int position) {
		return mFrinds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view;
		if (convertView != null) {
			view = convertView;
		} else {
			view = View.inflate(mContext, R.layout.item_list, null);
		}
		ViewHolder mHolder = ViewHolder.getHolder(view);
		Friend friend = mFrinds.get(position);
		String currentLetter = friend.getPinyin().charAt(0) + "";

		String str = null;
		// 判断当前条目是否是所在组的第一条，如果是，则将索引赋给str
		if (position == 0) {
			str = currentLetter;
		} else {
			String pinyin = mFrinds.get(position - 1).getPinyin();
			String lastLetter = pinyin.charAt(0) + "";
			// 如果跟上一条目的首字母不一致。
			if (!TextUtils.equals(lastLetter, currentLetter)) {
				str = currentLetter;
			}
		}

		mHolder.mIndexer.setVisibility(str == null ? View.GONE : View.VISIBLE);
		mHolder.mIndexer.setText(currentLetter);
		mHolder.mName.setText(friend.getName());

		return view;
	}

	static class ViewHolder {
		TextView mIndexer;
		TextView mName;

		public ViewHolder(TextView mIndexer, TextView mName) {
			super();
			this.mIndexer = mIndexer;
			this.mName = mName;
		}

		public static ViewHolder getHolder(View view) {
			Object tag = view.getTag();
			if (tag != null) {
				return (ViewHolder) tag;
			} else {
				return new ViewHolder(
						(TextView) view.findViewById(R.id.tv_indexer),
						(TextView) view.findViewById(R.id.tv_name));
			}
		}

	}

}
