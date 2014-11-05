package com.prakashd.simpleviewpager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager viewPager;
	private ViewPagerAdapter pagerAdapter;
	private ArrayList<Integer> listOfItems;
	
	private LinearLayout bulletParentLayout;
	private int bulletCount;
	private TextView[] bulletTextview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//For full screen mode.
		setContentView(R.layout.activity_main);
		
		initViews();
		setViewPagerItemsWithAdapter();
		setUiPageViewController();
	}

	private void setViewPagerItemsWithAdapter() {
		pagerAdapter = new ViewPagerAdapter(listOfItems);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(viewPagerPageChangeListener);
	}
	
	private void setUiPageViewController() {
		bulletParentLayout = (LinearLayout)findViewById(R.id.bullet_parent_layout);
		bulletCount = pagerAdapter.getCount();// To create bullet as per page size
		bulletTextview = new TextView[bulletCount];//Adding text view to show dots
		
		for (int i = 0; i < bulletCount; i++) {
			bulletTextview[i] = new TextView(this);
			bulletTextview[i].setText(Html.fromHtml("&#8226;"));//html bullet
			bulletTextview[i].setTextSize(30);
			bulletTextview[i].setTextColor(getResources().getColor(R.color.highlighter_color));//Setting green color for other dots
			bulletParentLayout.addView(bulletTextview[i]);//Adding text view in liner layout, to show number of dots.		
		}
		
		bulletTextview[0].setTextColor(getResources().getColor(R.color.white_color));//Setting white color for initial dot
	}
	
	OnPageChangeListener viewPagerPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			for (int i = 0; i < bulletCount; i++) {
				bulletTextview[i].setTextColor(getResources().getColor(R.color.highlighter_color));
			}
			bulletTextview[position].setTextColor(getResources().getColor(R.color.white_color));
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};

	private void initViews() {
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		
		listOfItems = new ArrayList<Integer>();
		listOfItems.add(1);
		listOfItems.add(2);
		listOfItems.add(3);
		listOfItems.add(4);
		listOfItems.add(5);
	}
	
	public class ViewPagerAdapter extends PagerAdapter{
		
		private LayoutInflater layoutInflater;
		private ArrayList<Integer> items;

		public ViewPagerAdapter(ArrayList<Integer> listOfItems) {
			this.items = listOfItems;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.child_item, container, false);//Inflating child view 
			
			TextView tView = (TextView)view.findViewById(R.id.page_count);
			tView.setText("Screen position: " +listOfItems.get(position).toString());
			((ViewPager) container).addView(view);
			
			return view;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == ((View)obj);
		}
		
				
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View)object;
	        ((ViewPager) container).removeView(view);
		}
	}
}