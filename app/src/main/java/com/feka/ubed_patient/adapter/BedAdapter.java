package com.feka.ubed_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feka.ubed_patient.Constant;
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

        final Bed bed = (Bed) getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_bed, null);
        ViewHolder holder = new ViewHolder();
        holder.bed_id = convertView.findViewById(R.id.bed_id);
        holder.bed_name = convertView.findViewById(R.id.bed_name);
        holder.specialist = convertView.findViewById(R.id.bed_wad_type);
        holder.status = convertView.findViewById(R.id.bed_status);
        holder.check_in = convertView.findViewById(R.id.bed_start_date);
        holder.check_out = convertView.findViewById(R.id.bed_end_date);

        holder.bed_id.setText("#" + String.format("%03d", position+1));
        holder.bed_name.setText("Bed: " + bed.getBed_name());
        holder.specialist.setText(bed.getSpecialist());
        holder.status.setText(bed.getStatus());
        if (bed.getStatus().equals(Constant.BOOKING_APPROVED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_approved));
        }else if (bed.getStatus().equals(Constant.BOOKING_REJECTED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_rejected));
        }else{
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_pending));
        }
        holder.check_in.setText("Start: " + bed.getCheck_in());
        holder.check_out.setText("End: " + bed.getCheck_out());


        return convertView;
    }

    static class ViewHolder {
        TextView bed_id;
        TextView bed_name;
        TextView specialist;
        TextView status;
        TextView check_in;
        TextView check_out;
    }
}
