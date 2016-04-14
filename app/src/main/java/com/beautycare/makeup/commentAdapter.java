package com.beautycare.makeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

/**
 * Created by ShenLing on 2016/3/29.
 */
public class commentAdapter extends BaseAdapter {
    private ArrayList<commentItems> mList;
    private LayoutInflater mInflater;
    private Context c;
    private DisplayImageOptions options;

    public commentAdapter(Context context, ArrayList<commentItems> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        c=context;

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
            convertView = mInflater.inflate(R.layout.comment_item,null);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.username);
            viewHolder.commentTime = (TextView) convertView.findViewById(R.id.commentTime);
            viewHolder.commentMark = (TextView)convertView.findViewById(R.id.commentMark);
            viewHolder.commentText = (TextView)convertView.findViewById(R.id.commentText);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        commentItems bean = mList.get(position);
        viewHolder.userName.setText(bean.getUserName());
        viewHolder.commentTime.setText(bean.getCommentTime());
        viewHolder.commentMark.setText(bean.getCommentMark());
        viewHolder.commentText.setText(bean.getCommentText());
        return convertView;
    }


    class ViewHolder {
        public TextView userName;
        public TextView commentTime;
        public TextView commentMark;
        public TextView commentText;
    }
}