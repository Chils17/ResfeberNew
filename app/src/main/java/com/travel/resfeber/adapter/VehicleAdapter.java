package com.travel.resfeber.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.travel.resfeber.R;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.ui.VehicleDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by its7 on 9/2/18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {
    private Context context;
    private String title, source, destination, startDate, endDate, distance, eventName, pickupTime, trip;
    private List<Vehicle> vehicleList;


    public VehicleAdapter(Context context, List<Vehicle> vehicleList, String title, String source, String destination, String startDate, String endDate, String distance, String eventName, String pickupTime, String trip) {
        this.context = context;
        this.vehicleList = vehicleList;
        this.title = title;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.eventName = eventName;
        this.pickupTime = pickupTime;
        this.trip = trip;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setValues(vehicleList.get(position));
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public void setDataList(List<Vehicle> data) {
        vehicleList = new ArrayList<>();
        vehicleList = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TfTextView txtVehicleName, txtVehicleSeat;
        private final ProgressBar progressBar;
        private final ImageView imgVehicle;

        private MyViewHolder(View itemView) {
            super(itemView);
            txtVehicleName = (TfTextView) itemView.findViewById(R.id.txtVehicleName);
            txtVehicleSeat = (TfTextView) itemView.findViewById(R.id.txtVehicleSeat);
            imgVehicle = (ImageView) itemView.findViewById(R.id.imgVehicle);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void setValues(final Vehicle vehicle) {
            // Log.d("id", String.valueOf(tournament.getId()));
            txtVehicleName.setText(vehicle.getVehicalName());
            txtVehicleSeat.setText("No of seats " + vehicle.getNoSeat());

            Function.loadImage(context, vehicle.getVehicalImage(), imgVehicle, progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VehicleDetailActivity.class);
                    intent.putExtra(AppConstant.INTENT_VEHICLE, vehicle);
                    intent.putExtra(AppConstant.INTENT_SOURCE, source);
                    intent.putExtra(AppConstant.INTENT_DESTINATION, destination);
                    intent.putExtra(AppConstant.INTENT_START_DATE, startDate);
                    intent.putExtra(AppConstant.INTENT_END_DATE, endDate);
                    intent.putExtra(AppConstant.INTENT_DISTANCE, distance);
                    intent.putExtra(AppConstant.INTENT_EVENT_NAME, eventName);
                    intent.putExtra(AppConstant.INTENT_PICKUP_TIME, pickupTime);
                    intent.putExtra(AppConstant.INTENT_TRIP, trip);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });


        }
    }
}
