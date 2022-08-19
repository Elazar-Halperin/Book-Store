package com.example.bookstore.LogActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.bookstore.LogActivities.LogFragments.SignInFragment;
import com.example.bookstore.LogActivities.LogFragments.SignUpFragment;
import com.example.bookstore.LogActivities.LogFragments.ViewPagerAdapter;
import com.example.bookstore.R;
import com.google.android.material.tabs.TabLayout;

public class ReaderLogActivity extends AppCompatActivity {

    private TabLayout tl_tabsHolder;
    private ViewPager vp_fragmentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_log);

        tl_tabsHolder = findViewById(R.id.tl_tabHolder);
        vp_fragmentHolder = findViewById(R.id.vp_logHolder);

        tl_tabsHolder.setupWithViewPager(vp_fragmentHolder);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new SignInFragment(), getResources().getString(R.string.sign_in));
        vpAdapter.addFragment(new SignUpFragment(), getResources().getString(R.string.sign_up));
        vp_fragmentHolder.setAdapter(vpAdapter);

    }
}