package com.feka.ubed_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feka.ubed_patient.Constant;
import com.feka.ubed_patient.R;
import com.feka.ubed_patient.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends BaseAdapter {

    private Context mContext;
    private List<Appointment> mData;

    public AppointmentAdapter(Context mContext, List<Appointment> mData) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    public void updateAdapter(List<Appointment> mData) {
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

        final Appointment app = (Appointment) getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_appointment, null);
        ViewHolder holder = new ViewHolder();
        holder.app_id = convertView.findViewById(R.id.appointment_id);
        holder.app_specialist = convertView.findViewById(R.id.appointment_specialist);
        holder.app_doctor = convertView.findViewById(R.id.appointment_doctor);
        holder.status = convertView.findViewById(R.id.appointment_status);
        holder.date_time = convertView.findViewById(R.id.appointment_date_time);

        String app_id = String.format("# %03d", position+1);
        holder.app_id.setText(app_id);
        holder.app_specialist.setText(app.getSpecialist());
        holder.app_doctor.setText(app.getDoctor());
        holder.status.setText(app.getStatus());
        if (app.getStatus().equals(Constant.BOOKING_APPROVED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_approved));
        }else if (app.getStatus().equals(Constant.BOOKING_REJECTED)){
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_rejected));
        }else{
            holder.status.setBackground(mContext.getDrawable(R.drawable.status_pending));
        }
        String date_time = String.format("Date: %s at %s", app.getDate(), app.getTime());
        holder.date_time.setText(date_time);
        return convertView;
    }

    static class ViewHolder {
        TextView app_id;
        TextView app_specialist;
        TextView app_doctor;
        TextView status;
        TextView date_time;
    }
}
