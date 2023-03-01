package com.couchcoding.oauth.oauth.domain.user.controller;

import com.couchcoding.oauth.oauth.domain.user.entity.Member;
import com.couchcoding.oauth.oauth.domain.user.service.MemberService;
import com.couchcoding.oauth.oauth.domain.user.dto.MemberDTO;
import com.couchcoding.oauth.oauth.util.RequestUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class MemberController {
    @Autowired
    FirebaseAuth firebaseAuth;
    @Autowired
    private MemberService memberService;

    @PostMapping("")
    public MemberDTO register(@RequestHeader("Authorization") String authorization) {
        // TOKEN을 가져온다.
        log.info("POSTTTTTTTTTTTTTTTTTTTTTTTTTT");
        FirebaseToken decodedToken;
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
                "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }
        // 사용자를 등록한다.
        Member registeredUser = memberService.findByUsername(decodedToken.getUid());
        log.info("++++++ " + registeredUser);
        if (registeredUser != null) {
            return new MemberDTO(registeredUser);
        }

        return new MemberDTO(registeredUser);
    }

    @GetMapping("/me")
    public MemberDTO getUserMe(Authentication authentication) {
        log.info("GETTTTTTTTTTTTTTTTTTTT");
        Member member = ((Member) authentication.getPrincipal());
        log.info("{}"+member);
        return new MemberDTO(member);
    }
}
