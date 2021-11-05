package com.seoul.feedback.repository;

import com.seoul.feedback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
