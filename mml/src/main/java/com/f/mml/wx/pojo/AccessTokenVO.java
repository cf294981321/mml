package com.f.mml.wx.pojo;

import lombok.Data;

@Data
public class AccessTokenVO {

     private String errcode;
     private String errmsg;
     private String access_token;
     private String expires_in;
}
