package com.beautycare.strategy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beautycare.R;
import com.beautycare.makeup.commentAdapter;
import com.beautycare.makeup.commentItems;

import java.util.ArrayList;
import java.util.List;

public class StrategyDetails extends AppCompatActivity {
    TextView strategyTitle;
    TextView mark;
    RatingBar rating;
    TextView strategyAbstract;
    TextView strategyDetail;


    String item_title;
    String item_abstract;
    String item_details;
    String item_video;

    //video
    private static final String LOGTAG = "video";
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_details);

        strategyTitle = (TextView) findViewById(R.id.strategy_title);
        mark = (TextView) findViewById(R.id.mark);
        rating = (RatingBar) findViewById(R.id.rating);
        strategyAbstract = (TextView) findViewById(R.id.strategy_abstract);
        strategyDetail = (TextView) findViewById(R.id.strategy_detail);


        //webview

        webView = (WebView) findViewById(R.id.webView);
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回键

        Bundle bundle = getIntent().getExtras();
        item_title = bundle.getString("strategyTitle");
        item_abstract = bundle.getString("strategyAbstract");
        item_details = bundle.getString("strategyDetails");
        item_video = bundle.getString("strategyVideo");

        //item name
        setTitle(item_title);

        strategyTitle.setText(item_title);
        strategyAbstract.setText(item_abstract);
        strategyDetail.setText(item_details);

        webView.loadUrl(item_video);

    }

    //返回键
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }




    public void WriteComment(View view){
        Intent intent = new Intent(StrategyDetails.this,com.beautycare.makeup.comment.class);
        Bundle bundle = new Bundle();
        bundle.putString("merchantName","StrategyProductName");
        bundle.putString("category","Strategy");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //查看评论的，执行了showComments()这个asynctask
    public void ViewComment(View view){
        new showComments().execute();
    }


    class showComments extends AsyncTask<Void, Void, ArrayList<commentItems>> {

        ArrayList<commentItems> tmplist = new ArrayList<commentItems>();
        String urlID;

        @Override
        protected ArrayList<commentItems> doInBackground(Void... params) {
            //这个方法是在onPreExecute后执行的，params参数的类型是第一个Void，返回的类型是第三个ArrayList<Data>，返回的参数会传到onPostExecute

            try {
                AVObject commentObject;

                AVQuery<AVObject> query_1 = new AVQuery<>("comment");//封装后，这里可以考虑传parms进来


                List<AVObject> result = query_1.whereEqualTo("category","Strategy").addDescendingOrder("createdAt").find();
                //分类筛选，第一个parameter为属性（key）,第二个为想选择的值，类似where category = 'Strategy'
                //推荐匹配名字，就是.whereEqualTo("merchant_name",item_name)
                //addDescendingOrder("createdAt")是按照创建评论时间倒序排序，就是说把最新的评论放在最前面

                if (result.size() != 0) {

                    for (int i = 0; i < result.size(); i++) {

                        commentItems tmpData = new commentItems();//封装好的数据类型
                        commentObject = result.get(i);//获取第i个AVObject
                        tmpData.setUserName(commentObject.getString("user"));
                        tmpData.setCommentMark("Mark: "+commentObject.getNumber("mark").toString());
                        tmpData.setCommentTime(commentObject.getDate("createdAt").toString());
                        tmpData.setCommentText(commentObject.getString("commentText"));

                        tmplist.add(i, tmpData);//将获取的数据加入全局的一个ArrayList

                    }
                }

            } catch (Exception e) {

                Log.d("exception", String.valueOf(e));
            }
            return tmplist;
        }

        protected void onPostExecute(ArrayList<commentItems> tmpdatalist) {
            //显示dialog的
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(StrategyDetails.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.comment_list, null);//把listview写入dialog中
            alertDialog.setView(convertView);
            alertDialog.setTitle("Comments");
            ListView lv = (ListView) convertView.findViewById(R.id.commentList);

            commentAdapter comAdapter = new commentAdapter(StrategyDetails.this, tmpdatalist);
            lv.setAdapter(comAdapter);

            alertDialog.show();
        }

        @Override
        protected void onPreExecute() {

            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
            * 我把我的账号密码给你们，暂时不知道能不能同时登，
            * 账号：maskliang@gmail.com
            * 密码：fgh159456IOP*/
            AVOSCloud.initialize(StrategyDetails.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }


}
