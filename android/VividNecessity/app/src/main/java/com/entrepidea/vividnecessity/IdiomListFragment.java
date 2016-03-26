package com.entrepidea.vividnecessity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.entrepidea.vividnecessity.model.Idiom;
import com.entrepidea.vividnecessity.model.IdiomModel;
import com.entrepidea.vividnecessity.model.IdiomRepo;
import com.entrepidea.vividnecessity.service.IdiomsService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by john on 2/29/2016.
 */
public class IdiomListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private boolean mSubtitleVisible;

    private RecyclerView mIdiomRecyclerView;
    private IdiomListAdapter mAdapter;
    private IdiomRepo mIdiomRepo;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dialog = new ProgressDialog(getActivity());

        mIdiomRepo = IdiomRepo.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idiom_list, container, false);
        //for crate home button
        IdiomListActivity activity = (IdiomListActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        mIdiomRecyclerView = (RecyclerView) view.findViewById(R.id.idiom_recycler_view);
        mIdiomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        startSpin();
        getAllIdioms();
        return view;

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


    @Override
    public void onResume() {
        super.onResume();
        getAllIdioms();
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    public void updateUI( ) {
        if(mIdiomRepo.isEmpty()) return;

        List<Idiom> idioms = mIdiomRepo.getIdioms();
        if (mAdapter == null) {
            mAdapter = new IdiomListAdapter(idioms);
            mIdiomRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setIdioms(idioms);
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
    }

    public void reset(){
        mIdiomRepo.empty();
    }


    public void getAllIdioms(){

        //Creating Rest Services
        IdiomsService restInterface =  new RestAdapter.Builder().setEndpoint(IdiomsService.URL).build().create(IdiomsService.class);
        restInterface.getAllIdioms(new Callback<IdiomModel>() {
            @Override
            public void success(IdiomModel model, Response response) {

                if(mIdiomRepo.isEmpty()) {
                    List<com.entrepidea.vividnecessity.model.Idiom> idioms = model.getIdioms();
                    mIdiomRepo.setIdioms(idioms);
                    mIdiomRepo.sortByDate();
                }
                else {
                    updateUI();
                }
                stopSpin();
            }

            @Override
            public void failure(RetrofitError error) {

                String merror = error.getMessage();
                Log.e("com.entrepidea.vn", merror);
                stopSpin();
            }

        });
    }

    private class IdiomHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private com.entrepidea.vividnecessity.model.Idiom mIdiom;
        private TextView mTitleTextView;
        private TextView mSubtitleTextView;
        private TextView mDateTextView;

        public IdiomHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.idiom_title);
            mSubtitleTextView = (TextView) itemView.findViewById(R.id.idiom_subtitle);
            mDateTextView = (TextView) itemView.findViewById(R.id.idiom_create_date);
        }

        public void bindIdiom(Idiom idiom) {
            mIdiom = idiom;
            if(mIdiom!=null) {
                mTitleTextView.setText(mIdiom.getTitle());
                mSubtitleTextView.setText(mIdiom.getSubtitle());
                mDateTextView.setText(mIdiom.getFormattedDate());
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = IdiomPagerActivity.newIntent(getActivity(),mIdiom);
            startActivity(intent);
        }
    }



    private class IdiomListAdapter extends RecyclerView.Adapter<IdiomHolder> {

        private List<Idiom> mIdioms;

        public IdiomListAdapter(List<Idiom> idioms) {
            mIdioms = idioms;
        }

        @Override
        public IdiomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_row, parent, false);
            return new IdiomHolder(view);
        }

        @Override
        public void onBindViewHolder(IdiomHolder holder, int position) {
            Idiom idiom = mIdioms.get(position);
            holder.bindIdiom(idiom);
        }

        @Override
        public int getItemCount() {
            return mIdioms.size();
        }

        public void setIdioms(List<Idiom> idioms) {
            mIdioms = idioms;
        }
    }
}
