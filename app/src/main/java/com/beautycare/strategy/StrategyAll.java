package com.beautycare.strategy;

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

public class StrategyAll extends Fragment  {

    private ArrayList<StrategyData> itemlist ;
    private ArrayList<StrategyData> tempList = new ArrayList<StrategyData>();
    private ImageLoader mImageLoader;
    private ListView listAll;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initImageLoader(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.strategy_all,container,false);


        initImageLoader(getActivity());
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initImageLoader(getActivity());
        new strategy_all_readdata().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
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



    public class strategy_all_readdata extends AsyncTask<Void,Void,ArrayList<StrategyData>> {

        ArrayList<StrategyData> read = new ArrayList<StrategyData>();

        @Override
        protected ArrayList<StrategyData> doInBackground(Void...params){
            try {

                AVObject strategyObject;
                AVFile image1;
                AVFile image2;
                AVFile image3;

                String url1;
                String url2;
                String url3;


                AVQuery<AVObject> query = new AVQuery<>("Strategy");//封装后，这里可以考虑传parms进来
                List<AVObject> result = query.find();//查询并传数据到result

                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        StrategyData tmpData = new StrategyData();//封装好的数据类型

                        strategyObject = result.get(i);//获取第i个AVObject

                        /*通过key获取AVObject（即数据库）相应的列的内容并调用封装数据类的方法添加数据*/
                        //tmpData.setID(strategyObject.getInt("ID"));
                        tmpData.setCategory(strategyObject.getString("category"));
                        tmpData.setItemTitle(strategyObject.getString("itemTitle"));
                        tmpData.setItemAbstract(strategyObject.getString("itemAbstract"));
                        tmpData.setItemDetails(strategyObject.getString("itemDetails"));
                        tmpData.setItemVideo(strategyObject.getString("itemVideo"));
                        tmpData.setMark(strategyObject.getInt("mark"));
                        tmpData.setLike(strategyObject.getInt("like"));

                        tmpData.setMore("Detailed Page");
                        tmpData.setIconSpread(R.drawable.spread);
                        tmpData.setIconShrink_up(R.drawable.shrink_up);
                        image1 = strategyObject.getAVFile("FlipperImage1");
                        image2 = strategyObject.getAVFile("FlipperImage2");
                        image3 = strategyObject.getAVFile("FlipperImage3");

                        if (image1 != null) {
                            url1 = image1.getUrl();
                            tmpData.setFlipper(url1);
                        }
                        if (image2 != null) {
                            url2 = image2.getUrl();
                            tmpData.setFlipper(url2);;
                        }
                        if (image3 != null) {
                            url3 = image3.getUrl();
                            tmpData.setFlipper(url3);
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
        protected void onPostExecute(ArrayList<StrategyData> dataList){

            listAll = (ListView) getView().findViewById(R.id.listAll);
            initImageLoader(getActivity());

            mImageLoader= ImageLoader.getInstance();
            com.beautycare.strategy.StrategyAdapter adapter = new com.beautycare.strategy.StrategyAdapter(getActivity(), dataList);
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


