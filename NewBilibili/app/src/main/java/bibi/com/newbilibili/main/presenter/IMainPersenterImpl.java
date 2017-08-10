package bibi.com.newbilibili.main.presenter;

import android.content.Context;
import android.content.Intent;

import bibi.com.newbilibili.main.model.bean.Book;
import bibi.com.newbilibili.main.model.DataManager;
import bibi.com.newbilibili.main.model.bean.SatinBean;
import bibi.com.newbilibili.main.view.IView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bilibili on 2017/6/27.
 */

public class IMainPersenterImpl implements IMainPersenter {
    private CompositeSubscription compositeSubscription;
    DataManager dataManager;
    Context context;
    IView iView;
    Book mBook;
    SatinBean mSatinBean;

    public IMainPersenterImpl(Context contex) {
        this.context = context;

    }

    @Override
    public void onCreate() {
        compositeSubscription = new CompositeSubscription();
        dataManager = new DataManager(context);
    }

    @Override
    public void attchView(IView v) {
        this.iView = v;
    }

    @Override
    public void stop() {
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getSatinBean() {
        compositeSubscription.add(dataManager.getSatinBeanDatas().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<SatinBean>() {
                    @Override
                    public void onCompleted() {
                        iView.SatinSucc(mSatinBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.allError(e.toString());
                    }

                    @Override
                    public void onNext(SatinBean satinBean) {
//                        Log.e("tag", satinBean);
                        mSatinBean = satinBean;
                    }
                }));
    }

    public void getSeachBook(String name, String tag, int star, int end) {
        compositeSubscription.add(dataManager.getSearchBooks(name, tag, star, end).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null) {
                            iView.success(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mBook != null) {
                            iView.allError(e.toString());
                        }
                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                }));
    }
}
