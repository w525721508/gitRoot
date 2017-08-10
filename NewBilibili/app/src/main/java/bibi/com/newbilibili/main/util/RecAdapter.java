package bibi.com.newbilibili.main.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import bibi.com.newbilibili.R;
import bibi.com.newbilibili.main.model.bean.ItemBean;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by bilibili on 2017/6/27.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolders> {
    public List<ItemBean> datas = null;
    public Activity mContext;

    public RecAdapter(List<ItemBean> datas, Activity context) {
        this.datas = datas;
        this.mContext = context;
    }

    int o = 0;

    //创建新View，被LayoutManager所调用
    @Override
    public RecAdapter.ViewHolders onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.page_item, viewGroup, false);
        Log.e("tag", "onCreateViewHolder" + o++);
        ViewHolders vh = new ViewHolders(view, mContext);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecAdapter.ViewHolders viewHolder, final int position) {
        Log.e("tag", "onBindViewHolder");
        viewHolder.setItemBean(datas.get(position));
        viewHolder.setDefault();
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolders extends RecyclerView.ViewHolder {
        private Button btn;
        private ImageView ivHeader, ivItemimage, ivDing, ivCai, ivPingLun, ivShare;
        private TextView tvUserName, tvItemTitle, tvShareCount, tvCommentCount, tvBuryCount, tvStarCount;
        private JCVideoPlayerStandard vvVideo;
        private Context context;
        private ItemBean itemBean;

        public ItemBean getItemBean() {
            return itemBean;
        }

        public void setItemBean(ItemBean itemBean) {
            this.itemBean = itemBean;

        }

        public void setDefault() {
            ivHeader.setImageBitmap(null);
            ivItemimage.setImageBitmap(null);
            vvVideo.thumbImageView.setImageBitmap(null);
        }

        public ViewHolders(View view, Context context) {
            super(view);
            this.context = context;
            btn = (Button) view.findViewById(R.id.btn);
            ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
            ivItemimage = (ImageView) view.findViewById(R.id.ivItemimage);
            ivDing = (ImageView) view.findViewById(R.id.ivDing);
            ivCai = (ImageView) view.findViewById(R.id.ivCai);
            ivPingLun = (ImageView) view.findViewById(R.id.ivPingLun);
            ivShare = (ImageView) view.findViewById(R.id.ivShare);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvItemTitle = (TextView) view.findViewById(R.id.tvItemTitle);
            tvShareCount = (TextView) view.findViewById(R.id.tvShareCount);
            tvCommentCount = (TextView) view.findViewById(R.id.tvCommentCount);
            tvBuryCount = (TextView) view.findViewById(R.id.tvBuryCount);
            tvStarCount = (TextView) view.findViewById(R.id.tvStarCount);
            vvVideo = (JCVideoPlayerStandard) view.findViewById(R.id.vvVideo);
        }

        public void initUi() {
            btn.setText(itemBean.getItemTitle());
            tvUserName.setText(itemBean.getUserName());
            tvItemTitle.setText(itemBean.getItemTitle());
            if ((null != itemBean.getItemImageUrl()) && (!itemBean.getItemImageUrl().equals(""))) {
                vvVideo.setVisibility(View.GONE);
                ivItemimage.setVisibility(View.VISIBLE);
                Glide.with(context).load(itemBean.getItemImageUrl()).asBitmap().placeholder(R.drawable.back).override(300, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(ivItemimage);
            }
            if ((null != itemBean.getUserHeaderUrl())) {
                Glide.with(context).load(itemBean.getUserHeaderUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new CircleTransform(context))
                        .placeholder(R.drawable.back)
                        .error(R.drawable.ic_cai)
                        .crossFade()
                        .into(ivHeader);

                Glide.with(context).load(itemBean.getUserHeaderUrl()).into(ivHeader);
            }

            if (null != itemBean.getItemContentUrl()) {
                vvVideo.setVisibility(View.VISIBLE);
                ivItemimage.setVisibility(View.GONE);
                vvVideo.setUp(itemBean.getItemContentUrl(), 1, "视频标题");
                Glide.with(context).load(itemBean.getItemImageUrl()).placeholder(R.drawable.back).override(300, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).into(vvVideo.thumbImageView);
                JCVideoPlayerStandard.releaseAllVideos();
            }
            tvShareCount.setText(itemBean.getShare_count() + "");

            tvCommentCount.setText(itemBean.getComment_count() + "");
            tvBuryCount.setText(itemBean.getBury_count() + "");
            tvStarCount.setText(itemBean.getDigg_count() + "");
        }
    }

}
