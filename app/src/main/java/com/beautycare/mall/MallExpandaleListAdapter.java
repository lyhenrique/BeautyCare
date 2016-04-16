package com.beautycare.mall;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by peter on 4/15/2016.
 */
public class MallExpandaleListAdapter extends BaseExpandableListAdapter {

    private Context tmpContext;
    private List<String> floorListHeader;
    private LinkedHashMap<String, String> ShopListData;
    private DisplayImageOptions options;

    public MallExpandaleListAdapter(Context context, List<String> listHeader, LinkedHashMap<String, String> listChildData) {

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
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.floorListHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return this.ShopListData.get(this.floorListHeader.get(groupPosition));
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
            convertView = inflater.inflate(R.layout.mall_expand_list_item_header, null);
        }

        String floorName = (String) getGroup(groupPosition);
        TextView mallFloorName = (TextView) convertView.findViewById(R.id.mall_floor_name_header);
        Typeface customFont = Typeface.createFromAsset(tmpContext.getAssets(), "fonts/segoe print.ttf");
        mallFloorName.setTypeface(customFont);
        Log.d("testTextView", mallFloorName.toString());
        mallFloorName.setText(floorName);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.tmpContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mall_expand_list_item_child, null);
        }

        String url = (String) getChild(groupPosition, childPosition);

//        TextView shopName = (TextView) convertView.findViewById(R.id.mall_shop_name);
        ImageView shopLogo = (ImageView) convertView.findViewById(R.id.mall_shop_logo);

        ImageLoader.getInstance().displayImage(url, shopLogo, options);
//        shopName.setText("testShop");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
