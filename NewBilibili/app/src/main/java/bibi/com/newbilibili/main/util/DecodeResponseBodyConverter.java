package bibi.com.newbilibili.main.util;

import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by bilibili on 2017/6/28.
 */
public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串
        String temp = new String(value.bytes(), "utf-8");
//        String response = value.string();
        return adapter.fromJson(temp);
    }
}
