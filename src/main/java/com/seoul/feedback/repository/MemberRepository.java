package com.seoul.feedback.repository;

import com.seoul.feedback.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
