package com.beautycare.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MallMainRecyAdapter extends RecyclerView.Adapter<MallMainRecyAdapter.ViewHolder>
        implements View.OnClickListener {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
//    private String[] mTitles;
    private DisplayImageOptions options;
//    private AVObject getObject;
//    private Data data;
    private mallListListener mallOnItemClickListner;
    private ArrayList<MallData> mallDataList;


    public MallMainRecyAdapter(Context context, ArrayList<MallData> tmpdatalist) {
        //mTitles = context.getResources().getStringArray(R.array.titles);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        mallDataList = tmpdatalist;


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

    public static interface mallListListener {

        void onItemClick(View v, MallData tmpdata);

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.mall_list_card_item, parent, false);
        v.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(v);
//        v.setOnClickListener(this);
//        return new ViewHolder(mLayoutInflater.inflate(R.layout.mall_list_card_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(/*mTitles[position]*/mallDataList.get(position).getMallName());
        Log.d("title", mallDataList.get(0).getMallName());
        ImageLoader.getInstance().displayImage(mallDataList.get(position).getMallLogoURL(), holder.mImageView, options);

        holder.itemView.setTag(mallDataList.get(position));
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
//    }

    @Override
    public int getItemCount() {

        return mallDataList.size();
    }

    @Override
    public void onClick(View v) {

        if (mallOnItemClickListner != null) {
            mallOnItemClickListner.onItemClick(v, (MallData)v.getTag());
        }
    }

    public void setOnItemClickListener(mallListListener listener) {
        this.mallOnItemClickListner = listener;
    }


    public class ViewHolder
            extends RecyclerView.ViewHolder {

        @Bind(R.id.mall_main_card_testname)
        TextView mTextView;

        @Bind(R.id.mall_mall_card_image)
        ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }

}