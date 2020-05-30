package com.afnanulcoder.aspirant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapterForPosts extends ArrayAdapter<PostDetails> {
    private Activity context;
    private List<PostDetails> postList;




    public CustomAdapterForPosts(Activity context, List<PostDetails> postList) {
        super(context, R.layout.post_sample_view, postList);
        this.context = context;
        this.postList = postList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.post_sample_view, null, true);

        PostDetails postDetails = postList.get(position);

        TextView postWriterName = view.findViewById(R.id.postWriterNameID);
        TextView postTv = view.findViewById(R.id.postTvID);
        TextView postLikeTextView = view.findViewById(R.id.postLikeTextViewID);
        ImageView postLikeImageView = view.findViewById(R.id.postLikeImageViewID);




        postWriterName.setText(postDetails.getPostWriter());
        postTv.setText(postDetails.getPost());
        postLikeTextView.setText(postDetails.getLikes()+" Likes");
        postLikeImageView.setImageResource(R.drawable.like_icon_black);


        return view;
    }

}
