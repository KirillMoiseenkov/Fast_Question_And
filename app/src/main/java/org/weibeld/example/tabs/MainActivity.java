package org.weibeld.example.tabs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.weibeld.example.R;
import org.weibeld.example.tabs.pages.AnswerQuestion;
import org.weibeld.example.tabs.pages.UniversalPage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private final String[] PAGE_TITLES = new String[]{
            "Page 1",
            "Page 2",
            "Page 3"
    };

    private final ArrayList<Fragment> pages = new ArrayList<Fragment>();

    private CustomViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        mViewPager.setPagingEnabled(false);
    }

    private Integer countAnswer = 0;

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        private UniversalPage previusPage;
        private UniversalPage newPage = new UniversalPage();

        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Fragment getItem(int position) {

            previusPage = newPage;
            previusPage.setUpdateMode(true);

            if(countAnswer < 3) {

                newPage = new UniversalPage();
                newPage.setCustomViewPager(mViewPager);
                mViewPager.setPagingEnabled(false);
                newPage.setUpdateMode(true);
                countAnswer++;
                //question.setUpdateMode(false);
            }else {
                newPage = new AnswerQuestion();
                newPage.setCustomViewPager(mViewPager);
                countAnswer = 0;
            }
            return newPage;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }
}
