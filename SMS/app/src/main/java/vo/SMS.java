package vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SMS implements Serializable {
    private String type;

    private String number;
    private String msg;
    private String date;
    private String time;

    public SMS() {
    }

    public SMS(String number, String msg) {
        this.number = number;
        this.msg = msg;
        Calendar c = Calendar.getInstance();
        int y = c.get(c.YEAR);
        int M = c.get(c.MONTH) + 1;
        int d = c.get(c.DAY_OF_MONTH);
        int h = c.get(c.HOUR) + 9;
        int m = c.get(c.MINUTE);
        int s = c.get(c.SECOND);
        date = y + "년 " + M + "월 " + d + "일";
        time = h + ":" + m + ":" + s;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return date + " " + time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime() {
        Calendar c = Calendar.getInstance();
        int y = c.get(c.YEAR);
        int M = c.get(c.MONTH) + 1;
        int d = c.get(c.DAY_OF_MONTH);
        int h = c.get(c.HOUR) + 9;
        int m = c.get(c.MINUTE);
        int s = c.get(c.SECOND);
        this.date = y + "년 " + M + "월 " + d + "일";
        this.time = h + ":" + m + ":" + s;
    }

    @Override
    public String toString() {
        String str = "";
        if(type.equals("write") || type.equals("reply")) {
            str += "받는사람 : " + number + "\n";
        }
        else {
            str += "보낸사람 : " + number + "\n";
        }
        if(msg.length() > 10) {
            str += "내용 : " + msg.substring(0, 10) + "\n";
        }
        else {
            str += "내용 : " + msg + "\n";
        }
        str += "날짜 : " + date + "\n";
        str += "시간 : " + time;
        return str;
    }
}
