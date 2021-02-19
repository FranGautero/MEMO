package gautero.tuma.memo.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public interface APIservice {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAkhk4q7Y:APA91bEFWNIoXo2v9e4tJ3nf7c40B2ihBZk0zb5RIbw20fIDLwKRQ-ltGC3joBx35ima3yUsxoQdDf_9Tu2XiLs2RK6YdDCdommc51JwrX9OVr1WQz1yNjvDxwWIYjShunf0q8Eue8rM"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);

}
