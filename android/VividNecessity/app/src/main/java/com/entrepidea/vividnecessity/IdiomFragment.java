package com.entrepidea.vividnecessity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.entrepidea.vividnecessity.model.Idiom;

/**
 * Created by john on 3/3/2016.
 */
public class IdiomFragment  extends Fragment {

    private Idiom idiom;

    public static IdiomFragment newInstance(Idiom idiom) {
        Bundle args = new Bundle();
        args.putSerializable("IDIOM", idiom);
        IdiomFragment fragment = new IdiomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idiom = (Idiom)getArguments().getSerializable("IDIOM");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_idiom_pager, container, false);
        WebView wv = (WebView) v.findViewById(R.id.webview);
        wv.getSettings().setJavaScriptEnabled(true);

        Intent i = getActivity().getIntent();
        Bundle b = i.getBundleExtra("IDIOM_BUNDLE");
        Idiom idiom = (Idiom)b.get("IDIOM");
        if(idiom!=null) {
            String definition = idiom.getDefinition();
            String title = idiom.getTitle();
            String example = idiom.getExample();

            String content = new StringBuilder("<htlm><strong>")
                    .append(title)
                    .append("</strong><hr/>")
                    .append(definition)
                    .append("<p></p><p></p><strong>Example:</strong><hr/>")
                    .append(example)
                    .append("</html>").toString();

            wv.loadData(content, "text/html", "UTF-8");

        }
        return v;
    }
}
