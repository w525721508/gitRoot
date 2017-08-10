package health.com.retrofittwo;

/**
 * Created by bilibili on 2017/6/13.
 */

public class Bodys {

    /**
     * errcode : 0
     * msg : 上传成功
     * obj : 149733570489.jpg
     */

    private int errcode;
    private String msg;
    private String obj;

    @Override
    public String toString() {
        return "Bodys{" +
                "errcode=" + errcode +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                '}';
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
