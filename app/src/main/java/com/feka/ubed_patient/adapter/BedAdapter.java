package com.feka.ubed_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.Bed;

import java.util.ArrayList;
import java.util.List;

public class BedAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bed> mData;

    public BedAdapter(Context mContext, List<Bed> mData) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    public void updateAdapter(List<Bed> mData) {
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

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_bed, null);
        }
        return convertView;
    }
}
