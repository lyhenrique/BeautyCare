package com.beautycare.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.beautycare.makeup.MakeupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by owner on 2016/2/24.
 */
public class CollectFragment extends Fragment {

    private View mParent;
    private ListView mListView;
    private ImageLoader mImageLoader;
    private Context mContext;
    private FragmentActivity mActivity;
    private final static String CACHE = "/css";
    static final int READ_BLOCK_SIZE = 100;
    String s = "DIORSHOW";
    List  ss = new ArrayList();
    List  ss2 = new ArrayList();

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
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
       return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    initImageLoader(getActivity().getApplicationContext());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this.getActivity();//获取Fragement Context
        delete_file_1();
        load_s();
        new rank().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        load_s2();//delete function
        load(); //add function
        new rank().execute();
        save();
    }


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
        load_s2();//delete function
        save();
        delete_file();
        if (mImageLoader!=null) {
            mImageLoader.clearMemoryCache();
            mImageLoader.clearDiscCache();
        }
    }

    public void delete_file_1(){
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
        File file = new File(directory, "textfile.txt");
        if(file.isFile()){
            file.delete();
        }
    }

    public void delete_file(){
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
        File file = new File(directory, "textfile.txt");
        if(file.isFile()){
            file.delete();
        }
        File file2 = new File(directory, "textfile_s2.txt");
        if(file2.isFile()){
            file2.delete();
        }


    }

    public void save(){

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
            directory.mkdirs();
            File file = new File(directory, "textfile_s.txt");
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
// --- Write the string to the file ---
            HashSet hSet  =   new  HashSet(ss);
            ss.clear();
            ss.addAll(hSet);
            for (int i =0 ;i< ss.size();i++){
                osw.write(ss.get(i)+"\t");
            }
            osw.flush();
            osw.close();
//--- Set the EditText to the text that has been read ---
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    private void load_s() {
        try {
//--- SD Storage ---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
            File file = new File(directory, "textfile_s.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line; // 一行数据
            int row = 0;
            while ((line = in.readLine()) != null) {
                String[] temp = line.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    ss.add(temp[j]);}
            }

//--- Set the EditText to the text that has been read ---
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void load_s2() {
        try {
//--- SD Storage ---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
            File file = new File(directory, "textfile_s2.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line; // 一行数据
            int row = 0;
            while ((line = in.readLine()) != null) {
                String[] temp = line.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    ss2.add(temp[j]);
                }
            }
            if(ss2.size()==0){
                return;
            }
            else if(ss2.size()<=ss.size()) {
                for (int j = 0; j < ss2.size(); j++) {
                    ss.set(j, ss2.get(j));
                }
               if(ss2.size()<ss.size()){
                   for(int j = ss.size()-1; j>=ss2.size();j--){
                       ss.remove(j);
                       j--;
                   }
               }
            }

//--- Set the EditText to the text that has been read ---
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private void load() {
        try {
//--- SD Storage ---
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsoluteFile() + "/MyFiles");
            File file = new File(directory, "textfile.txt");
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            s = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
//--- Convert the chars to a String ---
                String readString = String
                        .copyValueOf(inputBuffer, 0, charRead);
                s += readString;
                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            if(ss.size()==0)
                ss.add(s);
            else{
                for (int i = 0; i < ss.size(); i++) {
                    if (s != ss.get(i)) {
                        ss.add(s);
                    }
                }
            }
//--- Set the EditText to the text that has been read ---
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public class rank extends AsyncTask<Void,Void,ArrayList<MakeupData>> {
        ArrayList<MakeupData> read = new ArrayList<MakeupData>();

        @Override
        protected ArrayList<MakeupData> doInBackground(Void... params) {
            try {

                AVObject makeupObject;
                AVFile image1;
                AVFile image2;
                AVFile image3;

                String url1;
                String url2;
                String url3;
                AVQuery<AVObject> query = new AVQuery<>("Makeup");//封装后，这里可以考虑传parms进来
     //           query.whereEqualTo("name",s);
                query.whereContainedIn("name", ss);
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
                            tmpData.setImages(url1, makeupObject.getString("image1_content"));
                        }
                        if (image2 != null) {
                            url2 = image2.getUrl();
                            tmpData.setImages(url2, makeupObject.getString("image2_content"));
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
        protected void onPostExecute(ArrayList<MakeupData> dataList) {
            mListView = (ListView) getView().findViewById(R.id.listcollect);
            initImageLoader(getActivity().getApplicationContext());
            mImageLoader = ImageLoader.getInstance();

            ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
            String imagesUrl[] = ImagesUrl.Urls;
            ListViewItem listViewItem = null;
            // if flag = 1 SearchFragment flag = 2 CollectFragment
            if(dataList.size()==0)
            {
                listViewItem = new ListViewItem("null" , "",
                        "DIORSHOW",
                        "null",
                        "", "", 0, 0, "", "", ""
                        , imagesUrl[1],
                        "", "", "", "", "",
                        R.drawable.good_icon, 2, "0");
                arrayList.add(listViewItem);
            }
            else {
                for (int i = 0; i < dataList.size(); i++) {
                    listViewItem = new ListViewItem("No." + i,   dataList.get(i).getCategory(),
                            dataList.get(i).getMakeup_name(),
                            dataList.get(i).getBrand(),
                            dataList.get(i).getMakeup_content(),
                            dataList.get(i).getBrand_content(),
                            dataList.get(i).getMark(),
                            dataList.get(i).getPrice(),
                            dataList.get(i).getLocation().get(0),
                            dataList.get(i).getLocation().get(1),
                            dataList.get(i).getLocation().get(2),
                            dataList.get(i).getImages().get(0).getImage_url(),
                            dataList.get(i).getImages().get(1).getImage_url(),
                            dataList.get(i).getImages().get(2).getImage_url(),
                            dataList.get(i).getImages().get(0).getImage_content(),
                            dataList.get(i).getImages().get(1).getImage_content(),
                            dataList.get(i).getImages().get(2).getImage_content(),
                            R.drawable.good_icon, 2,String.valueOf(dataList.get(i).getLike()));
                    arrayList.add(listViewItem);
                }
            }
            ListViewAdapter adapter = new ListViewAdapter(arrayList, mContext, mImageLoader);
            mListView.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {

            AVOSCloud.initialize(getActivity(), "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }


    }
}
