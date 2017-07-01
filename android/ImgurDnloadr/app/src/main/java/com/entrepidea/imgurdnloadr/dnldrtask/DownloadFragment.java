package com.entrepidea.imgurdnloadr.dnldrtask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.entrepidea.imgurdnloadr.R;
import com.entrepidea.imgurdnloadr.dnldrtask.model.Datum;
import com.entrepidea.imgurdnloadr.dnldrtask.model.ImageRepo;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by jonat on 6/29/2017.
 */

public class DownloadFragment extends Fragment implements DownloadContract.View{

    private DownloadContract.Presenter mPresenter;

    private RecyclerView mImgRecyclerView;

    public static DownloadFragment newInstance(){return new DownloadFragment();}

    public DownloadFragment(){}

    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.start(); //from here presenter would take over
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.frag_download, container, false);
        mImgRecyclerView = (RecyclerView) root.findViewById(R.id.img_recycler_view);
        mImgRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO
       //presenter doing some task
    }

    @Override
    public void setPresenter(@NotNull DownloadContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private ImgListAdapter mAdapter;
    public void updateUI( ) {
        ImageRepo imgRepo = ImageRepo.newInstance();
        List<Datum> images = imgRepo.getImages();
        if (mAdapter == null) {
            mAdapter = new ImgListAdapter(images);
            mImgRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setImages(images);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void startSpin(){
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void stopSpin(){
        dialog.dismiss();
    }


    //bind the model data to the GUI
    private class ImgHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;

        public ImgHolder(View itemView) {
            super(itemView);
        }
    }

    private class ImgListAdapter extends RecyclerView.Adapter<ImgHolder> {

        private List<Datum> images;
        public ImgListAdapter(List<Datum> imgs){
            this.images = imgs;
        }
        public void setImages(List<Datum> imgs) {
            images = imgs;
        }

        @Override
        public ImgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ImgHolder ih = new ImgHolder(layoutInflater.inflate(R.layout.image_row, null));
            ih.img = (ImageView) ih.itemView.findViewById(R.id.image);
            ih.title = (TextView) ih.itemView.findViewById(R.id.title);
            return ih;
        }

        @Override
        public void onBindViewHolder(ImgHolder holder, int position) {
            Picasso.with(getActivity()).load(images.get(position).getLink()).into(holder.img);
            holder.title.setText(images.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }

}
