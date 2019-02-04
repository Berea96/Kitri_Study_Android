package vo;

import java.io.Serializable;

public class Message implements Serializable {
    private String tel;
    private String msg;

    public Message() {}

    public Message(String tel, String msg) {
        this.tel = tel;
        this.msg = msg;
    }

    public String getTel() {
        return tel;
    }

    public String getMsg() {
        return msg;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
