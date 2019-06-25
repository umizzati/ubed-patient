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
import com.feka.ubed_patient.model.User;

import java.util.ArrayList;
import java.util.List;

public class BedAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bed> mData;
    private User mUser;

    public BedAdapter(Context mContext, List<Bed> mData, User user) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
        this.mUser = user;
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
        holder.patient_name = convertView.findViewById(R.id.bed_patient_name);
        holder.bed_id = convertView.findViewById(R.id.bed_id);
        holder.bed_name = convertView.findViewById(R.id.bed_name);
        holder.bed_type = convertView.findViewById(R.id.bed_type);
        holder.specialist = convertView.findViewById(R.id.bed_wad_type);
        holder.doctor = convertView.findViewById(R.id.bed_doctor);
        holder.status = convertView.findViewById(R.id.bed_status);
        holder.check_in = convertView.findViewById(R.id.bed_start_date);
//        holder.check_out = convertView.findViewById(R.id.bed_end_date);

        holder.bed_id.setText("#" + String.format("%03d", position+1));
        holder.bed_name.setText(String.format("Bed: %s", bed.getBed_name()));
        holder.bed_type.setText(String.format("Wad: %s", bed.getBed_type()));
        holder.specialist.setText(String.format("Clinic: %s", bed.getSpecialist()));
        holder.doctor.setText(String.format("Doctor: %s", bed.getDoctor()));
        holder.status.setText(bed.getStatus());
        if (bed.getStatus().equals(Constant.BOOKING_APPROVED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_approved));
        }else if (bed.getStatus().equals(Constant.BOOKING_REJECTED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_rejected));
        }else{
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_pending));
        }

//        if (mUser.isAdmin()){
//            holder.patient_id.setVisibility(View.VISIBLE);
//            holder.patient_id.setText(String.format("Patient %s", bed.getPatient_id()));
//        }else{
//            holder.patient_id.setVisibility(View.GONE);
//        }
        holder.patient_name.setText(String.format("Patient: %s", bed.getUser_name()));
        holder.check_in.setText(String.format("Start: %s", bed.getCheck_in()));
//        holder.check_out.setText(String.format("End: %s", bed.getCheck_out()));


        return convertView;
    }

    static class ViewHolder {
        TextView bed_id;
//        TextView patient_id;
        TextView patient_name;
        TextView bed_name;
        TextView bed_type;
        TextView specialist;
        TextView doctor;
        TextView status;
        TextView check_in;
//        TextView check_out;
    }
}
