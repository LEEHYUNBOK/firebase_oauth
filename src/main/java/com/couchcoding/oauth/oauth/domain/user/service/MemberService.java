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

    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        Member member;
        try {
            member = loadByUsername(username);
        }
        catch (UsernameNotFoundException e)
        {
            return null;
        }
        return member;
    }

    public Member loadByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("해당 유저(%s)를 찾을 수 없습니다.", username)));
    }

    @Transactional
    public Member save(String uid, String email, String name, String picture) {
        Member member = Member.builder()
                .username(uid)
                .email(email)
                .name(name)
                .picture(picture)
                .build();
        memberRepository.save(member);
        return member;
    }
}
