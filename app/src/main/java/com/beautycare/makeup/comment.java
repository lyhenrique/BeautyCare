package com.beautycare.makeup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.beautycare.R;

public class comment extends AppCompatActivity {

    private Button cancel;
    private Button send;
    private RatingBar mark;
    private EditText commentText;

    private float merchant_mark;
    private String merchant_comment;
    private String merchant_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        cancel = (Button) findViewById(R.id.cancel);
        send = (Button) findViewById(R.id.send);
        mark = (RatingBar) findViewById(R.id.mark);
        commentText = (EditText) findViewById(R.id.commentText);

        Bundle bundle = getIntent().getExtras();
        merchant_name = bundle.getString("merchantName");


        mark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                merchant_mark = rating;
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchant_comment = commentText.getText().toString();
                new sendComment().execute();
                finish();
            }
        });

    }

    public void cancel(View view){
        finish();
    }

    private class sendComment extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            AVObject post = new AVObject("comment");
            post.put("category", "Makeup");
            post.put("merchant_name",merchant_name);
            post.put("user","tourist");
            post.put("mark",merchant_mark);
            post.put("commentText",merchant_comment);
            try {
                post.save();

            } catch (AVException e) {
                e.getMessage();
            }

            return null;
        }

        protected void onPostExecute() {
            commentText = (EditText) findViewById(R.id.commentText);
            merchant_comment = commentText.getText().toString();
        }

        @Override
        protected void onPreExecute() {

            /*这个是AsyncTask最开始的动作，这里我们可以初始化一些东西，比如说这个很重要的云数据需要初始，
            * 下面两个很奇怪的参数我自己账户中建了一个应用，然后就会提供应用的id和key,如果大家想试的话，
            * 我把我的账号密码给你们，暂时不知道能不能同时登，
            * 账号：maskliang@gmail.com
            * 密码：fgh159456IOP*/
            AVOSCloud.initialize(comment.this, "lpjA6quucO5BzlmMPxTrjKwD-gzGzoHsz", "vu6uDC74NlzzJP4YbrJmDFV6");

        }
    }


}
