package com.entrepidea.vividnecessity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.entrepidea.vividnecessity.model.Idiom;
import com.entrepidea.vividnecessity.model.IdiomRepo;

import java.util.List;
import java.util.UUID;

/**
 * Created by john on 3/3/2016.
 */
public class IdiomPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Idiom> mIdioms;
    public static Intent newIntent(Context packageContext, Idiom idiom) {
        Intent intent = new Intent(packageContext, IdiomPagerActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("IDIOM", idiom);
        intent.putExtra("IDIOM_BUNDLE", b);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom_pager);
        mViewPager = (ViewPager) findViewById(R.id.activity_idiom_pager_view_pager);
        mIdioms = IdiomRepo.getInstance(this).getIdioms();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Idiom idiom = mIdioms.get(position);
                return IdiomFragment.newInstance(idiom);
            }

            @Override
            public int getCount() {
                return mIdioms.size();
            }
        });
    }
}
