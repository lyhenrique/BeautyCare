package com.beautycare.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.beautycare.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

public class MallActivity extends AppCompatActivity implements OnTaskCompleted {

    private Intent intent;
    private RecyclerView mallRecyList;
    List<AVObject> objects;
    public MallMainRecyAdapter mallMainRecyAdapter;
    ArrayList<MallData> mallDataList = new ArrayList<MallData>();
    GetMallDataTask mytask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_list_main);

        setTitle("Mall");
        initImageLoader(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AVOSCloud.initialize(MallActivity.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");
        mallRecyList = (RecyclerView) findViewById(R.id.mall_list_main);

        mytask = new GetMallDataTask();
        mytask.taskCompleted = this;
        mytask.execute("");

    }

    @Override
    public void processFinish(ArrayList tmpdatalist) {

        mallDataList = tmpdatalist;
//        Log.d("size", String.valueOf(mallDataList.size()));
        mallMainRecyAdapter = new MallMainRecyAdapter(MallActivity.this, mallDataList);

        mallRecyList.setLayoutManager(new LinearLayoutManager(MallActivity.this));

        mallRecyList.setAdapter(mallMainRecyAdapter);

        mallMainRecyAdapter.setOnItemClickListener(new MallMainRecyAdapter.mallListListener() {
            @Override
            public void onItemClick(View v, MallData tmpdata) {

                Intent intent = new Intent(MallActivity.this, MallDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("MallName", tmpdata.getMallName());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

//        Log.d("boolean", mallMainRecyAdapter.toString());

    }


    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);

        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        // config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        //      config.diskCacheSize(50 * 1024 * 1024); // 50 MiB   dsc
        config.discCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.enableLogging();

        ImageLoader.getInstance().init(config.build());
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
}
