package com.couchcoding.oauth.oauth.domain.user.dao;

import com.couchcoding.oauth.oauth.domain.user.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUsername(String username);
}
