package com.travel.resfeber.api;

import com.travel.resfeber.api.model.Feedback.FeedbackRequest;
import com.travel.resfeber.api.model.Feedback.FeedbackResponse;
import com.travel.resfeber.api.model.login.ForgotPasswordRequest;
import com.travel.resfeber.api.model.Profile.UpdateProfileRequest;
import com.travel.resfeber.api.model.Profile.UpdateProfileResponse;
import com.travel.resfeber.api.model.Profile.ViewProfileResponse;
import com.travel.resfeber.api.model.login.LoginRequest;
import com.travel.resfeber.api.model.login.LoginResponse;
import com.travel.resfeber.api.model.register.RegisterRequest;
import com.travel.resfeber.api.model.register.RegisterResponse;
import com.travel.resfeber.api.model.vehicle.CancelVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.UpdateVehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookDetailResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookListRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookListResponse;
import com.travel.resfeber.api.model.vehicle.VehicleBookRequest;
import com.travel.resfeber.api.model.vehicle.VehicleBookResponse;
import com.travel.resfeber.api.model.vehicle.VehicleRateRequest;
import com.travel.resfeber.api.model.vehicle.VehicleRateResponse;
import com.travel.resfeber.api.model.vehicle.VehicleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCalling {
    @POST(ApiConstant.GET_REGISTER)
    Call<RegisterResponse> getRegister(@Body RegisterRequest loginRequest);

    @POST(ApiConstant.GET_LOGIN)
    Call<LoginResponse> getLogin(@Body LoginRequest loginRequest);

//    @Headers("Content-Type: application/json")
    @GET(ApiConstant.GET_VEHICLE)
    Call<VehicleResponse> getVehicle();

    @POST(ApiConstant.GET_VEHICLE_RATE)
    Call<VehicleRateResponse> getVehicleRate(@Body VehicleRateRequest loginRequest);

    @POST(ApiConstant.GET_VEHICLE_BOOK)
    Call<VehicleBookResponse> getVehicleBook(@Body VehicleBookRequest loginRequest);

    @POST(ApiConstant.GET_VEHICLE_BOOK_DETAIL)
    Call<VehicleBookDetailResponse> getVehicleBookDetail(@Body VehicleBookDetailRequest loginRequest);

    @POST(ApiConstant.GET_UPDATE_VEHICLE_BOOK)
    Call<UpdateVehicleBookResponse> getUpdateVehicleBook(@Body UpdateVehicleBookRequest loginRequest);

    @POST(ApiConstant.GET_VEHICLE_BOOK_LIST)
    Call<VehicleBookListResponse> getVehicleBookList(@Body VehicleBookListRequest vehicleBookListRequest);

    @POST(ApiConstant.GET_VEHICLE_BOOK_CANCEL)
    Call<CancelVehicleBookResponse> getCancelVehicleBook(@Body VehicleBookDetailRequest cancelVehicleBookRequest);

    @POST(ApiConstant.GET_ADD_FEEDBACK)
    Call<FeedbackResponse> getAddFeedback(@Body FeedbackRequest cancelVehicleBookRequest);

    @POST(ApiConstant.GET_FORGOT_PASSWORD)
    Call<FeedbackResponse> getForgotPassword(@Body ForgotPasswordRequest cancelVehicleBookRequest);

    @POST(ApiConstant.GET_VIEW_PROFILE)
    Call<ViewProfileResponse> getViewProfile(@Body VehicleBookListRequest viewProfileRequest);

    @POST(ApiConstant.GET_UPDATE_PROFILE)
    Call<UpdateProfileResponse> getUpdateProfile(@Body UpdateProfileRequest cancelVehicleBookRequest);
}
