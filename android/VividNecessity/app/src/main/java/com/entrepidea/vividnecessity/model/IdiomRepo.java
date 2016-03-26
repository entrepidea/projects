package com.entrepidea.vividnecessity.model;

import android.content.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by john on 3/4/2016.
 */
public class IdiomRepo {

    private static IdiomRepo idiomRepo;
    private Context mContext;
    List<Idiom> idioms;

    public enum SORT_MODE {
        SORT_LETTER_ASC, SORT_LETTER_DESC, SORT_DATE_NEWEST, SORT_DATE_OLDEST
    };

    SORT_MODE sortMode = SORT_MODE.SORT_DATE_NEWEST;

    public static IdiomRepo getInstance(Context context){
        if(idiomRepo==null){
            idiomRepo = new IdiomRepo(context);
        }
        return idiomRepo;
    }

    private IdiomRepo(Context context){
        mContext = context.getApplicationContext();
    }

    public void setIdioms(List<Idiom> idioms){
        this.idioms = idioms;
    }

    public List<Idiom> getIdioms(){
        return idioms;
    }

    public void empty(){
        idioms.clear();
        idioms = null;
    }

    public boolean isEmpty(){
        return idioms==null || idioms.size()==0;
    }

    public void sortByTitle1(){
        if(idioms!=null){
           //lambda is not supported on this level yet.
           // Collections.sort(idioms, (i1, i2) -> i1.getTitle().compareTo(i2.getTitle()));
            Collections.sort(idioms, new Comparator<Idiom>(){
                public int compare(Idiom p1, Idiom p2){
                    return p2.getTitle().compareTo(p1.getTitle());
                }
            });
        }
    }

    public void sortByTitle2(){
        if(idioms!=null){
            //lambda is not supported on this level yet.
            // Collections.sort(idioms, (i1, i2) -> i1.getTitle().compareTo(i2.getTitle()));
            Collections.sort(idioms, new Comparator<Idiom>(){
                public int compare(Idiom p1, Idiom p2){
                    return p1.getTitle().compareTo(p2.getTitle());
                }
            });
        }
    }

    public void sortByDate1(){
        if(idioms!=null){
            //lambda is not supported on this level yet.
            // Collections.sort(idioms, (i1, i2) -> i1.getTitle().compareTo(i2.getTitle()));
            Collections.sort(idioms, new Comparator<Idiom>(){
                public int compare(Idiom p1, Idiom p2){
                    return p2.getDateCreated().compareTo(p1.getDateCreated());
                }
            });
        }
    }

    public void sortByDate2(){
        if(idioms!=null){
            //lambda is not supported on this level yet.
            // Collections.sort(idioms, (i1, i2) -> i1.getTitle().compareTo(i2.getTitle()));
            Collections.sort(idioms, new Comparator<Idiom>(){
                public int compare(Idiom p1, Idiom p2){
                    return p1.getDateCreated().compareTo(p2.getDateCreated());
                }
            });
        }
    }

    public void sortByTitle(){
        switch (sortMode) {
            case SORT_LETTER_ASC:
                sortByTitle1();
                sortMode = SORT_MODE.SORT_LETTER_DESC;
                break;
            case SORT_LETTER_DESC:
                sortByTitle2();
                sortMode = SORT_MODE.SORT_LETTER_ASC;
                break;
            default:
                sortByTitle2();
                sortMode = SORT_MODE.SORT_LETTER_ASC;
                break;
        }
    }

    public void sortByDate(){
        switch (sortMode) {
            case SORT_DATE_NEWEST:
                sortByDate1();
                sortMode = SORT_MODE.SORT_DATE_OLDEST;
                break;
            case SORT_DATE_OLDEST:
                sortByDate2();
                sortMode = SORT_MODE.SORT_DATE_NEWEST;
                break;
            default:
                sortByDate2();
                sortMode = SORT_MODE.SORT_DATE_NEWEST;
                break;
        }
    }
}
