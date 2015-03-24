package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.adapter.FriendAdapter;
import com.example.demo.bean.Cheeses;
import com.example.demo.bean.Friend;
import com.example.demo.ui.QuickIndexBar;
import com.example.demo.ui.QuickIndexBar.OnLetterUpdateListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	List<Friend> mFrindsList;
	private TextView mCenterText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ininData();

	}

	private void ininData() {
		final ListView mList = (ListView) findViewById(R.id.list);
		QuickIndexBar mBar = (QuickIndexBar) findViewById(R.id.bar);
		mCenterText = (TextView) findViewById(R.id.tv_center);
		mBar.setOnLetterUpdateListener(new OnLetterUpdateListener() {

			@Override
			public void onLetterUpdate(String letter) {

				showLetter(letter);

				for (int i = 0; i < mFrindsList.size(); i++) {
					String str = mFrindsList.get(i).getPinyin().charAt(0) + "";
					if (TextUtils.equals(str, letter)) {
						// 找到第一个匹配的字母
						mList.setSelection(i);
						break;
					}
				}

			}
		});
		fillAndSortData();

		mList.setAdapter(new FriendAdapter(mFrindsList, MainActivity.this));

	}

	private Handler mHandler = new Handler();

	protected void showLetter(String letter) {
		mCenterText.setText(letter);
		mCenterText.setVisibility(View.VISIBLE);

		mHandler.removeCallbacksAndMessages(null);
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mCenterText.setVisibility(View.GONE);
			}
		}, 2000);
	}

	/**
	 * 填充并排序
	 */
	private void fillAndSortData() {
		mFrindsList = new ArrayList<Friend>();
		for (int i = 0; i < Cheeses.NAMES.length; i++) {
			mFrindsList.add(new Friend(Cheeses.NAMES[i]));
		}

		// 排序
		Collections.sort(mFrindsList);
	}
}
