package com.zucc.xwk_31401151.sharebookclient.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class MessageListResponse {

    private int total;
    protected List<MessageResponse> messages;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageListResponse{" +
                "total=" + total +
                ", messages=" + messages +
                '}';
    }
}
