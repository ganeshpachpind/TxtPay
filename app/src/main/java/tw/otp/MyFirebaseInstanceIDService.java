package tw.otp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    public static final String SERVER_URL = "http://90595ebd.ngrok.io";

    @Override
    public void onTokenRefresh() {
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES",
                Context.MODE_PRIVATE);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("deviceID", refreshedToken);
        editor.apply();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .build();

        MessageService messageService = retrofit.create(MessageService.class);
        Call<ResponseBody> responseBodyCall = messageService.sendDeviceId(refreshedToken);
        try {
            Response<ResponseBody> response = responseBodyCall.execute();
            while(!response.isSuccessful()){
                responseBodyCall.execute();
            }
            System.out.println("Registration Token Shared Sucessfully with server");
        } catch (IOException e) {
            System.out.println("Exception in sending deviceID");
        }
    }
}
