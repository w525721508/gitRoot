package health.com.retrofittwo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView) findViewById(R.id.tx);
        uploadJson();
//        test();
    }

    public void uploadJson() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.111.199:8596/").addConverterFactory(GsonConverterFactory.create()).build();
        Serivices serivices = retrofit.create(Serivices.class);
        Gson gson = new Gson();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("userId", "173");
        String strEntity = gson.toJson(paramsMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
        Call<Bodys> CALL = serivices.postJson("User", "upload1",requestBody);
        CALL.enqueue(new Callback<Bodys>() {
            @Override
            public void onResponse(Call<Bodys> call, Response<Bodys> response) {
                tx.setText("成功" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Bodys> call, Throwable t) {
                tx.setText("失败");
            }
        });
//
    }

    public void uploadss() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.111.199:8596/").addConverterFactory(GsonConverterFactory.create()).build();
        Serivices serivices = retrofit.create(Serivices.class);
        File file = new File("/mnt/sdcard/", "back.png");
        if (file.exists()) {
            RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
            Call<Bodys> call = serivices.uploadFiles("User", "upload1", image);


            call.enqueue(new Callback<Bodys>() {
                @Override
                public void onResponse(Call<Bodys> call,
                                       Response<Bodys> response) {
                    tx.setText("成功" + response.body().toString());
                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<Bodys> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        } else

        {
            Toast.makeText(MainActivity.this, "没找到文件", 1500).show();
        }

//
    }


    public void uploads() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.111.199:8596/").addConverterFactory(GsonConverterFactory.create()).build();
        Serivices serivices = retrofit.create(Serivices.class);
        File file = new File("/mnt/sdcard/back.png/");
        if (file.exists()) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

            String descriptionString = "This is a description";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);


// 执行请求
            Call<Bodys> call = serivices.upload("User", "upload", description, body);
            call.enqueue(new Callback<Bodys>() {
                @Override
                public void onResponse(Call<Bodys> call,
                                       Response<Bodys> response) {
                    tx.setText("成功" + response.body().toString());
                    Log.v("Upload", "success");
                }

                @Override
                public void onFailure(Call<Bodys> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        } else

        {
            Toast.makeText(MainActivity.this, "没找到文件", 1500).show();
        }

//
    }

    public void upload() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.111.199:8596/index.php/").addConverterFactory(GsonConverterFactory.create()).build();
        Serivices serivices = retrofit.create(Serivices.class);
        File file = new File("/mnt/sdcard/back.png/");
        if (file.exists()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
            //添加描述
            // 添加描述
            String descriptionString = "hello, 这是文件描述";
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
            Call<Bodys> call = serivices.fileCall(description, body);
            call.enqueue(new Callback<Bodys>() {
                @Override
                public void onResponse(Call<Bodys> call, Response<Bodys> response) {
                    tx.setText("成功" + response.body().toString());
                }

                @Override
                public void onFailure(Call<Bodys> call, Throwable t) {
                    tx.setText("失败");
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "没找到文件", 1500).show();
        }

//
    }

    public void test() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("q", "金瓶梅");
        map.put("tag", "");
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.douban.com/v2/").build();
        Serivices serivices = retrofit.create(Serivices.class);
        Call<Book> call = serivices.testcall("search", map, 0, 1);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                tx.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                tx.setText("失败");
            }
        });

    }
}
