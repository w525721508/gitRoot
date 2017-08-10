package bibi.com.newbilibili.main.model;

import android.content.Context;

import bibi.com.newbilibili.main.interfaces.RetrofitService;
import bibi.com.newbilibili.main.model.bean.Book;
import bibi.com.newbilibili.main.model.bean.SatinBean;
import bibi.com.newbilibili.main.util.RetrofitHelper;
import rx.Observable;

/**
 * Created by bilibili on 2017/6/27.
 */

public class DataManager {
    public RetrofitService retrofitService;

    public DataManager(Context context) {
        this.retrofitService = RetrofitHelper.getInstance(context).getService();
    }

    public Observable<Book> getSearchBooks(String name, String tag, int star, int end) {
        return retrofitService.getSeachBooks(name, tag, star, end);
    }

    public Observable<SatinBean> getSatinBeanDatas() {
        return retrofitService.getSatinData();
    }
}
