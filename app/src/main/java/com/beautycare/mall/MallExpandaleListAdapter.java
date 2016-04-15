package com.beautycare.mall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautycare.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by peter on 4/15/2016.
 */
public class MallExpandaleListAdapter extends BaseExpandableListAdapter {

    private Context tmpContext;
    private List<String> floorListHeader;
    private HashMap<String, ArrayList<String>> ShopListData;
    private DisplayImageOptions options;

    public MallExpandaleListAdapter(Context context, ArrayList<String> listHeader, HashMap<String, ArrayList<String>> listChildData) {

        this.tmpContext = context;
        this.floorListHeader = listHeader;
        this.ShopListData = listChildData;
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory()
                .cacheOnDisc()
//                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

    }


    @Override
    public int getGroupCount() {
        return this.floorListHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.ShopListData.get(this.floorListHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.floorListHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return this.ShopListData.get(this.floorListHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.tmpContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mall_expand_list_item_child, null);
        }

        TextView mallFloorName = (TextView) convertView.findViewById(R.id.mall_floor_name_header);
        mallFloorName.setText("testHeader");

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.tmpContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mall_expand_list_item_child, null);
        }

        TextView shopName = (TextView) convertView.findViewById(R.id.mall_shop_name);
        ImageView shopLogo = (ImageView) convertView.findViewById(R.id.mall_shop_logo);

        ImageLoader.getInstance().displayImage("", shopLogo, options);
        shopName.setText("testShop");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
