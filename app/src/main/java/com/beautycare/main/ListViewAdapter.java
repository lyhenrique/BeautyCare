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
import com.beautycare.makeup.MakeupData;
import com.beautycare.makeup.MakeupImage;
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
    private ArrayList<MakeupData> mArrayList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoadingListenerImpl mImageLoadingListenerImpl;
    int flag;
    List  ss = new ArrayList();

    private Context c;

    public ListViewAdapter(int flag, ArrayList<MakeupData> arrayList, Context context, ImageLoader imageLoader) {
        super();
        c=context;
        this.mArrayList = arrayList;
        this.flag = flag;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        MakeupData bean = mArrayList.get(position);
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

        if(flag == 1) //rank list fragment test
        {
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_style,null);
                holder.textView = (TextView) convertView.findViewById(R.id.num);
                holder.textView1 = (TextView) convertView.findViewById(R.id.brand);
                holder.textView2 = (TextView) convertView.findViewById(R.id.name);
                holder.textView3 = (TextView) convertView.findViewById(R.id.like);
                holder.imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
                holder.imageView1 = (ImageView) convertView.findViewById(R.id.prvImage);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.textView.setText("No." + String.valueOf(position + 1));
            holder.textView1.setText(brand);
            holder.textView2.setText(MakeupName);
            holder.textView3.setText(String.valueOf(like));
            holder.imageView1.setTag(R.drawable.good_icon);
            ImageLoader.getInstance().displayImage(bean.getImages().get(0).getImage_url(), holder.imageView, mDisplayImageOptions);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(), MakeupDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("category", category);
                    bundle.putString("MakeupName", MakeupName);
                    bundle.putString("MakeupBrand", brand);

                    bundle.putString("MakeupContent", MakeupContent);
                    bundle.putString("brandContent", brandContent);
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
                    bundle.putString("img_content2", images.get(1).getImage_content());
                    bundle.putString("img_content3", images.get(2).getImage_content());

                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("category",category);
                    bundle.putString("MakeupName",MakeupName);
                    bundle.putString("MakeupBrand", brand);

                    bundle.putString("MakeupContent", MakeupContent);
                    bundle.putString("brandContent", brandContent);
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
                    bundle.putString("img_content2", images.get(1).getImage_content());
                    bundle.putString("img_content3", images.get(2).getImage_content());

                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            });
            holder.textView1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("category",category);
                    bundle.putString("MakeupName",MakeupName);
                    bundle.putString("MakeupBrand",brand);

                    bundle.putString("MakeupContent",MakeupContent);
                    bundle.putString("brandContent", brandContent);
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
                    bundle.putString("img_content3", images.get(2).getImage_content());

                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            });
            holder.textView2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("category",category);
                    bundle.putString("MakeupName",MakeupName);
                    bundle.putString("MakeupBrand",brand);

                    bundle.putString("MakeupContent",MakeupContent);
                    bundle.putString("brandContent", brandContent);
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
                    bundle.putString("img_content3", images.get(2).getImage_content());

                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            });
            return convertView;
        }
        else if(flag == 2){
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_collect_style,null);
                holder.textView=(TextView) convertView.findViewById(R.id.c_name);
                holder.textView2=(TextView) convertView.findViewById(R.id.c_delete);
                holder.imageView=(ImageView) convertView.findViewById(R.id.c_ItemImage);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(MakeupName);
            ImageLoader.getInstance().displayImage(bean.getImages().get(0).getImage_url(), holder.imageView, mDisplayImageOptions);

            holder.textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openOptionsDialog(MakeupName);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(c.getApplicationContext(),MakeupDetails.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("category",category);
                    bundle.putString("MakeupName",MakeupName);
                    bundle.putString("MakeupBrand",brand);

                    bundle.putString("MakeupContent",MakeupContent);
                    bundle.putString("brandContent", brandContent);
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
                    bundle.putString("img_content3", images.get(2).getImage_content());

                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            });
            return convertView;
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
                                Intent intent = new Intent(c,FirstActivity.class);
                                c.startActivity(intent);
                            }
                        })
                .setNegativeButton("No",null)
                .create()
                .show();
    }


}
