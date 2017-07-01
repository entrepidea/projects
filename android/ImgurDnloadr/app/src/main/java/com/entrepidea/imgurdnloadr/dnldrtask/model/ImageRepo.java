package com.entrepidea.imgurdnloadr.dnldrtask.model;

import java.util.List;

/**
 * Created by jonat on 6/29/2017.
 */

public class ImageRepo {
    private static ImageRepo imageRepo;
    public static ImageRepo newInstance(){
        if(imageRepo==null){
            imageRepo = new ImageRepo();
        }
        return imageRepo;
    }


    private List<Datum> images;

    public void setImages(List<Datum> images){
        this.images = images;
    }

    public List<Datum> getImages(){
        return images;
    }

    public void empty(){
        images.clear();
        images = null;
    }

    public boolean isEmpty(){
        return images==null || images.size()==0;
    }
}
