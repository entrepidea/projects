package com.entrepidea.imgurdnloadr.dnldrtask;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.entrepidea.imgurdnloadr.R;

/**
 * Created by jonat on 6/29/2017.
 */

public class DownloadActivity extends AppCompatActivity {

    private DownloadPresenter downloadPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        //retrieve the reference to the fragment
        DownloadFragment downloadFragment = (DownloadFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(downloadFragment == null){
            downloadFragment = DownloadFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, downloadFragment);
            transaction.commit();
        }
        //crate a presenter
        downloadPresenter = new DownloadPresenter(downloadFragment); //presenter needs a reference of the view (fragment)



    }
}
