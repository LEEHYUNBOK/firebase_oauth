package com.couchcoding.oauth.oauth.message.response;

import com.couchcoding.oauth.oauth.domain.user.entity.Member;

import lombok.Data;

@Data
public class UserInfo {
    private String username;
    private String email;
    private String name;
    private String picture;

    public UserInfo(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
    }
}
