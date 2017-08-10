/**
 * Copyright 2017 bejson.com
 */
package bibi.com.newbilibili.main.model.bean;

import java.util.List;

/**
 * Auto-generated: 2017-06-28 15:56:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data2 {

    private Group group;
    private List<Comments> comments;
    private int type;
    private double display_time;
    private double online_time;

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setDisplay_time(int display_time) {
        this.display_time = display_time;
    }

    public double getDisplay_time() {
        return display_time;
    }

    public void setOnline_time(int online_time) {
        this.online_time = online_time;
    }

    public double getOnline_time() {
        return online_time;
    }

}