/**
 * Copyright 2017 bejson.com
 */
package bibi.com.newbilibili.main.model.bean;

import java.util.List;

/**
 * Auto-generated: 2017-06-28 15:47:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private boolean has_more;
    private String tip;
    private boolean has_new_message;
    private double max_time;
    private double min_time;
    private List<Data2> data;

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public boolean getHas_more() {
        return has_more;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }

    public void setHas_new_message(boolean has_new_message) {
        this.has_new_message = has_new_message;
    }

    public boolean getHas_new_message() {
        return has_new_message;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }

    public double getMax_time() {
        return max_time;
    }

    public void setMin_time(int min_time) {
        this.min_time = min_time;
    }

    public double getMin_time() {
        return min_time;
    }

    public void setData(List<Data2> data) {
        this.data = data;
    }

    public List<Data2> getData() {
        return data;
    }

}