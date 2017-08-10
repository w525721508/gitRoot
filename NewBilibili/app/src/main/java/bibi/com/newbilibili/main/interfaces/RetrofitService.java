package bibi.com.newbilibili.main.interfaces;

import bibi.com.newbilibili.main.model.bean.Book;
import bibi.com.newbilibili.main.model.bean.SatinBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bilibili on 2017/6/27.
 */
//http://iu.snssdk.com/neihan/stream/mix/v1/?
// mpic=1&webp=1&essence=1&content_type=-101&message_cursor=130120714&longitude=113.10413&latitude=14.070337&am_longitude=117.110799&am_latitude=39.07177&am_city=天津市&am_loc_time=1498613892498&count=30&min_time=1498613913&screen_width=1080&double_col_mode=0&local_request_tag=1498613942902&iid=11889369919&device_id=21011263061&ac=wifi&channel=xiaomi&aid=7&app_name=joke_essay&version_code=643&version_name=6.4.3&device_platform=android&ssmix=a&device_type=MI+5&device_brand=Xiaomi&os_api=24&os_version=7.0&uuid=868030354928314&openudid=e6fd2204fa4d80dR&manifest_version_code=643&resolution=1080*1920&dpi=480&update_version_code=6430
public interface RetrofitService {
    @GET("neihan/stream/mix/v1/?mpic=1&webp=1&essence=1&content_type=-101&message_cursor=130120714&longitude=113.10413&latitude=14.070337&am_longitude=117.110799&am_latitude=39.07177&am_city=天津市&am_loc_time=1498613892498&count=30&min_time=1498613913&screen_width=1080&double_col_mode=0&local_request_tag=1498613942902&iid=11889369919&device_id=21011263061&ac=wifi&channel=xiaomi&aid=7&app_name=joke_essay&version_code=643&version_name=6.4.3&device_platform=android&ssmix=a&device_type=MI+5&device_brand=Xiaomi&os_api=24&os_version=7.0&uuid=868030354928314&openudid=e6fd2204fa4d80dR&manifest_version_code=643&resolution=1080*1920&dpi=480&update_version_code=6430")
    Observable<SatinBean> getSatinData();

    @GET("book/search")
    Observable<Book> getSeachBooks(@Query("q") String name, @Query("tag") String tag, @Query("start") int star, @Query("end") int end);

    @GET("book/search")
    Call<Book> getBook(@Query("q") String name, @Query("tag") String tag, @Query("start") int star, @Query("end") int end);
}
