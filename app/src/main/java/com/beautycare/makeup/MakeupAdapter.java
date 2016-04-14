package com.beautycare.makeup;

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

import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

/**
 * Created by ShenLing on 2016/2/27.
 */
public class MakeupAdapter extends BaseAdapter {
    private ArrayList<MakeupData> mList;
    private LayoutInflater mInflater;
    private Context c;
    private DisplayImageOptions options;

    public MakeupAdapter(Context context, ArrayList<MakeupData> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        c=context;

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

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.makeup_item_style,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
            viewHolder.title = (TextView) convertView.findViewById(R.id.name);
            viewHolder.content = (TextView) convertView.findViewById(R.id.brand);

            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.item_layout);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        MakeupData bean = mList.get(position);
        //viewHolder.imageView.setImageResource(R.drawable.all);
        viewHolder.title.setText(bean.getMakeup_name());
        viewHolder.content.setText(bean.getBrand());

        ImageLoader.getInstance().displayImage(bean.getImages().get(0).getImage_url(), viewHolder.imageView, options);

        final String category = bean.getCategory();
        final String MakeupName = bean.getMakeup_name();
        final String brand = bean.getBrand();
        final String MakeupContent = bean.getMakeup_content();
        final String brandContent = bean.getBrand_content();
        final int mark = bean.getMark();
        final int price = bean.getPrice();
        final int like = bean.getLike();
        final ArrayList<String>location = bean.getLocation();
        final ArrayList<MakeupImage>images = bean.getImages();

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category",category);
                bundle.putString("MakeupName",MakeupName);
                bundle.putString("MakeupBrand",brand);

                bundle.putString("MakeupContent",MakeupContent);
                bundle.putString("brandContent",brandContent);
                bundle.putInt("mark", mark);
                bundle.putInt("price", price);
                bundle.putInt("like", like);
                bundle.putString("location1", location.get(0));
                bundle.putString("location2", location.get(1));
                bundle.putString("location3", location.get(2));
                bundle.putString("url1", images.get(0).getImage_url());
                bundle.putString("url2", images.get(1).getImage_url());
                bundle.putString("url3", images.get(2).getImage_url());
                bundle.putString("img_content1", images.get(0).getImage_content());
                bundle.putString("img_content2",images.get(1).getImage_content());
                bundle.putString("img_content3",images.get(2).getImage_content());

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category",category);
                bundle.putString("MakeupName",MakeupName);
                bundle.putString("MakeupBrand",brand);
                bundle.putString("MakeupContent",MakeupContent);
                bundle.putString("brandContent",brandContent);
                bundle.putInt("mark", mark);
                bundle.putInt("price", price);
                bundle.putInt("like", like);
                bundle.putString("location1", location.get(0));
                bundle.putString("location2",location.get(1));
                bundle.putString("location3",location.get(2));
                bundle.putString("url1",images.get(0).getImage_url());
                bundle.putString("url2",images.get(1).getImage_url());
                bundle.putString("url3",images.get(2).getImage_url());
                bundle.putString("img_content1",images.get(0).getImage_content());
                bundle.putString("img_content2",images.get(1).getImage_content());
                bundle.putString("img_content3",images.get(2).getImage_content());

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category",category);
                bundle.putString("MakeupName",MakeupName);
                bundle.putString("MakeupBrand",brand);
                bundle.putString("MakeupContent",MakeupContent);
                bundle.putString("brandContent",brandContent);
                bundle.putInt("mark", mark);
                bundle.putInt("price", price);
                bundle.putInt("like", like);
                bundle.putString("location1", location.get(0));
                bundle.putString("location2",location.get(1));
                bundle.putString("location3",location.get(2));
                bundle.putString("url1",images.get(0).getImage_url());
                bundle.putString("url2",images.get(1).getImage_url());
                bundle.putString("url3",images.get(2).getImage_url());
                bundle.putString("img_content1",images.get(0).getImage_content());
                bundle.putString("img_content2",images.get(1).getImage_content());
                bundle.putString("img_content3",images.get(2).getImage_content());

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });
        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                Bundle bundle = new Bundle();

                bundle.putString("category",category);
                bundle.putString("MakeupName",MakeupName);
                bundle.putString("MakeupBrand",brand);
                bundle.putString("MakeupContent",MakeupContent);
                bundle.putString("brandContent",brandContent);
                bundle.putInt("mark", mark);
                bundle.putInt("price", price);
                bundle.putInt("like", like);
                bundle.putString("location1", location.get(0));
                bundle.putString("location2",location.get(1));
                bundle.putString("location3",location.get(2));
                bundle.putString("url1",images.get(0).getImage_url());
                bundle.putString("url2",images.get(1).getImage_url());
                bundle.putString("url3",images.get(2).getImage_url());
                bundle.putString("img_content1",images.get(0).getImage_content());
                bundle.putString("img_content2",images.get(1).getImage_content());
                bundle.putString("img_content3",images.get(2).getImage_content());

                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        return convertView;
    }


    class ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView content;
        public RelativeLayout relativeLayout;
    }



}
