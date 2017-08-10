package bibi.com.newbilibili.main.presenter;

import android.content.Intent;
import android.view.View;

import bibi.com.newbilibili.main.view.IView;

/**
 * Created by bilibili on 2017/6/27.
 */

public interface IMainPersenter {
    void onCreate();

    void attchView(IView v);

    void stop();

    void attachIncomingIntent(Intent intent);
}
