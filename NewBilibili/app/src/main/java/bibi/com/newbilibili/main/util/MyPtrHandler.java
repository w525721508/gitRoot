package bibi.com.newbilibili.main.util;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import bibi.com.newbilibili.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by bilibili on 2017/6/29.
 */

public class MyPtrHandler implements PtrUIHandler {
    ImageView img;
    TextView tip;

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        img = ((ImageView) (frame.getHeaderView().findViewById(R.id.id_header_iv_img)));
        tip = ((TextView) (frame.getHeaderView().findViewById(R.id.id_header_tv_tip)));
        Log.e("tag", "onUIReset");
        tip.setText("onUIReset");
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        Log.e("tag", "onUIRefreshPrepare");
        img = ((ImageView) (frame.getHeaderView().findViewById(R.id.id_header_iv_img)));
        tip = ((TextView) (frame.getHeaderView().findViewById(R.id.id_header_tv_tip)));
        tip.setText("onUIRefreshPrepare");


    }


    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        Log.e("tag", "onUIRefreshBegin");

        tip.setText("Loading......");
        RotateAnimation animation = new RotateAnimation(0, 360, img.getPivotX(), img.getPivotY());
        animation.setFillAfter(true);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        img.startAnimation(animation);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        Log.e("tag", "onUIRefreshComplete");
        tip = ((TextView) (frame.getHeaderView().findViewById(R.id.id_header_tv_tip)));
        tip.setText("Load Complete");
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    }
}