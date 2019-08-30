package org.weibeld.example.tabs;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import org.weibeld.example.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url = "https://mp-vtb.opendev.com/api/services";
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


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Fragment getItem(int position) {
            UniversalPage question = new UniversalPage();
            question.setCustomViewPager(mViewPager);
            mViewPager.setPagingEnabled(false);
            //question.setUpdateMode(false);
            return question;
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
