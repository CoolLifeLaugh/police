package com.lhsj.police.dingding.request;

import java.io.Serializable;

public abstract class AbstractDingRequest implements Serializable {

    private String msgtype;

    public AbstractDingRequest(String msgtype) {
        this.msgtype = msgtype;
    }

    public AbstractDingRequest msgtype(String msgtype) {
        this.msgtype = msgtype;
        return this;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
