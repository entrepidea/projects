package com.entrepidea.imgurdnloadr.dnldrtask;

import android.os.AsyncTask;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.entrepidea.imgurdnloadr.dnldrtask.model.Datum;
import com.entrepidea.imgurdnloadr.dnldrtask.model.Image;
import com.entrepidea.imgurdnloadr.dnldrtask.model.ImageRepo;
import com.entrepidea.imgurdnloadr.dnldrtask.model.ImgurService;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static android.content.ContentValues.TAG;

/**
 * Created by jonat on 6/29/2017.
 */

public class DownloadPresenter implements DownloadContract.Presenter{

    private DownloadContract.View mDownloadView;

    public DownloadPresenter(DownloadContract.View view){
        mDownloadView = view;
    }

    @Override
    public void start() {
        ((DownloadFragment)mDownloadView).startSpin();
        ImgRetrivalTask task = new ImgRetrivalTask();
        task.execute((Void) null);
    }

    public class ImgRetrivalTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            ImgurService restInterface =  new RestAdapter.Builder().setEndpoint(ImgurService.URL).build().create(ImgurService.class);
            restInterface.getAllImages(new retrofit.Callback<Image>() {
                @Override
                public void success(Image model, retrofit.client.Response response) {
                    List<Datum> images = model.getData();
                    for(Datum img : images){
                        Log.d(TAG, img.getLink());
                    }
                    //filter the image list to include those whose link contains only .jpg
                    //the Stream API below is a backport of Java 8's Stream API from LSA (https://github.com/aNNiMON/Lightweight-Stream-API)
                    //This is used because the older version of android (like mine - Android 5.1 Lollip) can't support java 8 APIs but still
                    //I wanted to use some stream feature on the list.
                    //I learned from this post: https://stackoverflow.com/questions/44838263/can-i-use-java-stream-and-support-android-5-1-as-well
                    List<Datum> jpgImgs = Stream.of(images).filter(p -> p.getLink().contains(".jpg")||p.getLink().contains(".gif")).collect(Collectors.toList());
                    ImageRepo.newInstance().setImages(jpgImgs);
                    ((DownloadFragment)mDownloadView).updateUI();
                    ((DownloadFragment)mDownloadView).stopSpin();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                }
            });
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }
}
