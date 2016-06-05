package tw.otp;



import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageService {
    @FormUrlEncoded
    @POST("/deviceID")
    Call<ResponseBody> sendDeviceId(@Field("deviceID") String deviceId);
}
