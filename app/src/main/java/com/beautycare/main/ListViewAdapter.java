package com.beautycare.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
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
import com.beautycare.makeup.MakeupDetails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    List  ss = new ArrayList();

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
                //onclick
                ListViewItem bean = mArrayList.get(position);

                final String category = bean.getCategory();
                final String MakeupName = bean.getName();
                final String brand = bean.getBrand();
                final String MakeupContent = bean.getMakeup_content();
                final String brandContent = bean.getBrand_content();
                final int mark = bean.getMark();
                final int price = bean.getPrice();
                final int like = Integer.valueOf(bean.getLike());
                final String location1 = bean.getLocation1();
                final String location2 = bean.getLocation2();
                final String location3 = bean.getLocation3();
                final String image_url0 = bean.getImageURL0();
                final String image_url1 = bean.getImageURL1();
                final String image_url2 = bean.getImageURL2();

                final String img_content1 = bean.getImg_content1();
                final String img_content2 = bean.getImg_content2();
                final String img_content3 = bean.getImg_content3();

                holder.textView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext.getApplicationContext(),MakeupDetails.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("category",category);
                        bundle.putString("MakeupName",MakeupName);
                        bundle.putString("MakeupBrand",brand);

                        bundle.putString("MakeupContent",MakeupContent);
                        bundle.putString("brandContent",brandContent);
                        bundle.putInt("mark", mark);
                        bundle.putInt("price", price);
                        bundle.putInt("like", like);
                        bundle.putString("location1", location1);
                        bundle.putString("location2", location2);
                        bundle.putString("location3",location3);
                        bundle.putString("url1",image_url0);
                        bundle.putString("url2",image_url1);
                        bundle.putString("url3",image_url2);
                        bundle.putString("img_content1",img_content1);
                        bundle.putString("img_content2",img_content2);
                        bundle.putString("img_content3",img_content3);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
                holder.imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext.getApplicationContext(),MakeupDetails.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("category",category);
                        bundle.putString("MakeupName",MakeupName);
                        bundle.putString("MakeupBrand",brand);

                        bundle.putString("MakeupContent",MakeupContent);
                        bundle.putString("brandContent",brandContent);
                        bundle.putInt("mark", mark);
                        bundle.putInt("price", price);
                        bundle.putInt("like", like);
                        bundle.putString("location1", location1);
                        bundle.putString("location2", location2);
                        bundle.putString("location3",location3);
                        bundle.putString("url1",image_url0);
                        bundle.putString("url2",image_url1);
                        bundle.putString("url3",image_url2);
                        bundle.putString("img_content1",img_content1);
                        bundle.putString("img_content2",img_content2);
                        bundle.putString("img_content3",img_content3);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
                holder.textView1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext.getApplicationContext(),MakeupDetails.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("category",category);
                        bundle.putString("MakeupName",MakeupName);
                        bundle.putString("MakeupBrand",brand);

                        bundle.putString("MakeupContent",MakeupContent);
                        bundle.putString("brandContent",brandContent);
                        bundle.putInt("mark", mark);
                        bundle.putInt("price", price);
                        bundle.putInt("like", like);
                        bundle.putString("location1", location1);
                        bundle.putString("location2", location2);
                        bundle.putString("location3",location3);
                        bundle.putString("url1",image_url0);
                        bundle.putString("url2",image_url1);
                        bundle.putString("url3",image_url2);
                        bundle.putString("img_content1",img_content1);
                        bundle.putString("img_content2",img_content2);
                        bundle.putString("img_content3",img_content3);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
                holder.textView2.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext.getApplicationContext(),MakeupDetails.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("category",category);
                        bundle.putString("MakeupName",MakeupName);
                        bundle.putString("MakeupBrand",brand);

                        bundle.putString("MakeupContent",MakeupContent);
                        bundle.putString("brandContent",brandContent);
                        bundle.putInt("mark", mark);
                        bundle.putInt("price", price);
                        bundle.putInt("like", like);
                        bundle.putString("location1", location1);
                        bundle.putString("location2", location2);
                        bundle.putString("location3",location3);
                        bundle.putString("url1",image_url0);
                        bundle.putString("url2",image_url1);
                        bundle.putString("url3",image_url2);
                        bundle.putString("img_content1",img_content1);
                        bundle.putString("img_content2",img_content2);
                        bundle.putString("img_content3",img_content3);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            }
            else if (flag == 2)
            {
                convertView= LayoutInflater.from(this.mContext).inflate(R.layout.item_collect_style, null, false);
                holder.textView=(TextView) convertView.findViewById(R.id.c_name);
                holder.textView2=(TextView) convertView.findViewById(R.id.c_delete);
                holder.imageView=(ImageView) convertView.findViewById(R.id.c_ItemImage);
                convertView.setTag(holder);

                //onclick
                ListViewItem bean = mArrayList.get(position);

                final String category = bean.getCategory();
                final String MakeupName = bean.getName();
                final String brand = bean.getBrand();
                final String MakeupContent = bean.getMakeup_content();
                final String brandContent = bean.getBrand_content();
                final int mark = bean.getMark();
                final int price = bean.getPrice();
                final int like = Integer.valueOf(bean.getLike());
                final String location1 = bean.getLocation1();
                final String location2 = bean.getLocation2();
                final String location3 = bean.getLocation3();
                final String image_url0 = bean.getImageURL0();
                final String image_url1 = bean.getImageURL1();
                final String image_url2 = bean.getImageURL2();

                final String img_content1 = bean.getImg_content1();
                final String img_content2 = bean.getImg_content2();
                final String img_content3 = bean.getImg_content3();

                holder.textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openOptionsDialog(MakeupName);
                    }
                });

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext.getApplicationContext(), com.beautycare.makeup.MakeupDetails.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("category", category);
                        bundle.putString("MakeupName", MakeupName);
                        bundle.putString("MakeupBrand", brand);

                        bundle.putString("MakeupContent", MakeupContent);
                        bundle.putString("brandContent", brandContent);
                        bundle.putInt("mark", mark);
                        bundle.putInt("price", price);
                        bundle.putInt("like", like);
                        bundle.putString("location1", location1);
                        bundle.putString("location2", location2);
                        bundle.putString("location3", location3);
                        bundle.putString("url1", image_url0);
                        bundle.putString("url2", image_url1);
                        bundle.putString("url3", image_url2);
                        bundle.putString("img_content1", img_content1);
                        bundle.putString("img_content2", img_content2);
                        bundle.putString("img_content3", img_content3);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });


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
                    holder.textView.setText(listViewItem.getName());
                }
            }
            if (holder.imageView!=null) {
                try {
                    //加载图片
                    mImageLoader.displayImage(
                            listViewItem.getImageURL0(),
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

    private void load_s(String makeupName) {
        try {
//--- SD Storage ---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsoluteFile() + "/MyFiles");
            File file = new File(directory, "textfile_s.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line; // 一行数据
            int row = 0;
            while ((line = in.readLine()) != null) {
                String[] temp = line.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    ss.add(temp[j]);
                }
            }
            for (int i = 0 ; i<ss.size() ; i++){
                if(ss.get(i).equals(makeupName))
                {
                    ss.remove(i);
                    i--;
                }
            }
//--- Set the EditText to the text that has been read ---
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    public void save(){

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
            directory.mkdirs();
            File file = new File(directory, "textfile_s2.txt");
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
// --- Write the string to the file ---
            HashSet hSet  =   new  HashSet(ss);
            ss.clear();
            ss.addAll(hSet);
            for (int i =0 ;i< ss.size();i++){
                osw.write(ss.get(i)+"\t");
            }
            osw.flush();
            osw.close();
//--- Set the EditText to the text that has been read ---
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void openOptionsDialog(final String MakeupName) {
        new AlertDialog.Builder(mContext)
                .setTitle("Make sure")
                .setMessage("Are you sure to Delete")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                load_s(MakeupName);
                                save();
                            }
                        })
                .setNegativeButton("No",null)
                .create()
                .show();
    }


}
