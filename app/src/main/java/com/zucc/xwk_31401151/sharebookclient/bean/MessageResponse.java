package com.zucc.xwk_31401151.sharebookclient.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MessageResponse implements Serializable {
    public static final String serialVersionName = "message";
    String message_id;
    String user_id;
    String body;
    String create_time;
    String status;

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
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
                ", body='" + body + '\'' +
                ", create_time='" + create_time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
