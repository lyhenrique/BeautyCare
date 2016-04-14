package com.beautycare.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by owner on 2016/3/10.
 */
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> mArrayList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoadingListenerImpl mImageLoadingListenerImpl;
    int flag;

    public ListViewAdapter(ArrayList<ListViewItem> arrayList, Context context, ImageLoader imageLoader) {
        super();
        this.mArrayList = arrayList;
        flag =arrayList.get(0).getFlag() ;
        this.mContext = context;
        this.mImageLoader = imageLoader;
        int defaultImageId = R.drawable.android_normal;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(defaultImageId)
                .showImageForEmptyUri(defaultImageId)
                .showImageOnFail(defaultImageId)
                .cacheInMemory()
                .cacheOnDisc()
                .resetViewBeforeLoading()
                .build();
        mImageLoadingListenerImpl=new ImageLoadingListenerImpl();
    }

    public int getCount() {
        if (mArrayList==null) {
            return 0;
        } else {
            return mArrayList.size();
        }

    }

    public Object getItem(int position) {
        if (mArrayList==null) {
            return null;
        } else {
            return mArrayList.get(position);
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null) {
            holder=new ViewHolder();
            if(flag == 1) {
                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_style, null, false);
                holder.textView = (TextView) convertView.findViewById(R.id.num);
                holder.textView1 = (TextView) convertView.findViewById(R.id.brand);
                holder.textView2 = (TextView) convertView.findViewById(R.id.name);
                holder.textView3 = (TextView) convertView.findViewById(R.id.like);
                holder.imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
                holder.imageView1 = (ImageView) convertView.findViewById(R.id.prvImage);
                convertView.setTag(holder);
            }
            else if (flag == 2)
            {
                convertView= LayoutInflater.from(this.mContext).inflate(R.layout.item_collect_style, null, false);
                holder.textView2=(TextView) convertView.findViewById(R.id.c_name);
                holder.imageView=(ImageView) convertView.findViewById(R.id.c_ItemImage);
                convertView.setTag(holder);
            }
        } else {
            holder=(ViewHolder) convertView.getTag();
        }

        if (this.mArrayList!=null) {
            ListViewItem listViewItem=this.mArrayList.get(position);
            if (holder.textView!=null) {
                if(flag == 1) {
                    holder.textView.setText(listViewItem.getNum());
                    holder.textView1.setText(listViewItem.getBrand());
                    holder.textView2.setText(listViewItem.getName());
                    holder.textView3.setText(listViewItem.getLike());
                    holder.imageView1.setTag(listViewItem.getIcon());
                }
                else if (flag ==2 ){
                    holder.textView2.setText(listViewItem.getName());
                }
            }
            if (holder.imageView!=null) {
                try {
                    //加载图片
                    mImageLoader.displayImage(
                            listViewItem.getImageURL(),
                            holder.imageView,
                            mDisplayImageOptions,
                            mImageLoadingListenerImpl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return convertView;
    }

    //监听图片异步加载
    public static class ImageLoadingListenerImpl extends SimpleImageLoadingListener {

        public static final List<String> displayedImages =
                Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) view;
                boolean isFirstDisplay = !displayedImages.contains(imageUri);
                if (isFirstDisplay) {
                    //图片的淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                    System.out.println("===> loading "+imageUri);
                }
            }
        }
    }

    private class ViewHolder{
        ImageView imageView;
        ImageView imageView1;
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }
}
