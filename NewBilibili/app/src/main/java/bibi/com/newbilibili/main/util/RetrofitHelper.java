package bibi.com.newbilibili.main.util;

import android.content.Context;

import com.google.gson.GsonBuilder;

import bibi.com.newbilibili.Global.Global;
import bibi.com.newbilibili.main.interfaces.RetrofitService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bilibili on 2017/6/27.
 */

public class RetrofitHelper {
    public Context context;
    private static RetrofitHelper instance;
    Retrofit retrofit;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    public RetrofitHelper(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        retrofit = new Retrofit.Builder().baseUrl(Global.URLPATH)
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(DecodeConverterFactory.create())
                .build();

    }

    public RetrofitService getService() {
        return retrofit.create(RetrofitService.class);
    }
}
