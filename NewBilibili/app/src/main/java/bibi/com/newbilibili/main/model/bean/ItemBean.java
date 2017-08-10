package bibi.com.newbilibili.main.model.bean;

/**
 * Created by bilibili on 2017/6/29.
 */

public class ItemBean {
    private String userHeaderUrl;
    private String userName;
    private String itemTitle;
    private String itemImageUrl;
    private String itemContentUrl;
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
    private int garbageNum;
    private int leaveNum;

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

    public String getUserHeaderUrl() {
        return userHeaderUrl;
    }

    public void setUserHeaderUrl(String userHeaderUrl) {
        this.userHeaderUrl = userHeaderUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemContentUrl() {
        return itemContentUrl;
    }

    public void setItemContentUrl(String itemContentUrl) {
        this.itemContentUrl = itemContentUrl;
    }


    public int getGarbageNum() {
        return garbageNum;
    }

    public void setGarbageNum(int garbageNum) {
        this.garbageNum = garbageNum;
    }

    public int getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(int leaveNum) {
        this.leaveNum = leaveNum;
    }
}
