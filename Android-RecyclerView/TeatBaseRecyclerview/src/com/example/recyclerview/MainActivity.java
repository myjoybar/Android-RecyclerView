package com.example.recyclerview;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

import java.util.ArrayList;
import java.util.List;

import com.example.recyclerview.adapter.MyAdapter;
import com.example.recyclerview.bean.User;
import com.example.recyclerview.common.T;
import com.example.recyclerview.interfaces.ItemClickListener;
import com.example.teatbase.recyclerview.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static final String TAG = MainActivity.class.getSimpleName();

	Context context = MainActivity.this;
	private TextView tv, tv_state;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

	private RecyclerView recyclerView;
	private MyAdapter adapter;
	private LinearLayoutManager layoutManager;
	private GridLayoutManager gridLayoutManager;
	private StaggeredGridLayoutManager StaggeredGridLayoutManager;

	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setLinstener();
		initData();
		fillData();
		setRecyclerView(LinearLayoutManager.VERTICAL);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn1:
			type = 0;
			setRecyclerView(LinearLayoutManager.VERTICAL);

			break;
		case R.id.btn2:
			type = 0;
			setRecyclerView(LinearLayoutManager.HORIZONTAL);
			break;
		case R.id.btn3:
			type = 0;
			User uBean = new User();
			uBean.setUsername("我是增加的Item");
			adapter.addData(uBean, 1);// 添加到第二个
			break;
		case R.id.btn4:
			type = 0;
			adapter.daleteData(1); // 删除第二个
			break;
		case R.id.btn5:
			//Demo 只是测试RecyclerView功能，注意可能发生数组下标越界错误
			type = 0;
			User uBean1 = new User();
			uBean1.setUsername("我是连续添加的Item");
			adapter.itemRangeInserted(uBean1, 1, 5);
			break;
		case R.id.btn6:
			//Demo 只是测试RecyclerView功能，注意可能发生数组下标越界错误
			type = 0;
			adapter.itemRangeRemoved(1, 5);
			break;
		case R.id.btn7:
			type = 1;
			setGridLayoutRecyclerView();
			break;
		case R.id.btn8:
			type = 2;
			setStaggeredGridLayoutRecyclerView();
			break;

		default:
			break;
		}

	}

	protected void initData() {

	}

	protected void initView() {
		btn1 = (Button) this.findViewById(R.id.btn1);
		btn2 = (Button) this.findViewById(R.id.btn2);
		btn3 = (Button) this.findViewById(R.id.btn3);
		btn4 = (Button) this.findViewById(R.id.btn4);
		btn5 = (Button) this.findViewById(R.id.btn5);
		btn6 = (Button) this.findViewById(R.id.btn6);
		btn7 = (Button) this.findViewById(R.id.btn7);
		btn8 = (Button) this.findViewById(R.id.btn8);
		tv = (TextView) this.findViewById(R.id.tv);
		tv_state = (TextView) this.findViewById(R.id.tv_state);
		recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);

	}

	protected void setLinstener() {

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int scrollState) {
				updateState(scrollState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int i, int i2) {
				String s = "";
				if (type == 0) {
					s = "可见Item数量：" + layoutManager.getChildCount() + "\n"
							+ "可见Item第一个Position："
							+ layoutManager.findFirstVisibleItemPosition()
							+ "\n" + "可见Item最后一个Position："
							+ layoutManager.findLastVisibleItemPosition();

				} else if (type == 1) {
					s = "可见Item数量：" + gridLayoutManager.getChildCount() + "\n"
							+ "可见Item第一个Position："
							+ gridLayoutManager.findFirstVisibleItemPosition()
							+ "\n" + "可见Item最后一个Position："
							+ gridLayoutManager.findLastVisibleItemPosition();
				} else {
					s = "可见Item数量："
							+ StaggeredGridLayoutManager.getChildCount();

				}
				tv.setText(s);
			}
		});

	}

	private void updateState(int scrollState) {
		String stateName = "Undefined";
		switch (scrollState) {
		case SCROLL_STATE_IDLE:
			stateName = "Idle";
			break;

		case SCROLL_STATE_DRAGGING:
			stateName = "Dragging";
			break;

		case SCROLL_STATE_SETTLING:
			stateName = "Flinging";
			break;
		}

		tv_state.setText("滑动状态：" + stateName);
	}

	protected void fillData() {
		// setRecyclerView(VERTICAL);

	}

	public void setRecyclerView(int oritation) {

		// 如果布局大小一致有利于优化
		recyclerView.setHasFixedSize(true);

		// 创建一个线性布局管理器
		layoutManager = new LinearLayoutManager(this);

		switch (oritation) {
		case LinearLayoutManager.VERTICAL:

			layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

			break;
		case LinearLayoutManager.HORIZONTAL:

			layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

			break;

		default:
			break;
		}

		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		// 创建数据集
		List<User> listData = new ArrayList<User>();
		for (int i = 0; i < 20; ++i) {
			User uBean = new User();
			uBean.setUsername("我是Item" + i);
			listData.add(uBean);
		}
		// 使用RecyclerView提供的默认的动画效果
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		// 为Item添加分割线
		recyclerView.addItemDecoration(new ItemDecorationDivider(context,
				R.drawable.item_divider, oritation));
		// recyclerView.addItemDecoration(new
		// DividerItemDecoration(context,oritation));

		// 创建Adapter，并指定数据集
		adapter = new MyAdapter(context, listData);
		// 为Item具体实例点击3种事件
		adapter.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemSubViewClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Image" + postion);

			}

			@Override
			public void onItemLongClick(View view, int postion) {
				T.showShort(context, "亲，你长按了Item" + postion);

			}

			@Override
			public void onItemClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Item" + postion);

			}
		});
		// 设置Adapter
		recyclerView.setAdapter(adapter);
	}

	private void setGridLayoutRecyclerView() {

		gridLayoutManager = new GridLayoutManager(this, 3,
				GridLayoutManager.VERTICAL, false);
		// 设置布局管理器
		recyclerView.setLayoutManager(gridLayoutManager);
		// 创建数据集
		List<User> listData = new ArrayList<User>();
		for (int i = 0; i < 20; ++i) {
			User uBean = new User();
			uBean.setUsername("我是Item" + i);
			listData.add(uBean);
		}
		// 使用RecyclerView提供的默认的动画效果
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		// 为Item添加分割线
		recyclerView.addItemDecoration(new ItemDecorationDivider(context,
				R.drawable.item_divider, LinearLayoutManager.VERTICAL));
		recyclerView.addItemDecoration(new ItemDecorationDivider(context,
				R.drawable.item_divider, LinearLayoutManager.HORIZONTAL));
		// recyclerView.addItemDecoration(new
		// DividerItemDecoration(context,oritation));

		// 创建Adapter，并指定数据集
		adapter = new MyAdapter(context, listData);
		// 为Item具体实例点击3种事件
		adapter.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemSubViewClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Image" + postion);

			}

			@Override
			public void onItemLongClick(View view, int postion) {
				T.showShort(context, "亲，你长按了Item" + postion);

			}

			@Override
			public void onItemClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Item" + postion);

			}
		});
		// 设置Adapter
		recyclerView.setAdapter(adapter);
	}

	private void setStaggeredGridLayoutRecyclerView() {

		StaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(StaggeredGridLayoutManager);
		// 创建数据集
		List<User> listData = new ArrayList<User>();
		for (int i = 0; i < 20; ++i) {
			User uBean = new User();
			uBean.setUsername("我是Item" + i);
			listData.add(uBean);
		}
		// 使用RecyclerView提供的默认的动画效果
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		// 为Item添加分割线
		recyclerView.addItemDecoration(new ItemDecorationDivider(context,
				R.drawable.item_divider, LinearLayoutManager.VERTICAL));
		recyclerView.addItemDecoration(new ItemDecorationDivider(context,
				R.drawable.item_divider, LinearLayoutManager.HORIZONTAL));
		// recyclerView.addItemDecoration(new
		// DividerItemDecoration(context,oritation));

		// 创建Adapter，并指定数据集
		adapter = new MyAdapter(context, listData);
		// 为Item具体实例点击3种事件
		adapter.setItemClickListener(new ItemClickListener() {

			@Override
			public void onItemSubViewClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Image" + postion);

			}

			@Override
			public void onItemLongClick(View view, int postion) {
				T.showShort(context, "亲，你长按了Item" + postion);

			}

			@Override
			public void onItemClick(View view, int postion) {
				T.showShort(context, "亲，你点击了Item" + postion);

			}
		});
		// 设置Adapter
		recyclerView.setAdapter(adapter);
	}
}
