package com.example.recyclerview.adapter;

/*
 * @Title:  MyAdapter.java
 * @Copyright:  XXX Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * @Description:  TODO<>
 * @author:  wen_er
 * @data:  2015年6月5日 下午2:57:16
 * @version:  V1.0
 */

import java.util.ArrayList;
import java.util.List;

import com.example.recyclerview.bean.User;
import com.example.recyclerview.common.T;
import com.example.recyclerview.interfaces.ItemClickListener;
import com.example.teatbase.recyclerview.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TODO<>
 * 
 * @author wen_er
 * @data: 2015年6月5日 下午2:57:16
 * @version: V1.0
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MViewHolder> {

	private Context context;
	private List<User> listData;
	private ItemClickListener mItemClickListener;

	public MyAdapter(Context context, List<User> mList) {
		super();
		this.context = context;
		this.listData = mList;
	}

	/**
	 * TODO<添加数据，指定其位置>
	 */

	public void addData(User info, int position) {
		listData.add(position, info);
		notifyItemInserted(position);
	//	notifyDataSetChanged(); //不会触发Item的动画效果，告知数据改变，刷新UI
		
	}

	/**
	 * TODO<添加数据到最后面添加>
	 */

	public void addData(User info) {
		// listData.add(position, info);
		// notifyItemInserted(position);
		listData.add(info);
		notifyDataSetChanged();
	}

	/**
	 * TODO<删除数据，指定其位置>
	 */
	public void daleteData(int position) {
		listData.remove(position);
		notifyItemRemoved(position);
		
	}

	/**
	 * TODO<某一位置开始，有itemCount个Item的数据删除>
	 */
	public void itemRangeRemoved(int positionStart, int itemCount) {
		for (int i = positionStart; i < itemCount; i++) {
			listData.remove(positionStart);
		}
		notifyItemRangeRemoved(positionStart, itemCount);
	//	notifyDataSetChanged(); //不会触发Item的动画效果，告知数据改变，刷新UI
	}

	/**
	 * TODO<某一位置开始，有itemCount个Item的数据插入>
	 */
	public void itemRangeInserted(User info, int positionStart, int itemCount) {
		for (int i = positionStart; i < itemCount; i++) {
			listData.add(i, info);
		}
		//notifyItemRangeInserted(positionStart, itemCount);
		 notifyDataSetChanged();
	}

	public void setItemClickListener(ItemClickListener mItemClickListener) {

		this.mItemClickListener = mItemClickListener;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {

		View view = View.inflate(viewGroup.getContext(),
				R.layout.item_user_friend_nod, null);
		// 创建一个ViewHolder
		MViewHolder holder = new MViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(final MViewHolder mViewHolder,
			final int postion) {

		mViewHolder.mTextView.setText(listData.get(postion).getUsername());
		mViewHolder.image.setBackgroundResource(R.drawable.head);
		// 为image添加监听回调
		mViewHolder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mItemClickListener) {
					mItemClickListener.onItemSubViewClick(mViewHolder.image,
							postion);
				}

			}

		});

	}

	public class MViewHolder extends RecyclerView.ViewHolder {
		public TextView mTextView;
		public ImageView image;

		public MViewHolder(final View view) {
			super(view);
			this.mTextView = (TextView) view.findViewById(R.id.tv_friend_name);
			this.image = (ImageView) itemView
					.findViewById(R.id.img_friend_avatar);
			// 为item添加普通点击回调
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (null != mItemClickListener) {
						mItemClickListener.onItemClick(view, getPosition());
					}

				}
			});

			// 为item添加长按回调
			view.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					if (null != mItemClickListener) {
						mItemClickListener.onItemLongClick(view, getPosition());
					}
					return true;
				}
			});

		}
	}

}
