package com.travel.resfeber.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.travel.resfeber.api.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpinnerAdapter extends BaseAdapter implements SpinnerAdapter
{
    private final Context activity;
    private List<Vehicle> vehicles;

    public VehicleSpinnerAdapter(Context context, List<Vehicle> vehicles) {
        this.vehicles=vehicles;
        activity = context;
    }



    public int getCount()
    {
        return vehicles.size();
    }

    public Object getItem(int i)
    {
        return vehicles.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(vehicles.get(position).getVehicalName());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
      //  txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0);
        txt.setText(vehicles.get(i).getVehicalName());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }
}
