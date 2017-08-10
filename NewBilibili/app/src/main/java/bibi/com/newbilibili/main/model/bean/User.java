/**
 * Copyright 2017 bejson.com
 */
package bibi.com.newbilibili.main.model.bean;

/**
 * Auto-generated: 2017-06-28 15:56:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class User {

    private long user_id;
    private String name;
    private int followings;
    private boolean user_verified;
    private int ugc_count;
    private String avatar_url;
    private int followers;
    private boolean is_following;
    private boolean is_pro_user;

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getFollowings() {
        return followings;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }

    public boolean getUser_verified() {
        return user_verified;
    }

    public void setUgc_count(int ugc_count) {
        this.ugc_count = ugc_count;
    }

    public int getUgc_count() {
        return ugc_count;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowers() {
        return followers;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public boolean getIs_following() {
        return is_following;
    }

    public void setIs_pro_user(boolean is_pro_user) {
        this.is_pro_user = is_pro_user;
    }

    public boolean getIs_pro_user() {
        return is_pro_user;
    }

}