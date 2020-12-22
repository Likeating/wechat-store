package com.fortwelve.wechatstore.component;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ToString
public class MsgMap {
    private Map<String,Object> msg;
    private Map<String,Object> meta;

    public MsgMap() {
        msg=new HashMap<>();
        meta=new HashMap<>();
    }
    public MsgMap(String msg, int status) {
        this.msg=new HashMap<>();
        meta=new HashMap<>();
        meta.put("msg",msg);
        meta.put("status",status);
    }

    public Map<String,Object> getMsg() {
        return msg;
    }

    public Object put(String key,Object value){
        return msg.put(key,value);
    }

    public Map<String, Object> getMeta() {
        return meta;
    }
    public void setMeta(String msg, int status) {
        meta.put("msg",msg);
        meta.put("status",status);
    }
}
