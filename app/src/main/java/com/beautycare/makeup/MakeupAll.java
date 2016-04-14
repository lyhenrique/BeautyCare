package com.beautycare.makeup;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beautycare.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

/**
 * Created by ShenLing on 2016/3/7.
 */
public class MakeupAll extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private ArrayList<MakeupData> itemlist;
    private ArrayList<MakeupData> tempList = new ArrayList<MakeupData>();
    private ImageLoader mImageLoader;
    private  ListView listAll;

    static int flag;

    private BGARefreshLayout mRefreshLayout;

    // private BGARefreshLayout.BGARefreshLayoutDelegate delegate;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initImageLoader(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.makeup_all, container, false);

        initImageLoader(getActivity());



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initImageLoader(getActivity());
        new makeup_all_readdata().execute();
    }


    private void initRefreshLayout(BGARefreshLayout mRefreshLayout) {

        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);

        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGAStickinessRefreshViewHolder(getActivity(),true);

        refreshViewHolder.setLoadingMoreText("Loading More");
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.gray);
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.gray);

        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


    }
    @Override
    public void onBGARefreshLayoutBeginRefreshing() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                mRefreshLayout.endRefreshing();

            }
        }.execute();


    }

    @Override
    public void onBGARefreshLayoutBeginLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {


            }
        }.execute();
    }


    //ImageLoader
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



    public class makeup_all_readdata extends AsyncTask<Void,Void,ArrayList<MakeupData>> {

        ArrayList<MakeupData> read = new ArrayList<MakeupData>();

        @Override
        protected ArrayList<MakeupData> doInBackground(Void...params){
            try {

                AVObject makeupObject;
                AVFile image1;
                AVFile image2;
                AVFile image3;

                String url1;
                String url2;
                String url3;


                AVQuery<AVObject> query = new AVQuery<>("Makeup");//封装后，这里可以考虑传parms进来
                List<AVObject> result = query.find();//查询并传数据到result

                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        MakeupData tmpData = new MakeupData();//封装好的数据类型

                        makeupObject = result.get(i);//获取第i个AVObject

                        /*通过key获取AVObject（即数据库）相应的列的内容并调用封装数据类的方法添加数据*/
                        tmpData.setID(makeupObject.getInt("ID"));
                        tmpData.setCategory(makeupObject.getString("category"));
                        tmpData.setMakeup_name(makeupObject.getString("name"));
                        tmpData.setMakeup_content(makeupObject.getString("makeup_content"));
                        tmpData.setBrand(makeupObject.getString("brand"));
                        tmpData.setBrand_content(makeupObject.getString("brand_content"));
                        tmpData.setMark(makeupObject.getInt("mark"));
                        tmpData.setPrice(makeupObject.getInt("price"));
                        tmpData.setLike(makeupObject.getInt("like"));

                        tmpData.setLocation(makeupObject.getString("location1"));
                        tmpData.setLocation(makeupObject.getString("location2"));
                        tmpData.setLocation(makeupObject.getString("location3"));

                        image1 = makeupObject.getAVFile("image1");
                        image2 = makeupObject.getAVFile("image2");
                        image3 = makeupObject.getAVFile("image3");

                        if (image1 != null) {
                            url1 = image1.getUrl();
                            tmpData.setImages(url1,makeupObject.getString("image1_content"));
                        }
                        if (image2 != null) {
                            url2 = image2.getUrl();
                            tmpData.setImages(url2,makeupObject.getString("image2_content"));
                        }
                        if (image3 != null) {
                            url3 = image3.getUrl();
                            tmpData.setImages(url3, makeupObject.getString("image3_content"));
                        }
                        read.add(i, tmpData);
                    }
                }

            } catch (Exception e) {

                Log.d("exception", String.valueOf(e));
            }
            return read;
        }
        @Override
        protected void onPostExecute(ArrayList<MakeupData> dataList){



            listAll = (ListView) getView().findViewById(R.id.listAll);
            initImageLoader(getActivity());

            mImageLoader= ImageLoader.getInstance();
            MakeupAdapter adapter = new MakeupAdapter(getActivity(), dataList);
            listAll.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {

            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
            * 我把我的账号密码给你们，暂时不知道能不能同时登，
            * 账号：maskliang@gmail.com
            * 密码：fgh159456IOP*/
            AVOSCloud.initialize(getActivity(), "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }
}
