package com.beautycare.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beautycare.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by owner on 2016/2/24.
 */
public class CollectFragment extends Fragment {

    private View mParent;
    private ListView mListView;
    private ImageLoader mImageLoader;
    private Context mContext;
    private FragmentActivity mActivity;

    /**
     * Create a new instance of DetailsFragment, initialized to show the text at
     * 'index'.
     */
    public static SettingsFragment newInstance(int index) {
        SettingsFragment f = new SettingsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_collect, container, false);



        mContext=getActivity().getApplicationContext();//获取Fragement Context
        mListView = (ListView) view.findViewById(R.id.listcollect);
        mImageLoader= ImageLoader.getInstance();
        ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
        String imagesUrl[] = ImagesUrl.Urls;
        ListViewItem listViewItem = null;
        // if flag = 1 SearchFragment flag = 2 CollectFragment
        for (int i = 0; i < imagesUrl.length; i++) {
            listViewItem = new ListViewItem("No." + i,"item","brand",imagesUrl[i], R.drawable.good_icon,2,"");
            arrayList.add(listViewItem);
        }

        ListViewAdapter adapter = new ListViewAdapter(arrayList,mContext,mImageLoader);
        mListView.setAdapter(adapter);
        /*
        listAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getActivity(), ((TextView) view).findViewById(R.id.name).toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), makeupDetails.class);
                startActivity(intent);

            }
        });
        */
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader(getActivity().getApplicationContext());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParent = getView();
        mActivity = getActivity();
    }

    // list view
    /**
     *  Configuration of ImageLoader:
     *  This configuration tuning is custom.
     *  You can tune every option, you may tune some of them,
     *  or you can create default configuration by
     *  ImageLoaderConfiguration.createDefault(this) method.
     *
     *  Note:
     *  1 enableLogging() // Not necessary in common
     *  2 实际项目中可将其放到Application中
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .enableLogging()
                .build();
        ImageLoader.getInstance().init(config);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageLoader!=null) {
            mImageLoader.clearMemoryCache();
            mImageLoader.clearDiscCache();
        }
    }

}
