package app.laundrydelegate.services;


import app.laundrydelegate.models.chat.ChatRequest;
import app.laundrydelegate.models.myOrders.OrderSRequest;
import app.laundrydelegate.models.orders.MyOrderRequest;
import app.laundrydelegate.models.notifications.NotificationsRequest;
import app.laundrydelegate.models.orders.AcceptOrderRequest;
import app.laundrydelegate.models.settings.SettingsResponse;
import app.laundrydelegate.models.users.LoginRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RequestApi {


    @FormUrlEncoded
    @POST("api/delegate/auth/login")
    Call<LoginRequest> signIn(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("notification_token") String notification_token,
            @Field("device_id") String device_id
    );

    @FormUrlEncoded
    @POST("api/auth/code-confirmation")
    Call<LoginRequest> confirmation_Code(
            @Field("phone") String phone,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("api/delegate/auth/reset-password")
    Call<LoginRequest> send_Code(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("api/delegate/auth/change-password")
    Call<LoginRequest> change_Password(
            @Field("phone") String phone,
            @Field("new_password") String password,
            @Field("new_password_confirmation") String new_password_confirmation
    );

    @Multipart
    @POST("api/delegate/profile/update-info")
    Call<LoginRequest> updateUserInfo(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part img
    );

    //
    @POST("api/delegate/auth/change-password")
    Call<LoginRequest> updateUserPassword(
            @Query("phone") String phone,
            @Query("new_password") String new_password,
            @Query("new_password_confirmation") String password
    );

    @GET("api/delegate/orders/get")
    Call<OrderSRequest> getMyOrders(
            @Query("lat") Double lat,
            @Query("lng") Double lng
    );

    @POST("api/delegate/orders/accept")
    Call<AcceptOrderRequest> createOrder(@Query("order_id") int order_id);

    @GET("api/delegate/orders/my-orders")
    Call<MyOrderRequest> getMyOrders();

    @GET("api/delegate/profile/get-notification")
    Call<NotificationsRequest> getNotifications();

    @GET("api/settings")
    Call<SettingsResponse> getSettings();

    @POST("api/delegate/profile/update-notification-token")
    Call<AcceptOrderRequest> updateToken(
            @Query("notification_token") String token
    );

    //contact Forum
    @POST("api/delegate/suggestions/add")
    Call<AcceptOrderRequest> AddContactForum(
            @Query("title") String title,
            @Query("details") String details

    );

    //Cancel Orders
    @POST("api/delegate/orders/change-order-status")
    Call<AcceptOrderRequest> changeOrdersStatus(
            @Query("order_id") int order_id,
            @Query("status") int status,
            @Query("reason") String reason
    );

    @POST("api/delegate/chat/send")
    Call<ChatRequest> sendMessage(
            @Query("order_id") int order_id,
            @Query("message") String message
    );
}
