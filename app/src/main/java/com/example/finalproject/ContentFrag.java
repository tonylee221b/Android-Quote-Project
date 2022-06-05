package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContentFrag extends Fragment implements NetworkingService.NetworkingListener {

    NetworkingService networkManager;
    JsonService jsonService;
    Content data;
    TextView quoteView, authorView;
    ImageView imgView;

    public static ContentFrag newInstance() {
        ContentFrag frag = new ContentFrag();

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = ((MyApp) getActivity().getApplication()).getNetworkingService();
        jsonService = ((MyApp) getActivity().getApplication()).getJsonService();
        data = ((MyApp) getActivity().getApplication()).mainData;
        networkManager.listener = this;

        networkManager.getQuote();
        networkManager.getImage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);

        quoteView = v.findViewById(R.id.frag_quote);
        authorView = v.findViewById(R.id.frag_author);
        imgView = v.findViewById(R.id.frag_img);

        return  v;
    }

    @Override
    public void quoteListener(String json) {
        Content tmp = jsonService.getContentFromJSON(json);
        data.setQuote(tmp.getQuote());
        data.setAuthor(tmp.getAuthor());
        quoteView.setText("\"" +data.getQuote() + "\"");
        authorView.setText("-" + data.getAuthor());
    }

    @Override
    public void imgListener(Bitmap img, String imgURL) {
        data.setImg(imgURL);
        imgView.setImageBitmap(img);
    }
}
