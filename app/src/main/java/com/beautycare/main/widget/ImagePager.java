/**
 * ImagePager.java
 * @author 苏德兵
 * @datetime 2015-7-9下午3:09:15
 */
package com.beautycare.main.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class ImagePager extends CycleViewPager {

	private int[] resIds;

	private PagerAdapter adapter = new PagerAdapter() {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(getContext());
			iv.setImageResource(resIds[position]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			return iv;
		}

		@Override
		public int getCount() {
			return resIds == null ? 0 : resIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return false;
		}
	};

	private OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (listener2 != null) {
				listener2.onPageScrollStateChanged(arg0);
			}

			switch (arg0) {
			case SCROLL_STATE_IDLE: // 闲置
				if (!handler.hasMessages(START_FLIPPING))
					handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 延时滚动
				break;
			case SCROLL_STATE_DRAGGING: // 拖动中
				handler.sendEmptyMessage(STOP_FLIPPING); // 取消滚动
				break;
			case SCROLL_STATE_SETTLING: // 拖动结束
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (listener2 != null) {
				listener2.onPageScrolled(arg0, arg1, arg2);
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			if (listener2 != null) {
				listener2.onPageSelected(arg0);
			}
		}
	}, listener2;

	private final int START_FLIPPING = 0;
	private final int STOP_FLIPPING = 1;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case START_FLIPPING:
				setCurrentItem(getCurrentItem() + 1);
				handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 延时滚动
				break;
			case STOP_FLIPPING:
				handler.removeMessages(START_FLIPPING);
				break;
			}
		}
	};

	public ImagePager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ImagePager(Context context) {
		super(context);
		init();
	}

	private void init() {

		// 切换滑动速度修改， 取自：http://my.oschina.net/javalover/blog/179003
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), new DecelerateInterpolator());
			field.set(this, scroller);
			scroller.setmDuration(400);
		} catch (Exception e) {
			
		}
		
		setOffscreenPageLimit(1); // 最大页面缓存数量
		super.setOnPageChangeListener(listener);
		
		handler.sendEmptyMessageDelayed(START_FLIPPING, 3000); // 自动滚动
	}

	public void setViews(int[] ids) {
		this.resIds = ids;
		setAdapter(adapter);
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.listener2 = listener;
	}

	public class FixedSpeedScroller extends Scroller {
		private int mDuration = 1500;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}
	}
}
