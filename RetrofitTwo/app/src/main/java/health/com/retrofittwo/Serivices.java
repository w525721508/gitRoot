package health.com.retrofittwo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by bilibili on 2017/6/12.
 */

public interface Serivices {
    @GET("book/{paths}")
        //q,tag,start,count
    Call<Book> testcall(@Path("paths") String paths, @QueryMap Map<String, String> maps, @Query("start") int start, @Query("count") int count);

    @Multipart
    @POST("c=User&a=upload")
    Call<Bodys> fileCall(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    @Multipart
    @POST("index.php")
    Call<Bodys> uploadFile(
            @Part("file\"; filename=\"test.png") RequestBody file
    );

    @Multipart
    @POST("index.php")
    Call<Bodys> upload(@Query("c") String User, @Query("a") String upload, @Part("description") RequestBody description,
                       @Part MultipartBody.Part file);

    @Multipart()
    @POST("index.php")
    Call<Bodys> uploadFiles(@Query("c") String User, @Query("a") String upload, @Part("file\";filename=\"back.png") RequestBody photo);

    @Multipart
    @POST("index.php")
    Call<Bodys> uploadFiles(
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);


    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("index.php")
    Call<Bodys> postJson(@Query("c") String User, @Query("a") String upload, @Body RequestBody route);
//    @Multipart
//    @POST
//    Call<String> uploadFile(
//            @Part("avatar"; filename=\\\\"avatar.jpg") RequestBody file);

}
