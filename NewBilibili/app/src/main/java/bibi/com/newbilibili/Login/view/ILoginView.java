package bibi.com.newbilibili.Login.view;

/**
 * Created by bilibili on 2017/6/23.
 */

public interface ILoginView {
    public void setViewGray();

    public void loginResult(boolean result, String mes);

    public void shareResult(boolean result, String mes);
}
