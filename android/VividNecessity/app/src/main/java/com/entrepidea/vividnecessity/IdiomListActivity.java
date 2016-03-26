package com.entrepidea.vividnecessity;

import com.entrepidea.vividnecessity.model.IdiomRepo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class IdiomListActivity extends SingleFragmentActivity {

    private IdiomListFragment m_fragment;


    public void loadAds(){
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        String TOAST_TEXT = "Test ads are being shown. To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load an ad into the AdMob banner view.
        //TODO: there is a bug running LoadAds: the ad didn't show. check later.
        //loadAds();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        IdiomRepo idiomRepo = IdiomRepo.getInstance(this);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                m_fragment.startSpin();
                m_fragment.reset();
                m_fragment.getAllIdioms();
                return true;

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Setting page is to be implemented", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_sort_alpha:
                if(idiomRepo!=null){
                    idiomRepo.sortByTitle();
                    m_fragment.updateUI();
                }
                return true;

            case R.id.action_sort_date:
                if(idiomRepo!=null){
                    idiomRepo.sortByDate();
                    m_fragment.updateUI();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_idiom_list, menu);
        return true;
    }

    @Override
    protected Fragment createFragment() {
        m_fragment =new IdiomListFragment();
        return m_fragment;
    }

}
