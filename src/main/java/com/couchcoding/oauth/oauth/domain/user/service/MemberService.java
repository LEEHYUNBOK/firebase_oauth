package com.couchcoding.oauth.oauth.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.couchcoding.oauth.oauth.domain.user.dao.*;
import com.couchcoding.oauth.oauth.domain.user.entity.Member;

@Service
public class MemberService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(username).get();
    }

    @Transactional
    public Member register(String uid, String email, String nickname) {
        Member member = Member.builder()
                .username(uid)
                .email(email)
                .nickname(nickname)
                .build();
        memberRepository.save(member);
        return member;
    }
}
