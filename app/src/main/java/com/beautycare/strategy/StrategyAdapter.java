package com.beautycare.strategy;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

public class StrategyAdapter extends BaseAdapter {
    private ArrayList<StrategyData> mList;
    private LayoutInflater mInflater;
    private ArrayList<String>  flipper;
    private Context c;
    private DisplayImageOptions options;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    public StrategyAdapter(Context context, ArrayList<StrategyData> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        c = context;

        options = new DisplayImageOptions.Builder()
                //          .showImageOnLoading(R.drawable.ic_stub)             dsc
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
                        //         .cacheOnDisk()              dsc
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    @Override
    //listview要显示的条数
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.strategy_item_style, null);
            //viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
            viewHolder.title = (TextView) convertView.findViewById(R.id.name);
            viewHolder.content = (TextView) convertView.findViewById(R.id.brand);
            viewHolder.more = (TextView) convertView.findViewById(R.id.more);
            viewHolder.spread = (ImageView) convertView.findViewById(R.id.spread);
            viewHolder.shrink_up = (ImageView) convertView.findViewById(R.id.shrink_up);
            viewHolder.viewFlipper = (ViewFlipper)convertView.findViewById(R.id.viewFlipper);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StrategyData bean = mList.get(position);
        //viewHolder.imageView.setImageResource(bean.getIconID());
        viewHolder.title.setText(bean.getItemTitle());
        viewHolder.content.setText(bean.getItemAbstract());
        viewHolder.more.setText(bean.getMore());
        viewHolder.spread.setImageResource(bean.getIconSpread());
        viewHolder.shrink_up.setImageResource(bean.getIconShrink_up());

        flipper = bean.getFlipper();

        for (int i = 0; i < flipper.size(); i++) {
            ImageView iv = new ImageView(c);

            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.viewFlipper.addView(iv, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            ImageLoader.getInstance().displayImage(flipper.get(i), iv, options);
        }

        viewHolder.viewFlipper.setAutoStart(true);
        viewHolder.viewFlipper.setFlipInterval(3000);
        viewHolder.viewFlipper.startFlipping();

        final String category = bean.getCategory();
        final String strategyTitle = bean.getItemTitle();
        final String strategyAbstract = bean.getItemAbstract();
        final String strategyDetails = bean.getItemDetails();
        final int mark = bean.getMark();
        final int like = bean.getLike();
        final ArrayList<String> flipper = bean.getFlipper();

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c.getApplicationContext(), StrategyDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category", category);
                bundle.putString("strategyTitle", strategyTitle);
                bundle.putString("strategyAbstract", strategyAbstract);
                bundle.putString("strategyDetails", strategyDetails);
                bundle.putInt("mark", mark);
                bundle.putInt("like", like);
                bundle.putString("FlipperImage1", flipper.get(0));
                bundle.putString("FlipperImage2", flipper.get(1));
                bundle.putString("FlipperImage3", flipper.get(2));

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c.getApplicationContext(), StrategyDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category", category);
                bundle.putString("strategyTitle", strategyTitle);
                bundle.putString("strategyAbstract", strategyAbstract);
                bundle.putString("strategyDetails", strategyDetails);
                bundle.putInt("mark", mark);
                bundle.putInt("like", like);
                bundle.putString("FlipperImage1", flipper.get(0));
                bundle.putString("FlipperImage2", flipper.get(1));
                bundle.putString("FlipperImage3", flipper.get(2));

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c.getApplicationContext(), StrategyDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category", category);
                bundle.putString("strategyTitle", strategyTitle);
                bundle.putString("strategyAbstract", strategyAbstract);
                bundle.putString("strategyDetails", strategyDetails);
                bundle.putInt("mark", mark);
                bundle.putInt("like", like);
                bundle.putString("FlipperImage1", flipper.get(0));
                bundle.putString("FlipperImage2", flipper.get(1));
                bundle.putString("FlipperImage3", flipper.get(2));

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.brand: {
                        if (mState == SPREAD_STATE) {
                            viewHolder.content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                            viewHolder.content.requestLayout();
                            viewHolder.shrink_up.setVisibility(View.GONE);
                            viewHolder.spread.setVisibility(View.VISIBLE);
                            mState = SHRINK_UP_STATE;
                        } else if (mState == SHRINK_UP_STATE) {
                            viewHolder.content.setMaxLines(Integer.MAX_VALUE);
                            viewHolder.content.requestLayout();
                            viewHolder.shrink_up.setVisibility(View.VISIBLE);
                            viewHolder.spread.setVisibility(View.GONE);
                            mState = SPREAD_STATE;
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.more: {
                        if (mState == SPREAD_STATE) {
                            viewHolder.content.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                            viewHolder.content.requestLayout();
                            viewHolder.shrink_up.setVisibility(View.GONE);
                            viewHolder.spread.setVisibility(View.VISIBLE);
                            mState = SHRINK_UP_STATE;
                        } else if (mState == SHRINK_UP_STATE) {
                            viewHolder.content.setMaxLines(Integer.MAX_VALUE);
                            viewHolder.content.requestLayout();
                            viewHolder.shrink_up.setVisibility(View.VISIBLE);
                            viewHolder.spread.setVisibility(View.GONE);
                            mState = SPREAD_STATE;
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        //public ImageView imageView;
        public TextView title;
        public TextView content;
        public TextView more;
        public ImageView spread;
        public ImageView shrink_up;
        public ViewFlipper viewFlipper;
        public RelativeLayout relativeLayout;
    }
}
