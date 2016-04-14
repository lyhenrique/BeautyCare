package com.beautycare.main.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Cycle 循环
 */
public class CycleViewPager extends ViewPager {


	private PagerAdapter adapter = new PagerAdapter() {
		
		private View cachedHeader, cachedBottom; /* 缓存相关 */ 
		
		public Object instantiateItem(ViewGroup container, int position) {

			View v = null;
			if(enabled()) { /* 缓存相关 */ 
				if(position == 1){
					if(cachedHeader != null && container.indexOfChild(cachedHeader) == -1){
						v = cachedHeader;
					}
					if(cachedBottom == null || container.indexOfChild(cachedBottom) != -1){
						cachedBottom = (View) adapter2.instantiateItem(container, adapter2.getCount() - 1); 
					}
				}else if(position == getCount() - 2){
					if(cachedBottom != null && container.indexOfChild(cachedBottom) == -1){
						v = cachedBottom;
					}
					if(cachedHeader == null || container.indexOfChild(cachedHeader) != -1){
						cachedHeader = (View) adapter2.instantiateItem(container, 0); 
					}
				}
			}
			
			if(v == null){
				v = (View) adapter2.instantiateItem(container, convert(position));
			}
			container.addView(v); // 用户适配器仅负责实例化即可
			return v;
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		public int getItemPosition(Object object) {
			return adapter2.getItemPosition(object); // 动态更新
		}
		
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			if(!edge(position))
				adapter2.setPrimaryItem(container, convert(position), object);
		}
		
		@Override
		public int getCount() {
			return enabled() ? adapter2.getCount() + 2 : adapter2 != null ? adapter2.getCount() : 0;
		}
		
	}, adapter2;
	

	private OnPageChangeListener listener = new OnPageChangeListener() {
		
		Handler handler = new Handler();
		
		@Override
		public void onPageSelected(int position) {
			if(listener2 != null && !edge(position)) 
				listener2.onPageSelected(convert(position));
		}
		
		@Override
		public void onPageScrolled(final int position, final float percent, int offsetPx) {
			if(listener2 != null && !edge(position)) 
				listener2.onPageScrolled(convert(position), percent, offsetPx);
			
			if(enabled() && percent == 0){
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						if(position == 0)
							CycleViewPager.super.setCurrentItem(( adapter.getCount() - 2 ) % adapter.getCount(), false); // 切到倒数第二页
						else if(position == adapter.getCount() - 1)
							CycleViewPager.super.setCurrentItem(1, false); // 切到正数第二页
					}
				});
			}
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			if(listener2 != null && !edge(CycleViewPager.super.getCurrentItem())) 
				listener2.onPageScrollStateChanged(state);
		}
		
	}, listener2;
	
	public CycleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CycleViewPager(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		super.setAdapter(adapter);
		super.setOnPageChangeListener(listener);
	}
	
	@Override
	public void setAdapter(PagerAdapter adapter) {
		this.adapter2 = adapter;
		if(enabled()) 
			super.setCurrentItem(1);
		this.adapter.notifyDataSetChanged();
	}
	
	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.listener2 = listener;
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(enabled() ? item + 1 : item);
	}
	
	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(enabled() ? item + 1 : item, smoothScroll);
	}
	
	@Override
	public int getCurrentItem() {
		return convert(super.getCurrentItem());
	}
	
	private int convert(int item){
		return enabled() ? item == 0 ? adapter2.getCount() - 1 : ( item > adapter2.getCount() ? 0 : item - 1 ): item;
	}
	
	private boolean enabled(){
		return adapter2 != null ? adapter2.getCount() > 1 : false;
	}
	
	private boolean edge(int position){
		return enabled() ? position == 0 || position == adapter.getCount() - 1 : false;
	}
}
