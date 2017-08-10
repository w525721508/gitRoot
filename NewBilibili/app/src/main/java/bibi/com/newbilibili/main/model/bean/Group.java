/**
 * Copyright 2017 bejson.com
 */
package bibi.com.newbilibili.main.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2017-06-28 15:56:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Group {

    private User user;
    private String text;
    private String neihan_hot_start_time;
    private List<Dislike_reason> dislike_reason;
    private int create_time;
    private long id;
    private int user_favorite;
    private int share_type;
    private double max_screen_width_percent;
    private int is_can_share;
    private int category_type;
    private String share_url;
    private int label;
    private String content;
    private String id_str;
    private int media_type;
    private int type;
    private int status;
    private int has_comments;
    private Large_image large_image;
    private int user_bury;
    private Activity activity;
    private String status_desc;
    private boolean quick_comment;
    private int display_type;
    private String neihan_hot_end_time;
    private int user_digg;
    private int online_time;
    private String category_name;
    private boolean category_visible;
    private boolean is_anonymous;
    private int repin_count;
    private double min_screen_width_percent;
    private boolean is_neihan_hot;
    private int has_hot_comments;
    private boolean allow_dislike;
    private int image_status;
    private int user_repin;
    private Neihan_hot_link neihan_hot_link;
    private long group_id;
    private Middle_image middle_image;
    private long category_id;
    //后增字段
    private String video_id;
    //视频播放地址
    private String mp4_url;
    //视频缩略图地址
    private LargeCover large_cover;
    //评论数
    private long comment_count;
    //分享数
    private long share_count;
    //赞数
    private long digg_count;
    //踩数
    private long bury_count;
    //详情数
    private long go_detail_count;
    //收藏数
    private long favorite_count;

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getComment_count() {
        return comment_count;
    }

    public void setComment_count(long comment_count) {
        this.comment_count = comment_count;
    }

    public long getShare_count() {
        return share_count;
    }

    public void setShare_count(long share_count) {
        this.share_count = share_count;
    }

    public long getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(long digg_count) {
        this.digg_count = digg_count;
    }

    public long getBury_count() {
        return bury_count;
    }

    public void setBury_count(long bury_count) {
        this.bury_count = bury_count;
    }

    public long getGo_detail_count() {
        return go_detail_count;
    }

    public void setGo_detail_count(long go_detail_count) {
        this.go_detail_count = go_detail_count;
    }

    public long getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(long favorite_count) {
        this.favorite_count = favorite_count;
    }


    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }


    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }


    public LargeCover getLarge_cover() {
        return large_cover;
    }

    public void setLarge_cover(LargeCover large_cover) {
        this.large_cover = large_cover;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setNeihan_hot_start_time(String neihan_hot_start_time) {
        this.neihan_hot_start_time = neihan_hot_start_time;
    }

    public String getNeihan_hot_start_time() {
        return neihan_hot_start_time;
    }

    public void setDislike_reason(List<Dislike_reason> dislike_reason) {
        this.dislike_reason = dislike_reason;
    }

    public List<Dislike_reason> getDislike_reason() {
        return dislike_reason;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }


    public void setGo_detail_count(int go_detail_count) {
        this.go_detail_count = go_detail_count;
    }


    public void setUser_favorite(int user_favorite) {
        this.user_favorite = user_favorite;
    }

    public int getUser_favorite() {
        return user_favorite;
    }

    public void setShare_type(int share_type) {
        this.share_type = share_type;
    }

    public int getShare_type() {
        return share_type;
    }

    public void setMax_screen_width_percent(double max_screen_width_percent) {
        this.max_screen_width_percent = max_screen_width_percent;
    }

    public double getMax_screen_width_percent() {
        return max_screen_width_percent;
    }

    public void setIs_can_share(int is_can_share) {
        this.is_can_share = is_can_share;
    }

    public int getIs_can_share() {
        return is_can_share;
    }

    public void setCategory_type(int category_type) {
        this.category_type = category_type;
    }

    public int getCategory_type() {
        return category_type;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }


    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getId_str() {
        return id_str;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setHas_comments(int has_comments) {
        this.has_comments = has_comments;
    }

    public int getHas_comments() {
        return has_comments;
    }

    public void setLarge_image(Large_image large_image) {
        this.large_image = large_image;
    }

    public Large_image getLarge_image() {
        return large_image;
    }

    public void setUser_bury(int user_bury) {
        this.user_bury = user_bury;
    }

    public int getUser_bury() {
        return user_bury;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setQuick_comment(boolean quick_comment) {
        this.quick_comment = quick_comment;
    }

    public boolean getQuick_comment() {
        return quick_comment;
    }

    public void setDisplay_type(int display_type) {
        this.display_type = display_type;
    }

    public int getDisplay_type() {
        return display_type;
    }

    public void setNeihan_hot_end_time(String neihan_hot_end_time) {
        this.neihan_hot_end_time = neihan_hot_end_time;
    }

    public String getNeihan_hot_end_time() {
        return neihan_hot_end_time;
    }

    public void setUser_digg(int user_digg) {
        this.user_digg = user_digg;
    }

    public int getUser_digg() {
        return user_digg;
    }

    public void setOnline_time(int online_time) {
        this.online_time = online_time;
    }

    public int getOnline_time() {
        return online_time;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_visible(boolean category_visible) {
        this.category_visible = category_visible;
    }

    public boolean getCategory_visible() {
        return category_visible;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }


    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public boolean getIs_anonymous() {
        return is_anonymous;
    }

    public void setRepin_count(int repin_count) {
        this.repin_count = repin_count;
    }

    public int getRepin_count() {
        return repin_count;
    }

    public void setMin_screen_width_percent(double min_screen_width_percent) {
        this.min_screen_width_percent = min_screen_width_percent;
    }

    public double getMin_screen_width_percent() {
        return min_screen_width_percent;
    }

    public void setIs_neihan_hot(boolean is_neihan_hot) {
        this.is_neihan_hot = is_neihan_hot;
    }

    public boolean getIs_neihan_hot() {
        return is_neihan_hot;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }


    public void setHas_hot_comments(int has_hot_comments) {
        this.has_hot_comments = has_hot_comments;
    }

    public int getHas_hot_comments() {
        return has_hot_comments;
    }

    public void setAllow_dislike(boolean allow_dislike) {
        this.allow_dislike = allow_dislike;
    }

    public boolean getAllow_dislike() {
        return allow_dislike;
    }

    public void setImage_status(int image_status) {
        this.image_status = image_status;
    }

    public int getImage_status() {
        return image_status;
    }

    public void setUser_repin(int user_repin) {
        this.user_repin = user_repin;
    }

    public int getUser_repin() {
        return user_repin;
    }

    public void setNeihan_hot_link(Neihan_hot_link neihan_hot_link) {
        this.neihan_hot_link = neihan_hot_link;
    }

    public Neihan_hot_link getNeihan_hot_link() {
        return neihan_hot_link;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setMiddle_image(Middle_image middle_image) {
        this.middle_image = middle_image;
    }

    public Middle_image getMiddle_image() {
        return middle_image;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public long getCategory_id() {
        return category_id;
    }

}