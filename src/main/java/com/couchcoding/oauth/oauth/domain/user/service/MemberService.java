package com.couchcoding.oauth.oauth.domain.user.service;

import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.couchcoding.oauth.oauth.domain.user.dao.*;
import com.couchcoding.oauth.oauth.domain.user.entity.Member;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class MemberService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        Member member;
        try {
            member = loadUserByUsername(username);
        }
        catch (UsernameNotFoundException e)
        {
            return null;
        }
        return member;
    }

    @Transactional
    public Member updateByUsername(FirebaseToken firebaseToken) {
        Member member = memberRepository.findByUsername(firebaseToken.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 유저(%s)를 찾을 수 없습니다."));

        member.update(firebaseToken);

        return memberRepository.save(member);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return memberRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format("해당 유저(%s)를 찾을 수 없습니다.", username)));
//    }

    @Override
    @Transactional(readOnly = true)
    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("해당 유저(%s)를 찾을 수 없습니다.", username)));
    }

    @Transactional
    public Member save(FirebaseToken firebaseToken) {
        log.info("FirebaseTOken ---- "+ firebaseToken.getEmail()+ " "+ firebaseToken.getUid());
        Member member = Member.builder()
                .username(firebaseToken.getUid())
                .email(firebaseToken.getEmail())
                .name(firebaseToken.getName())
                .picture(firebaseToken.getPicture())
                .build();
        memberRepository.save(member);
        return member;
    }
}
