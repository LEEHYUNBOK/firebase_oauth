package com.couchcoding.oauth.oauth.domain.user.controller;

import com.couchcoding.oauth.oauth.domain.user.entity.Member;
import com.couchcoding.oauth.oauth.domain.user.service.MemberService;
import com.couchcoding.oauth.oauth.message.request.RegisterInfo;
import com.couchcoding.oauth.oauth.message.response.UserInfo;
import com.couchcoding.oauth.oauth.util.RequestUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class MemberController {
    @Autowired
    FirebaseAuth firebaseAuth;
    @Autowired
    private MemberService customUserDetailsService;

    @PostMapping("")
    public UserInfo register(@RequestHeader("Authorization") String authorization,
                            @RequestBody RegisterInfo registerInfo) {
        // TOKEN을 가져온다.
        FirebaseToken decodedToken;
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
                "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }
        // 사용자를 등록한다.
        Member registeredUser = customUserDetailsService.register(
            decodedToken.getUid(), decodedToken.getEmail(), decodedToken.getName(), decodedToken.getPicture());
        return new UserInfo(registeredUser);
    }

    @GetMapping("/me")
    public UserInfo  getUserMe(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return new UserInfo(member);
    }
}
