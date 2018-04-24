package com.zucc.xwk_31401151.sharebookclient.dataBean.response;

import java.io.Serializable;

/**
 * Created by 帅气逼人唐志丰 on 2018/4/24.
 */

public class MessageResponse implements Serializable {
    public static final String serialVersionName = "message";
    private String message_id;
    private String user_id;
    private String Body;
    private String create_time;
    private String status;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "message_id='" + message_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", Body='" + Body + '\'' +
                ", create_time='" + create_time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
