package com.beautycare.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.beautycare.makeup.MakeupData;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 2016/2/18.
 */
public class SearchFragment extends Fragment {

    String url = "http://sina.com.cn/";
    ProgressDialog progressDialog;
    private Bitmap bm;
    private  Drawable bd;
    private View mParent;
    private FragmentActivity mActivity;

    private ListView mListView;
    private ImageLoader mImageLoader;
    private Context mContext;
    /**
     * Create a new instance of DetailsFragment, initialized to show the text at
     * 'index'.
     */
    public static SearchFragment newInstance(int index) {
        SearchFragment f = new SearchFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    initImageLoader(getActivity().getApplicationContext());

    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container,
                false);

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new rank().execute();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
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

    public class rank extends AsyncTask<Void,Void,ArrayList<MakeupData>> {
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
                query.orderByDescending("like");
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
            int a[];
            mContext=getActivity().getApplicationContext();//获取Fragement Context
            mListView = (ListView) getView().findViewById(R.id.listAll);
            initImageLoader(getActivity().getApplicationContext());
            mImageLoader= ImageLoader.getInstance();

            ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
            String imagesUrl[] = ImagesUrl.Urls;
            ListViewItem listViewItem = null;
            // if flag = 1 SearchFragment flag = 2 CollectFragment
            for (int i = 0; i < dataList.size(); i++) {
                listViewItem = new ListViewItem("No." + i, dataList.get(i).getMakeup_name(), dataList.get(i).getBrand(), dataList.get(i).getImages().get(0).getImage_url(), R.drawable.good_icon, 1, String.valueOf(dataList.get(i).getLike()));
                arrayList.add(listViewItem);
            }
          /*
                if (arrayList.size() == 0) {
                    arrayList.add(listViewItem);
                }
                else {
                    for (int u = 0; u < arrayList.size(); u++) {
                        if (dataList.get(i).getLike() > Integer.parseInt(arrayList.get(u).getLike()))
                        {
                            arrayList.add(u,listViewItem);
                            break;
                        }
                        else {
                            arrayList.add(listViewItem);
                             }
                        }
                      }
                }
            for (int i = 0; i < arrayList.size(); i++)
            {
                int u =i+1;
                arrayList.get(i).setNum("No." + u);
            }
            */
            ListViewAdapter adapter = new ListViewAdapter(arrayList,mContext,mImageLoader);
            mListView.setAdapter(adapter);

        }
        @Override
        protected void onPreExecute() {

            AVOSCloud.initialize(getActivity(), "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }

}