package com.fortwelve.wechatstore.component;


import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ToString
public class MsgMap {
    private Map<String,Object> msg;
    private Map<String,Object> meta;
    public MsgMap() {
        this.msg=new LinkedHashMap<>();
        this.meta=new LinkedHashMap<>();
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
