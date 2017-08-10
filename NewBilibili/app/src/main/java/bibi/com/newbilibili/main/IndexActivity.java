package bibi.com.newbilibili.main;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bibi.com.newbilibili.R;
import bibi.com.newbilibili.main.model.bean.Book;
import bibi.com.newbilibili.main.model.bean.Group;
import bibi.com.newbilibili.main.model.bean.ItemBean;
import bibi.com.newbilibili.main.model.bean.SatinBean;
import bibi.com.newbilibili.main.presenter.IMainPersenterImpl;
import bibi.com.newbilibili.main.util.MyPtrHandler;
import bibi.com.newbilibili.main.util.MyPtrRefresher;
import bibi.com.newbilibili.main.util.RecAdapter;
import bibi.com.newbilibili.main.view.IView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;


public class IndexActivity extends AppCompatActivity implements IView {
    in.srain.cube.views.ptr.PtrClassicFrameLayout ptrLayout;
    android.support.v7.widget.RecyclerView rotate_header_list_view;
    android.support.v7.widget.RecyclerView recyclerView;
    private RecAdapter adapter;
    private List<ItemBean> dataSource;
    LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridLayoutManager;
    Handler handler;
    private IMainPersenterImpl mBookPresenter = new IMainPersenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ptrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr);
        handler = new Handler();
        initData();
        initEvent();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 初始化ListView中展示的数据

        mBookPresenter.onCreate();
        mBookPresenter.attchView(this);
        mBookPresenter.getSatinBean();
    }

    /**
     * 初始化布局控件
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        // 初始化ListView中的数据
        adapter = new RecAdapter(dataSource, this);
        //线性布局
        mLayoutManager = new LinearLayoutManager(this);

        //水平显示（默认竖直显示）
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
        recyclerView.setAdapter(adapter);


        // 为布局设置头部和底部布局
        ptrLayout.setHeaderView(new MyPtrRefresher(IndexActivity.this));
//        ptrLayout.setFooterView(new MyPtrRefresher(IndexActivity.this));
        ptrLayout.addPtrUIHandler(new MyPtrHandler());
        //解决横向滑动冲突
        ptrLayout.disableWhenHorizontalMove(true);
        //分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //状态为0时：当前屏幕停止滚动；
                // 状态为1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；
                //状态为2时：随用户的操作，屏幕上产生的惯性滑动；
                switch (newState) {
                    case 0: {
                        Log.e("tag", "停止滚动");
                        Glide.with(getApplicationContext()).resumeRequests();
                        loadingUiData(recyclerView);

                    }
                    break;
                    case 1: {
                        Log.e("tag", "手指还在屏幕上");
                        Glide.with(getApplicationContext()).pauseRequests();
                    }
                    break;
                    case 2: {
                        Log.e("tag", "惯性滑动");
                        Glide.with(getApplicationContext()).pauseRequests();
                    }
                    break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void loadingUiData(RecyclerView recyclerView) {
        int star = mLayoutManager.findFirstVisibleItemPosition();
        int end = mLayoutManager.findLastVisibleItemPosition();
        for (int i = star; i <= end; i++) {
            RecAdapter.ViewHolders vh = (RecAdapter.ViewHolders) (recyclerView.getChildViewHolder(recyclerView.getChildAt(i - star)));
            vh.initUi();
        }
    }


    /**
     * 初始化事件
     */
    private void initEvent() {
        // 为布局设置下拉刷新和上拉加载的回调事件
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) { // 上拉加载的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        dataSource.add("New Bottom List Item");
//                        adapter.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        recyclerView.smoothScrollToPosition(dataSource.size() - 1);
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) { // 下拉刷新的回调方法

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBookPresenter.getSatinBean();
                        ptrLayout.refreshComplete();
                        recyclerView.smoothScrollToPosition(0);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void success(Book books) {
        Toast.makeText(IndexActivity.this, books.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void allError(String errorStr) {
        Toast.makeText(IndexActivity.this, "错误" + errorStr, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void SatinSucc(SatinBean satinBean) {
        dataSource = new ArrayList<>();

        initData(satinBean);
        initView();
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadingUiData(recyclerView);
            }
        });

        Toast.makeText(IndexActivity.this, satinBean.getData().getData().get(0).getGroup().getText(), Toast.LENGTH_SHORT).show();
    }

    private void initData(SatinBean satinBean) {
        for (int i = 0; i < satinBean.getData().getData().size(); i++) {
            ItemBean itemBean = new ItemBean();
            Group group = satinBean.getData().getData().get(i).getGroup();
            if (null != group) {
//                private String itemImageUrl;
//                private String itemContentUrl;
//                private int starNum;
//                private int garbageNum;
//                private int leaveNum;
                if (null != group.getUser()) {
                    if (null != group.getUser().getName()) {
                        itemBean.setUserName(group.getUser().getName());
                    }
                    if (null != group.getUser().getAvatar_url()) {
                        itemBean.setUserHeaderUrl(group.getUser().getAvatar_url());
                    }
                }
                if (null != group.getText()) {
                    if (group.getText().equals("")) {
                        itemBean.setItemTitle("默认");
                    } else {
                        itemBean.setItemTitle(group.getText());
                    }
                }
                if ((null != group.getLarge_image()) && (null != group.getLarge_image().getUrl_list()) && (!(group.getLarge_image().getUrl_list().get(0).getUrl().equals("")))) {
                    itemBean.setItemImageUrl(group.getLarge_image().getUrl_list().get(0).getUrl());
                }
                if (null != group.getVideo_id()) {
                    if (null != group.getMp4_url()) {
                        itemBean.setItemContentUrl(group.getMp4_url());
                    }
                    if (null != group.getLarge_cover() && (null != group.getLarge_cover().getUrl_list().get(0).getUrl())) {
                        itemBean.setItemImageUrl(group.getLarge_cover().getUrl_list().get(0).getUrl());
                    }
                }
                itemBean.setShare_count(group.getShare_count());
                itemBean.setComment_count(group.getComment_count());
                itemBean.setBury_count(group.getBury_count());
                itemBean.setDigg_count(group.getDigg_count());

                dataSource.add(itemBean);
            }

        }
    }

    @Override
    public void indexIng() {

    }

}


/**
 * 经典 风格的头部实现
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 * <p>
 * StoreHouse风格的头部实现
 * <p>
 * Material Design风格的头部实现
 * <p>
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 */
//        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
/**
 * StoreHouse风格的头部实现
 */
//        final StoreHouseHeader header = new StoreHouseHeader(this);
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
//
//        /**
//         * using a string, support: A-Z 0-9 - .
//         * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
//         */
//        header.initWithString("Alibaba");


/**
 * Material Design风格的头部实现
 */
//        final MaterialHeader header = new MaterialHeader(this);
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);//显示相关工具类，用于获取用户屏幕宽度、高度以及屏幕密度。同时提供了dp和px的转化方法。


/**
 * Rentals Style风格的头部实现
 * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
 * 在人家git上的daemon中能找到
 */
//        final RentalsSunHeaderView header = new RentalsSunHeaderView(this);
//
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
//        header.setUp(mPtrFrame);
//        mPtrFrame.setLoadingMinTime(1000);
//        mPtrFrame.setDurationToCloseHeader(1500);


// mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr);
//        mPtrFrame.setHeaderView(header);
//        mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
//        mPtrFrame.addPtrUIHandler(header);
//mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
//mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
//        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return super.checkCanDoRefresh(frame, content, header);
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                System.out.println("MainActivity.onRefreshBegin");
//
//                mPtrFrame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mPtrFrame.refreshComplete();
//                        Toast.makeText(IndexActivity.this, "完成", 1500).show();
//                        //mPtrFrame.autoRefresh();//自动刷新
//                    }
//                }, 1800);
//            }
//        });
//        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
//            @Override
//            public void onLoadMoreBegin(final PtrFrameLayout frame) {
//                frame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        frame.refreshComplete();
//                        Toast.makeText(IndexActivity.this, "上啦", 1500).show();
//                        //mPtrFrame.autoRefresh();//自动刷新
//                    }
//                }, 1800);
//            }
//
//            @Override
//            public void onRefreshBegin(final PtrFrameLayout frame) {
//                frame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        frame.refreshComplete();
//                        Toast.makeText(IndexActivity.this, "下啦", 1500).show();
//                        //mPtrFrame.autoRefresh();//自动刷新
//                    }
//                }, 1800);
//            }
//        });