package com.feka.ubed_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.Bed;
import com.feka.ubed_patient.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Review> mData;

    public ReviewAdapter(Context mContext, List<Review> mData) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    public void updateAdapter(List<Review> mData) {
        this.mData = new ArrayList<>(mData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Review review = (Review) getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_review, null);
        ViewHolder holder = new ViewHolder();
        holder.name = convertView.findViewById(R.id.review_name);
        holder.rating = convertView.findViewById(R.id.review_rating);
        holder.date = convertView.findViewById(R.id.review_date);
        holder.reviews = convertView.findViewById(R.id.review_note);

//        final ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.name.setText(review.getUser_name());
        holder.rating.setRating(review.getRating());
        holder.reviews.setText(review.getReview_msg());
        holder.date.setText(review.getDate());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        RatingBar rating;
        TextView reviews;
        TextView date;
    }
}
