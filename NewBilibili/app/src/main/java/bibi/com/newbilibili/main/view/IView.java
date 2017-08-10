package bibi.com.newbilibili.main.view;

import bibi.com.newbilibili.main.model.bean.Book;
import bibi.com.newbilibili.main.model.bean.SatinBean;

/**
 * Created by bilibili on 2017/6/27.
 */

public interface IView {
    void success(Book books);

    void allError(String errorStr);

    void SatinSucc(SatinBean satinBean);

    void indexIng();
}
