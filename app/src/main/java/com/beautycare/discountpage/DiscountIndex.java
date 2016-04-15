package com.beautycare.discountpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVOSCloud;
import com.beautycare.R;
import com.beautycare.discountpage.adapter.ItemBean;
import com.beautycare.discountpage.adapter.MyAdapter;
import com.beautycare.discountpage.utils.DiscountDataUtils;
import com.beautycare.discountpage.utils.NetworkUtils;
import com.beautycare.discountpage.utils.RecyclerViewStateUtils;
import com.beautycare.discountpage.utils.onDiscountDataCompleted;
import com.beautycare.discountpage.widget.ImageLoadingDialog;
import com.beautycare.discountpage.widget.LoadingFooter;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DiscountIndex extends AppCompatActivity implements onDiscountDataCompleted {

    /**
     * 服务器端一共多少条数据
     */
    private static final int TOTAL_COUNTER = 10;//用itemList.size()代替.

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 6;

    /**
     * 已经获取到多少条数据了
     */
    private int mCurrentCounter = 0;

    /**
     * 整个List有多少数据
     */
    private int mAllCounter = 0;

    private RecyclerView mRecyclerView = null;

    private MyAdapter mDataAdapter = null;

//    private List<ItemBean> itemBeanList = new ArrayList<ItemBean>();

    private PreviewHandler mHandler = new PreviewHandler(this);
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

//    private List<ItemBean> temItemBeanList = new ArrayList<>();

    DiscountDataUtils mDataReader;
    ArrayList<ItemBean> mAllDataList = new ArrayList<ItemBean>();
    ArrayList<ItemBean> mTmpDataList = new ArrayList<ItemBean>();
    /**
     * 已经获取到多少条数据了
     */
    int mCurrentCount;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_index);
        setTitle("Discount");
        //显示系统Actionbar的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AVOSCloud.initialize(DiscountIndex.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        //初始化ImageLoader
        initLoaderImage(this);

        mDataReader = new DiscountDataUtils();
        mDataReader.mDataCompleted = this;
        mDataReader.execute("");

        mRecyclerView.addOnScrollListener(mOnScrollListener);
//        new LoadData().execute();

        //init data
//        initData();

//        mCurrentCounter = itemBeanList.size();

//        mDataAdapter = new MyAdapter(this,itemBeanList);
//
//        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
//        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
//
//        //setLayoutManager
//        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
//        mRecyclerView.setLayoutManager(manager);

//        RecyclerViewUtils.setHeaderView(mRecyclerView, new SampleHeader(this));


//        setItemClick();
    }

    @Override
    public void onDiscountDataDone(ArrayList<ItemBean> arrayList, ArrayList<ItemBean> allData, int currentNum) {
        mAllDataList = allData;
        mTmpDataList = arrayList;
        mCurrentCount = currentNum;

        final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
        dialog.show();
        // 两秒后关闭后dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000 * 2);

        mDataAdapter = new MyAdapter(DiscountIndex.this,mTmpDataList);

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        //setLayoutManager
        GridLayoutManager manager = new GridLayoutManager(DiscountIndex.this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);

        mDataAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ItemBean data) {
                Intent intent = new Intent(DiscountIndex.this, DiscountDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",data.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSecondDataDone(ArrayList<ItemBean> secondList) {

    }

    //初始化imageloader
    private void initLoaderImage(Context context) {
        ImageLoaderConfiguration.Builder configuration = new ImageLoaderConfiguration.Builder(context);
        configuration.threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .enableLogging();
        ImageLoader.getInstance().init(configuration.build());
    }


    /*
    * 之后改到网上读
    * 估计得改好多
    *
    * */
//    private void initData() {
//        itemBeanList = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher,
//                    "Title" +i,"Content" +i));
//        }
//    }

    //设置点击事件
//    private void setItemClick() {
//        mDataAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, ItemBean data) {
//                Intent intent = new Intent(SalesIndex.this,SalesDetails.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Title",data.title);
//                bundle.putString("Content",data.content);
//                bundle.putInt("Photo",data.photo);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }

    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

//    private void addItems(ArrayList<ItemBean> list) {
//        mDataAdapter.addAll(list);
//        mCurrentCounter += list.size();
//    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
            if (state == LoadingFooter.State.Loading) {
                Log.d("BeautyCare", "the state is Loading, just wait..");
                return;
            }

            if (mCurrentCount < mAllDataList.size()) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(DiscountIndex.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(DiscountIndex.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private class PreviewHandler extends Handler {

        private WeakReference<DiscountIndex> ref;

        PreviewHandler(DiscountIndex activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final DiscountIndex activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case -1:
//                    int currentSize = activity.mDataAdapter.getItemCount();
                    //模拟组装10个数据

                    if (mCurrentCount + 4 <= mAllDataList.size()) {
                        Log.e("EricLau", "newListAdd successfully");
                        final ArrayList<ItemBean> newdata = new ArrayList<>();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<4;i++) {
                                    if(mCurrentCount<mAllDataList.size()) {
                                        newdata.add(i, mAllDataList.get(mCurrentCount));
                                        mCurrentCount++;
                                    }else
                                        break;
                                }
                                mDataAdapter.addAll(newdata);
                                activity.notifyDataSetChanged();
                            }
                        },3000);
                    }

//                        newList.add(new ItemBean(R.mipmap.ic_launcher, "Titleadfadf", "Contentadfdafs"));

//                    activity.addItems(newList);
                    RecyclerViewStateUtils.setFooterViewState(activity.mRecyclerView, LoadingFooter.State.Normal);
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    Log.e("EricLau", "Case2 notifyDatasetchanged");
                    break;
                case -3:
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.mRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, activity.mFooterClick);
                    Log.e("EricLau", "case3 network error");
                    break;
            }
        }
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(DiscountIndex.this, mRecyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };

    /**
     * 模拟请求网络
     */
    private void requestData() {

        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if (NetworkUtils.isNetAvailable(DiscountIndex.this)) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }

    //菜单返回上一页
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }



//    class LoadData extends AsyncTask<Void, Void, ArrayList<ItemBean>> {
//        ArrayList<ItemBean> mTmpList = new ArrayList<>();
//        String urlID;
//        String banner1UrlID;
//        String banner2UrlID;
//
//        @Override
//        protected ArrayList<ItemBean> doInBackground(Void... params) {
//            //这个方法是在onPreExecute后执行的，params参数的类型是第一个Void，返回的类型是第三个ArrayList<Data>，返回的参数会传到onPostExecute
//
//            try {
//                AVObject mSalesObject;
//                AVFile logoObject;
//                AVFile banner1;
//                AVFile banner2;
//                AVQuery<AVObject> query_1 = new AVQuery<>("Sales");
//                List<AVObject> result = query_1.find();
//                Log.e("sales_size", String.valueOf(result.size()));
//                mAllCounter = result.size();
//
//                if(result.size() != 0) {
//                    for (int j=0;j<mAllCounter;j++) {
//                        ItemBean alldata = new ItemBean();
//                        mSalesObject = result.get(j);
//                        alldata.setTitle(mSalesObject.getString("title"));
//                        alldata.setContent(mSalesObject.getString("content"));
//                        alldata.setID(mSalesObject.getObjectId());
//                        alldata.setBANNER_1_NAME(mSalesObject.getString("banner1_name"));
//                        alldata.setBANNER_2_NAME(mSalesObject.getString("banner2_name"));
//                        logoObject = mSalesObject.getAVFile("sales_logo");
//                        banner1 = mSalesObject.getAVFile("banner_1");
//                        banner2 = mSalesObject.getAVFile("banner_2");
//                        if (logoObject != null || banner1 != null || banner2 != null) {
//                            Log.e("logoObject", logoObject.toString());
//                            Log.e("banner_1", banner1.toString());
//                            urlID = logoObject.getUrl();
//                            banner1UrlID = banner1.getUrl();
//                            banner2UrlID = banner2.getUrl();
//                            Log.e("url", urlID);
//                            Log.e("banner", banner1UrlID);
//                            alldata.setURL(urlID);
//                            alldata.setBANNER_1(banner1UrlID);
//                            alldata.setBANNER_2(banner2UrlID);
//                        }
//                        itemBeanList.add(alldata);
//                    }
//                    for(int i=0;i<6;i++) {
//                        ItemBean data = new ItemBean();
//                        mSalesObject = result.get(i);
//                        data.setTitle(mSalesObject.getString("title"));
//                        data.setContent(mSalesObject.getString("content"));
//                        data.setID(mSalesObject.getObjectId());
//                        data.setBANNER_1_NAME(mSalesObject.getString("banner1_name"));
//                        data.setBANNER_2_NAME(mSalesObject.getString("banner2_name"));
//                        Log.e("BANNERNAME", data.getBANNER_1_NAME() + " " + data.getBANNER_2_NAME());
//                        logoObject = mSalesObject.getAVFile("sales_logo");
//                        banner1 = mSalesObject.getAVFile("banner_1");
//                        banner2 = mSalesObject.getAVFile("banner_2");
//                        if (logoObject != null || banner1 != null || banner2 != null) {
//                            Log.e("logoObject", logoObject.toString());
//                            urlID = logoObject.getUrl();
//                            banner1UrlID = banner1.getUrl();
//                            banner2UrlID = banner2.getUrl();
//                            Log.e("url", urlID);
//                            data.setURL(urlID);
//                            data.setBANNER_1(banner1UrlID);
//                            data.setBANNER_2(banner2UrlID);
//                        }
//                        mTmpList.add(i, data);
//                        mCurrentCounter++;
//                        Log.e("data", mTmpList.get(i).getTitle());
//                    }
//                }
//
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//            return mTmpList;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<ItemBean> itemBeans) {
//            super.onPostExecute(itemBeans);
//            temItemBeanList = itemBeans;
//            Log.e("size", String.valueOf(temItemBeanList.size()));
//            mDataAdapter = new MyAdapter(DiscountIndex.this,temItemBeanList);
//
//            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
//            mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
//
//            //setLayoutManager
//            GridLayoutManager manager = new GridLayoutManager(DiscountIndex.this, 2);
//            manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
//            mRecyclerView.setLayoutManager(manager);
//
//            //设置点击事件
//            mDataAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
//                @Override
//                public void onItemClick(View view, ItemBean data) {
//                    Intent intent = new Intent(DiscountIndex.this, DiscountDetails.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("Title", data.getTitle());
//                    bundle.putString("Content", data.getContent());
//                    bundle.putString("Banner1", data.getBANNER_1());
//                    bundle.putString("Banner2",data.getBANNER_2());
//                    bundle.putString("Banner1_Name",data.getBANNER_1_NAME());
//                    bundle.putString("Banner2_Name",data.getBANNER_2_NAME());
//                    Log.e("show me 4 parames",data.getTitle() + " " + data.getContent() + " " +data.getURL() + " " + data.getBANNER_1());
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
//            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
//            * 我把我的账号密码给你们，暂时不知道能不能同时登，
//            * 账号：maskliang@gmail.com
//            * 密码：fgh159456IOP*/
//            AVOSCloud.initialize(DiscountIndex.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");
//            mRecyclerView = (RecyclerView) findViewById(R.id.list);
//
//        }
//    }
}
