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
import com.travel.resfeber.api.call.GetCancelVehicleBook;
import com.travel.resfeber.api.model.vehicle.CancelVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.Vehicle;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookOrder;
import com.travel.resfeber.custom.TfTextView;
import com.travel.resfeber.helper.AppConstant;
import com.travel.resfeber.helper.Function;
import com.travel.resfeber.ui.VehicleDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by its7 on 9/2/18.
 */

public class VehicleBookAdapter extends RecyclerView.Adapter<VehicleBookAdapter.MyViewHolder> {
    private Context context;
    private List<VehicleBookOrder> vehicleBookOrderList;


    public VehicleBookAdapter(Context context, List<VehicleBookOrder> vehicleBookOrders) {
        this.context = context;
        this.vehicleBookOrderList = vehicleBookOrders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle_book, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VehicleBookOrder bookOrder = vehicleBookOrderList.get(position);
        if (bookOrder.isIsCancel()) {
            holder.imgCancel.setVisibility(View.GONE);
        } else {
            holder.imgCancel.setVisibility(View.VISIBLE);
        }

        holder.setValues(vehicleBookOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        return vehicleBookOrderList.size();
    }

    public void setDataList(List<VehicleBookOrder> data) {
        vehicleBookOrderList = new ArrayList<>();
        vehicleBookOrderList = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TfTextView txtVehicleName, txtVehicleDate, txtVehicleToken, txtVehicleTotalAmount, txtBookingId, txtPickTime;
        private final ProgressBar progressBar;
        private final ImageView imgVehicle, imgCancel;

        private MyViewHolder(View itemView) {
            super(itemView);
            txtBookingId = (TfTextView) itemView.findViewById(R.id.txtBookingId);
            txtVehicleName = (TfTextView) itemView.findViewById(R.id.txtVehicleName);
            txtVehicleDate = (TfTextView) itemView.findViewById(R.id.txtVehicleDate);
            txtVehicleToken = (TfTextView) itemView.findViewById(R.id.txtVehicleToken);
            txtPickTime = (TfTextView) itemView.findViewById(R.id.txtPickTime);
            txtVehicleTotalAmount = (TfTextView) itemView.findViewById(R.id.txtVehicleTotalAmount);
            imgVehicle = (ImageView) itemView.findViewById(R.id.imgVehicle);
            imgCancel = (ImageView) itemView.findViewById(R.id.imgCancel);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void setValues(final VehicleBookOrder vehicleBookOrder) {
            // Log.d("id", String.valueOf(tournament.getId()));

            txtBookingId.setText("Booking Id : " + vehicleBookOrder.getBookingID());
            txtVehicleName.setText(vehicleBookOrder.getVehicalName());
            txtVehicleDate.setText("From Date : " + vehicleBookOrder.getFromDate() + " To Date : " + vehicleBookOrder.getToDate());

            txtVehicleToken.setText("Token : 500 Rs");
            txtPickTime.setText("Pickup Time : " + vehicleBookOrder.getPickupTime());
            txtVehicleTotalAmount.setText("Amount : " + vehicleBookOrder.getTotalAmount() + " Rs");

            // Function.loadImage(context, vehicleBookOrder.getVehicalImage(), imgVehicle, progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VehicleDetailActivity.class);
                    // intent.putExtra(AppConstant.INTENT_VEHICLE, vehicle);
                    intent.putExtra(AppConstant.INTENT_VEHICLE_BOOK_LIST, vehicleBookOrder);
                    intent.putExtra(AppConstant.INTENT_SOURCE, vehicleBookOrder.getSource());
                    intent.putExtra(AppConstant.INTENT_DESTINATION, vehicleBookOrder.getDestination());
                    intent.putExtra(AppConstant.INTENT_START_DATE, vehicleBookOrder.getFromDate());
                    intent.putExtra(AppConstant.INTENT_END_DATE, vehicleBookOrder.getToDate());
                    intent.putExtra(AppConstant.INTENT_DISTANCE, "");
                    intent.putExtra(AppConstant.INTENT_EVENT_NAME, vehicleBookOrder.getEventName());
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });

            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Function.showAlertDialogWithYesNo(context, "Are you sure you want to cancel vehicle order?", new Function.DialogOptionsSelectedListener() {
                        @Override
                        public void onSelect(boolean isYes) {
                            if (isYes) {
                                callVehicleCancelApi(vehicleBookOrder.getBookingID());
                            }
                        }
                    });
                }
            });

        }
    }

    private void callVehicleCancelApi(long bookingID) {
        VehicleBookDetailRequest cancelVehicleBookRequest = new VehicleBookDetailRequest();
        cancelVehicleBookRequest.setBookingID((int) bookingID);

        new GetCancelVehicleBook(context, cancelVehicleBookRequest, new GetCancelVehicleBook.OnGetCancelVehicleBook() {
            @Override
            public void onSuccess(CancelVehicleBookResponse data) {
                if (data != null) {
                    Function.showToast(context, "Vehicle book order cancel successfully ");
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onServerError(String responseMessage) {

            }
        });
    }
}
