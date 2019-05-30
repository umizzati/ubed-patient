package com.feka.ubed_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.feka.ubed_patient.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public AppointmentAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    public void updateAdapter(List<String> mData) {
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
            convertView = inflater.inflate(R.layout.list_appointment, null);
        }
        return convertView;
    }
}
