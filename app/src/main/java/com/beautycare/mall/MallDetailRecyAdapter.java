package com.beautycare.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.beautycare.R;

import butterknife.Bind;
import butterknife.ButterKnife;




public class MallDetailRecyAdapter extends RecyclerView.Adapter<MallDetailRecyAdapter.VH> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
//    private DisplayImageOptions options;
    private MallData mallData = new MallData();

    public MallDetailRecyAdapter(Context context, MallData tmpdatalist)

    {
        //mTitles = context.getResources().getStringArray(R.array.titles);
        mContext = context;
        mallData = tmpdatalist;
        mLayoutInflater = LayoutInflater.from(context);


//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .build();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {


        return new VH(mLayoutInflater.inflate(R.layout.mall_detail_card_item, parent, false));

    }


    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.mallDeName.setText(mallData.getMallName());
        holder.mallDeIntro.setText(mallData.getMallContent());
    }

    @Override
    public int getItemCount() {
//        return mTitles == null ? 0 : mTitles.length;
        return 1;
    }

    public class VH
            extends RecyclerView.ViewHolder{

        @Bind(R.id.mall_detail_card_textname)
        TextView mallDeName;

        @Bind(R.id.mall_detail_card_textintro)
        TextView mallDeIntro;


        public VH(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}