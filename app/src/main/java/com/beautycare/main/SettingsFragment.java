package com.beautycare.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.beautycare.R;

import java.io.File;

/**
 * Created by owner on 2016/2/18.
 */
public class SettingsFragment extends Fragment {

    private View mParent;
    private Button recommand,about,contact;
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
                .inflate(R.layout.fragment_settings,null);
        recommand = (Button) view.findViewById(R.id.sButton2);
        about = (Button) view.findViewById(R.id.sButton1);
        contact = (Button) view.findViewById(R.id.sButton);
        View.OnClickListener listener0 = null;
        View.OnClickListener listener1 = null;
        View.OnClickListener listener2 = null;
        listener0 = new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(),"A platform introduce cosmetic",Toast.LENGTH_SHORT).show();
            }
        };
        listener1 = new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(),"000-0000000",Toast.LENGTH_SHORT).show();
            }
        };

        listener2 = new View.OnClickListener() {
            public void onClick(View v) {
                String imagePath = Environment.getExternalStorageDirectory() + File.separator + "beautycare_logo.png";
                //由文件得到uri
                Uri imageUri = Uri.fromFile(new File("beatuycare_logo.png"));
                Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to BeautyCare");
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        };
        recommand.setOnClickListener(listener2);
        about.setOnClickListener(listener1);
        contact.setOnClickListener(listener0);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParent = getView();
        mActivity = getActivity();
    }

}