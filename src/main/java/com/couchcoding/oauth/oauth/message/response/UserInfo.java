package com.couchcoding.oauth.oauth.message.response;

import com.couchcoding.oauth.oauth.domain.user.entity.Member;

import lombok.Data;

@Data
public class UserInfo {
    private String uid;
    private String email;
    private String nickname;

    public UserInfo(Member member) {
        this.uid = member.getUsername();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
