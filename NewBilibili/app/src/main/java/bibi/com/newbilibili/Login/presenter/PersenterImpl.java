package bibi.com.newbilibili.Login.presenter;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bibi.com.newbilibili.Login.view.ILoginView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by bilibili on 2017/6/23.
 */

public class PersenterImpl implements IPersenter {
    ILoginView loginView;
    Context context;

    public PersenterImpl(ILoginView loginView) {
        this.loginView = loginView;
//        this.context = loginView;
    }

    @Override
    public void doLogin() {
        //逻辑
        shouquan();


    }

    public void shouquan() {
        final Platform weibo = ShareSDK.getPlatform(QQ.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub

                arg0.getDb().exportData();
                //遍历Map
                Iterator ite = arg2.entrySet().iterator();
                String str = "";
                while (ite.hasNext()) {
                    Map.Entry entry = (Map.Entry) ite.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    str += key + "： " + value + "\n";
                    //                    System.out.println(key + "： " + value);
                }
                loginView.loginResult(true, "");
                //移除授权
                weibo.removeAccount(true);
                //输出所有授权信息
                //                arg0.getDb().exportData();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                Log.e("tag", "onCancel");
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
        //        weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("app");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}
